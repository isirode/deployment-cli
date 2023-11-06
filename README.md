# deployment-cli

This project allow to execute a Groovy script with data injected into it (using TOML files).

It also contains various utility tools (classes) for deploying executables to a remote server.

You can use it execute commands on the server or locally.

This allow to store the commands being executed, without using a deployment server, like Jenkins.

The main purpose is to do the same thinks that a CI/CD server would do, without the costs and infrastructure maintenance.

It's useful if you are an indie dev, for instance.

## Main features

- Pass a deployment Groovy script
- Pass a file containing values to add in the context of the script
- Load global (from the user home directory) and per workspace value files

## Installation

- Download the jar and the [.sh script](./deployment-cli.sh)
- Put them in the same folder, somewhere in your path

If you are using cmder:
- You can setup an alias this way "dcli=sh deployment-cli.sh $*"
- And do "dcli -s etc"

After this, you can run the scripts.

## Running the project

- Create a file named values.private.toml from the values.toml file
  - Replace the values by the one of your server
- Run the code using java

> java -jar deployment-cli.jar -s test.groovy -v values.private.toml

You can look at [examples](examples/README.md) as well.

## File example

````groovy
import static com.isirode.deployment.cli.groovy.Utils.getSSHSession
import com.isirode.deployment.cli.local.Files
import com.isirode.deployment.cli.ssh.Exec
import com.isirode.deployment.cli.logger.Logger
import com.jcraft.jsch.JSch;
import com.isirode.deployment.cli.logger.JschLogger;

log.info("Starting script")

JSch.setLogger(new JschLogger());

var session = getSSHSession(username, password, host, port)

session.setTimeout(30000);

var exec = new Exec(session)
exec.with {
    run "ls -sh ~/"
}

session.disconnect()
````

## Know issues

Nothing yet here.

## Partipating

Open the [DEVELOPER.md](./DEVELOPER.md) section.

## License

It is provided with the GNU AFFERO GENERAL PUBLIC LICENSE.

deployment-cli is a project allowing to execute commands for the deployment of applications on local or remote servers.

Copyright (C) 2023  Isirode

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
