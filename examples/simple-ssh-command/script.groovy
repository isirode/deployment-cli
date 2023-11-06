import static com.isirode.deployment.cli.groovy.Utils.getSSHSession
import com.isirode.deployment.cli.local.Files
import com.isirode.deployment.cli.ssh.Exec
import com.isirode.deployment.cli.logger.Logger
import com.jcraft.jsch.JSch;
import com.isirode.deployment.cli.logger.JschLogger;

// Info : a log (log4j) variable is added to every scripts
log.info("Starting script")

JSch.setLogger(new JschLogger());

// Info : unused but show usage of a specific logger
var logger = new Logger();

// Info : instantiating a local file utility (unused)
var files = new Files()

var session = getSSHSession(username, password, host, port)

session.setTimeout(30000);

var exec = new Exec(session)
exec.with {
    run command + ' ' + directory
}

session.disconnect()
