import static com.isirode.deployment.cli.groovy.Utils.getSSHSession
import com.isirode.deployment.cli.local.Files
import com.isirode.deployment.cli.local.ShellCommand
import com.isirode.deployment.cli.ssh.Exec
import com.isirode.deployment.cli.ssh.Sftp
import com.isirode.deployment.cli.logger.Logger
import com.jcraft.jsch.JSch;
import com.isirode.deployment.cli.logger.JschLogger;
import com.isirode.deployment.cli.docker.*;
import com.github.dockerjava.api.model.AuthConfig
import java.util.concurrent.TimeUnit

var logger = new Logger();

def command = 'npm run build'

ShellCommand
    .builder()
    .workingDir(scriptDir)
    .build()
    .exec(command)

def dockerClient = DockerUtils.getDefaultClient();

var dockerFile = new File(scriptDir,"Dockerfile")

String localImageName = "${name}:${version}"
String remoteImageName = "${remoteName}:${version}"

logger.log.info("Build: {}", build)
logger.log.info("Push: {}", push)

if (build == "true") {
    log.info("Building")
    String imageId = dockerClient
        .buildImageCmd(dockerFile)
        .withTags([localImageName, remoteImageName, "${name}:latest"].toSet())
        .exec(new BuildImageCallback())
        .awaitImageId()
}

if (push == "true") {
    log.info("Pushing")
    var authConfig = new AuthConfig()
        .withUsername(dockerUsername)
        .withPassword(dockerPassword)

    var pushImageResultCallback = new PushImageCallback()

    var cmd = dockerClient.pushImageCmd(remoteImageName)
            .withTag(version)
            .withAuthConfig(authConfig)

    cmd.exec(pushImageResultCallback)

    pushImageResultCallback.awaitCompletion(90, TimeUnit.SECONDS)
}

if (startPod == "true") {
    log.info("Starting pod")

    JSch.setLogger(new JschLogger());

    var session = getSSHSession(username, password, host, port)

    session.setTimeout(30000);

    var exec = new Exec(session)
    exec.with {
        run "podman stop " + containerName
        // WARN : You need to have login to your Docker registry
        // Like this for instance "podman login registry-url"
        // Do not use "docker login etc", except if you are using docker
        // FIXME : if this necessary ?
        run "podman pull " + remoteImageName
        run "podman run --detach --rm --name " + containerName + " " + remoteImageName
        run "podman container logs --tail=3 " + containerName
    }

    session.disconnect()

    ShellCommand
        .builder()
        .workingDir(scriptDir)
        .build()
        .exec(["curl", remoteUrl])
}
