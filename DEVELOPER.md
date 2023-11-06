# DEVELOPER

## Philosophy

The way I add the features is on a "need to have" basis.

So, if a feature is listed, but I do not need it yet, it will probably not be added.

If you need it, you can contribute or request it (maybe I will take a look).

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
    - [x] Use multi object file
  - [ ] HOCON
  - [ ] JSON
  - [ ] YAML
  - [ ] Multiple files
  - [ ] Use script directory as root
  - [ ] Specify a directory as root

- [ ] Passing arguments
  - [x] Via -a
  - [ ] Via additional arguments (extra arguments of the initial command)
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
  - [ ] Easy way to use working directory

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
  - [ ] Values
    - [x] Global
    - [x] Workspace
    - [ ] Overwrite root
    - [ ] Use all files of extension (*.toml)
  - [ ] Configuration
    - [ ] Global
      - [ ] Choose wether or not put the global values in an object or not
      - [ ] Choose global values object name
      - [ ] Auto-load workspaces
      - [ ] Recursive workspaces
  - [ ] Scripts
    - Would be common scripts to use with variables
    - [ ] Pass arguments
  - [ ] Commands
    - [ ] Create ???
    - [ ] Add files ???
    - FIXME : not sure what I meant (I think it was pre-made commands)

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
  - [ ] Provide a way to log (or not) SSH logs

- [ ] Init (to create the files)
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
