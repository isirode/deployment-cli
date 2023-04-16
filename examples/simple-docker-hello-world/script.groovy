import static com.isirode.deployment.cli.groovy.Utils.getSSHSession
import com.isirode.deployment.cli.local.Files
import com.isirode.deployment.cli.ssh.Exec
import com.isirode.deployment.cli.ssh.Sftp
import com.isirode.deployment.cli.logger.Logger
import com.jcraft.jsch.JSch;
import com.isirode.deployment.cli.logger.JschLogger;
import com.github.dockerjava.core.DockerClientConfig
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.isirode.deployment.cli.docker.*;
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.core.command.LogContainerResultCallback
import com.github.dockerjava.api.model.Frame

var logger = new Logger();

if (version == null) {
    throw new RuntimeException("version is null")
}
if (name == null) {
    throw new RuntimeException("name is null")
}

logger.log.info(version);
logger.log.info(name);

DockerClientConfig dockerConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
    .withDockerHost("tcp://localhost:2375")
    .build()

DockerClient dockerClient = DockerClientBuilder
    .getInstance(dockerConfig)
    .build()

logger.log.info(scriptDir.toString())

var dockerFile = new File(scriptDir,"Dockerfile")
logger.log.info(dockerFile.absoluteFile.toString())

String imageId = dockerClient
    .buildImageCmd(dockerFile)
    .withTags(["${name}:${version}", "${name}:latest"].toSet())
    .exec(new BuildImageCallback())
    .awaitImageId()

CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
    // Warn : think of removing the container after using it
    // .withName(name)
    .exec();

logger.log.info("Container id : {}", container.id)

dockerClient.startContainerCmd(container.id).exec()

dockerClient.logContainerCmd(container.id)
    // FIXME : checkout if stdout, stderr and tail are necessary
    .withStdOut(true)
    .withStdErr(true)
    .withTail(1)
    .exec(new ResultCallbackDeprecated())
    .awaitCompletion()

//dockerClient.logContainerCmd(container.id)
//        .withStdOut(true)
//        .withStdErr(true)
//        .withTail(1)
//        .exec(new ResultCallback())
