import static com.isirode.deployment.cli.groovy.Utils.getSSHSession
import com.isirode.deployment.cli.local.Files
import com.isirode.deployment.cli.ssh.Exec
import com.isirode.deployment.cli.ssh.Sftp
import com.isirode.deployment.cli.logger.Logger
import com.jcraft.jsch.JSch;
import com.isirode.deployment.cli.logger.JschLogger;

JSch.setLogger(new JschLogger());

var logger = new Logger();

var files = new Files()

var session = getSSHSession(username, password, host, port)

session.setTimeout(30000);

var exec = new Exec(session)
exec.with {
    run command + ' ' + directory
}

session.disconnect()
