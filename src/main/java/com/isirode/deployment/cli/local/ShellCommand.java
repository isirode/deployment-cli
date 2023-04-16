package com.isirode.deployment.cli.local;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Builder
public class ShellCommand {

    private static final boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    @Builder.Default
    final List<String> rootCommand = isWindows ? List.of("cmd", "/c") : List.of("sh");

    @Builder.Default
    boolean useRootCommand = true;

    @Builder.Default
    File workingDir = new File(System.getProperty("user.dir"));

    public void exec(List<String> command) throws IOException, InterruptedException, TimeoutException {
        List<String> execCommand = new ArrayList<>();
        if (useRootCommand) {
            execCommand = Stream.concat(rootCommand.stream(), command.stream()).collect(Collectors.toList());
        } else {
            execCommand = command;
        }

        new ProcessExecutor()
                .command(execCommand)
                .directory(workingDir)
                .redirectOutput(Slf4jStream.ofCaller().asInfo())
                .redirectError(Slf4jStream.ofCaller().asInfo())
                .exitValueNormal()
                .destroyOnExit()
                .execute();
    }

    public void exec(String[] command) throws IOException, InterruptedException, TimeoutException {
        exec(Arrays.asList(command));
    }

    public void exec(String command) throws IOException, InterruptedException, TimeoutException {
        exec(command.split(","));
    }

}
