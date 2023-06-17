# DEVELOPER

## Contributing

You can open a PR if there is a feature you need or a bug to fix.

There is a list of features below which could be interesting.

## Features

- [ ] Scripting languages
  - [x] Groovy
    - [ ] Type autocompletion / verification
      - [ ] Dependencies
        - Use https://github.com/DontShaveTheYak/groovy-guru
        - Specify the groovy.classpath
        - TODO : still need to add the Javadoc
      - [ ] Variable bindings
    - [ ] Multiple files
    - [ ] Auto inject the imports
    - [ ] Very simple setup (pass objects)
  - [ ] Javascript
  - [ ] Typescript
  - [ ] Lua

- [ ] Value file
  - [x] TOML
    - [ ] Use multi object file
  - [ ] HOCON
  - [ ] JSON
  - [ ] Multiple files
  - [ ] Use script directory as root
  - [ ] Specify a directory as root

- [ ] Passing arguments
  - [x] Via -a
  - [ ] Via additional arguments
  - [ ] Boolean arguments
  - [ ] Declare types of elements in the script
  - [ ] Interactive (passwords)

- [ ] Remote
  - [x] Connect
  - [x] Execute commands
  - [x] Copy files

- [ ] Local
  - [ ] Copy files
  - [ ] Copy dir
  - [ ] Exec command
  - [ ] Easy to use working directory

- [ ] Docker
  - [ ] Build image
    - [x] Example
    - [x] Callbacks
    - [ ] Easy to use implementation
  - [ ] Run container
    - [x] Example
    - [x] Callbacks
    - [ ] Easy to use implementation
  - [ ] Push image
    - [x] Example
    - [x] Callbacks
    - [ ] Easy to use implementation

- [ ] Podman
  - [ ] Container
  - [ ] Pod

- [ ] HTTP
  - [ ] Provide an easy to use HTTP client implementation
    - Not updated https://github.com/kevinsawicki/http-request

- [ ] Workspace
  - [ ] Configurations
    - [ ] Global
    - [ ] Workspace
    - [ ] Overwrite root
  - [ ] Commands
    - [ ] Create ???
    - [ ] Add files ???
    - FIXME : not sure what I meant

- [ ] Plugin
  - [ ] Inject Java dependencies
    - Would need to load the dependency
    - Groovy seem to be using the default class loader
  - [ ] Modify some part of the execution of the CLI

- [ ] Secure conf
  - [ ] Application key pass
  - [ ] OS key pass

- [ ] Language / Framework supports
  - [ ] npm

- [ ] Execution
  - [x] Provide a fatJar
    - Note : java need to provide the absolute path of the jar
      - Even if it is on the path
  - [x] Provide a shell executable
  - [ ] Provide a bash
  - [ ] Provide an exe
  - [ ] Provide a linux exe

- [ ] Installation
  - [ ] Provide an MSI
  - [ ] Provide a Linux installer
  - [ ] Fix the .sh system
    - I think I did not add the jar to the path
    - bash scripts are difficult
    - And we cannot use the jar in itself, it will not start from the correct path otherwise

- [ ] CI/CD
  - [ ] Build fatJar after a commit

- [ ] Logging
  - [x] Logger class
  - [x] Logger object
  - [ ] Provide a way to log SSH logs

- [ ] Init
  - [ ] Provide a script initializer
  - [ ] Provide a value file initializer
  - [ ] Provide a script that replace values, to create an example file

- [ ] Templates
  - [ ] Reusable template (npm etc)

## TODO

- Ensure we can pass an argument as a string
- Use a container to SSH into for the examples and/or tests

### Examples

Copying file:

```groovy
var files = new Files()

files.with {
    copyFile "README.md", "dist/README.md"
}
```

Removing file:

```groovy
var files = new Files()

files.rmFile("dist/README.md")
```
