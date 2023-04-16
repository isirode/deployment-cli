package com.isirode.deployment.cli.local;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Builder
public class ShellCommandDeprecated {

    private static final boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    @Builder.Default
    final List<String> rootCommand = isWindows ? List.of("cmd", "/c") : List.of("sh");

    @Builder.Default
    boolean useRootCommand = true;

    @Builder.Default
    File workingDir = new File(System.getProperty("user.dir"));

    public void exec(String command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        if (useRootCommand) {
            var execCommand = Stream.concat(rootCommand.stream(), Stream.of(command)).collect(Collectors.toList());
            builder.command(execCommand);
        } else {
            builder.command(command);
        }

        builder.directory(workingDir);

        Process process = builder.start();

        // TODO : use a future here
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            log.info(line);
        }

        // Warn : this is blocking a thread
        int exitCode = process.waitFor();

        log.info("Exit code: {}", exitCode);
    }

}
