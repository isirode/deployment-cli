package com.isirode.deployment.cli.commands;

import com.moandjiezana.toml.Toml;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
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
                    log.warn("Argument '{}' is not correctly formatted", result);
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

        GroovyShell shell = new GroovyShell(binding);

        Object value = shell.evaluate(script);

        return 0;
    }
}
