package com.isirode.deployment.cli.logger;

import com.jcraft.jsch.Logger;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class JschLogger implements Logger {

    private int level = INFO;

    @Override
    public boolean isEnabled(int level) {
        return level >= this.level;
    }

    @Override
    public void log(int level, String message) {
        if (!isEnabled(level)) return;
        switch (level) {
            case DEBUG:
                log.debug(message);
                break;
            case INFO:
                log.info(message);
                break;
            case WARN:
                log.warn(message);
                break;
            case ERROR:
            case FATAL:
                // Info : there is fatal logging level in Slf4j
                log.error(message);
                break;
        }
    }

}
