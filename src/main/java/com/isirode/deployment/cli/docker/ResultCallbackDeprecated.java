package com.isirode.deployment.cli.docker;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ResultCallbackDeprecated extends LogContainerResultCallback {

    @Override
    public void onNext(Frame item) {
        String content = new String(item.getPayload(), StandardCharsets.UTF_8);
        switch (item.getStreamType()) {
            case STDERR:
                log.error(content);
                break;
            case RAW:
            case STDIN:
            case STDOUT:
                log.info(content);
                break;
            default:
                log.info(content);
                throw new RuntimeException("Unknown stream type " + item.getStreamType().toString());
        }
    }

}
