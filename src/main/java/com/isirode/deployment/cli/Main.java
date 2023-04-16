package com.isirode.deployment.cli;

import com.isirode.deployment.cli.commands.DeployCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
public class Main {
    public static void main(String... args) {

        // FIXME : this is for local development
        // Maybe use another name
        if (System.getenv("INTELLIJ") != null) {
            log.info("Args: {}", String.join(" ", args));
        }

        CommandLine cmd = new CommandLine(new DeployCommand());

        System.exit(cmd.execute(args));
    }
}
