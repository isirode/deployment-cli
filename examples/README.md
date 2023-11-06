# Examples

To run this examples:
- You need to have the .jar in the main directory of the project

You could also run the .sh script.

## simple-docker-hello-world

This example is about using the Docker CLI and passing an argument to the deployment-cli.

> java -jar deployment-cli.jar -s examples/simple-docker-hello-world/script.groovy -a name=my-hello-world -a version=0.0.1

Or, from the project, after building:

> java -jar build/libs/deployment-cli.jar -s examples/simple-docker-hello-world/script.groovy -a name=my-hello-world -a version=0.0.1

## Simple Jsch

This example show how to execute a SSH command. We are using a value file to store the password, and we are passing the command to execute by argument.

> java -jar deployment-cli.jar -s examples/simple-ssh-command/script.groovy -v examples/simple-ssh-command/values.toml -a command='ls -sh'

Or, from the project, after building:

> java -jar build/libs/deployment-cli.jar -s examples/simple-ssh-command/script.groovy -v examples/simple-ssh-command/values.toml -a command="ls -sh"

## Simple SFTP

TODO

## Simple Typescript + Node + Express : build & deploy

This example is a full deployment example, it shows:
- Building a Typescript Node Express project
- Building a Docker image
- Pushing the Docker image to a remote registry
- Stopping the former pod
- Start the new pod
- Logging the a result of a HTTP request

> java -jar deployment-cli.jar -s examples/simple-node-ts-express-build-and-deploy/script.groovy -v examples/simple-node-ts-express-build-and-deploy/values.toml -a build=true -a push=true -a startPod=true

To run the image locally:

> docker run -p 3000:3000 -it simplenodetsexpressbuildanddeploy:latest sh

## Workspace

You can copy "./simple-workspace-example/.dcli" in your home directory.

Then, run:

> java -jar build/libs/deployment-cli.jar -s examples/simple-workspace-example/script.groovy  -w test_workspace_1,test-workspace-2

