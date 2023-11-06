package com.isirode.deployment.cli.commands;

import com.moandjiezana.toml.Toml;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Slf4j
@CommandLine.Command(version = "1.0.0", mixinStandardHelpOptions = true)
public class DeployCommand implements Callable<Integer> {

    @CommandLine.Option(names = { "-s", "--script" }, required = true, paramLabel = "SCRIPT", description = "the script file")
    File script;

    @CommandLine.Option(names = { "-v", "--values" }, paramLabel = "VALUES", description = "the values file, only TOML is supported for now")
    File values;

    @CommandLine.Option(names = { "-a", "--argument"}, split = ",", description = "arguments to inject in the script")
    String[] arguments;

    @CommandLine.Option(names = { "-w", "--workspace"}, split = ",", description = "workspaces to use to insert values")
    String[] workspaces;

    @Override
    public Integer call() throws Exception {

        log.info("script file is {}", script.getPath());
        if (values != null) {
            log.info("value file is {}", values.getPath());
        }

        var workingDir = System.getProperty("user.dir");
        if (workingDir == null) {
            throw new RuntimeException("user.dir is not set");
        }
        log.info("Working directory is '{}", workingDir);

        var userHome = System.getProperty("user.home");
        if (userHome == null) {
            throw new RuntimeException("user.home is not set");
        }
        log.info("User home is '{}", userHome);

        Binding binding = new Binding();

        if (values != null) {
            Toml toml = new Toml().read(values);
            toml.entrySet()
                .forEach((entry) -> {
                    binding.setVariable(entry.getKey(), entry.getValue());
                });
        } else {
            log.warn("No value file was passed");
        }
        if (arguments != null && arguments.length != 0) {
            for (var argument : arguments) {
                var result = argument.split("=");
                if (result.length != 2) {
                    log.warn("Argument '{}' is not correctly formatted", String.join(" ", result));
                    continue;
                }
                binding.setVariable(result[0], result[1]);
            }
        }
        binding.setVariable("scriptDir", new File(script.getAbsoluteFile().getParent()));

        Logger logger = LoggerFactory.getLogger(script.getName());
        binding.setVariable("log", logger);

        // Info : this is not working
        // System.setProperty("user.dir", script.getAbsoluteFile().getParent());

        // Info : deprecated, replacement not documented
        // var logContainerResultCallback = new LogContainerResultCallback();

        // FIXME : create a module for all this below

        var userGlobalWorkspace = userHome + File.separator + ".dcli";
        var userGlobalWorkspacePath = Paths.get(userGlobalWorkspace);
        if (Files.exists(userGlobalWorkspacePath)) {
            log.info("User workspace dir exists");
            var globalValues = userGlobalWorkspace + File.separator + "global.toml";
            var globalValuesPath = Paths.get(globalValues);
            if (Files.exists(globalValuesPath) && Files.isRegularFile(globalValuesPath)) {
                log.info("Global values file exist");

                var globalValuesFile = globalValuesPath.toFile();
                Toml globalValuesToml = new Toml().read(globalValuesFile);
//                var globalValuesMap = globalValuesToml.toMap();
                // Info : we insert globally for now
                globalValuesToml.entrySet()
                        .forEach((entry) -> {
                            binding.setVariable(entry.getKey(), entry.getValue());
                        });

                // TODO : complete workspace system
                // Would complexify it though
                if (workspaces != null) {
                    for (var workspace : workspaces) {
                        var workspaceDir = userGlobalWorkspace + File.separator + workspace;
                        var workspaceDirPath = Paths.get(workspaceDir);
                        if (Files.exists(workspaceDirPath) ) {
                            var workspaceValues = workspaceDir + File.separator + "values.toml";
                            var workspaceValuesPath = Paths.get(workspaceValues);
                            if (Files.exists(workspaceValuesPath) && Files.isRegularFile(workspaceValuesPath)) {
                                log.info("Workspace '{}' values file exist", workspace);

                                var workspaceValuesFile = workspaceValuesPath.toFile();
                                Toml workspaceValuesToml = new Toml().read(workspaceValuesFile);
                                var globalValuesMap = workspaceValuesToml.toMap();
                                binding.setVariable(workspace, globalValuesMap);
                            } else {
                                log.warn("Workspace '{}' values file does not exist", workspace);
                            }
                        } else {
                            log.warn("Provided workspace '{}' does not exist", workspace);
                        }
                    }
                }

            } else {
                log.info("Global values file is not present");
            }
        } else {
            log.info("User workspace dir is not present");
        }

        GroovyShell shell = new GroovyShell(binding);

        Object value = shell.evaluate(script);

        return 0;
    }
}
