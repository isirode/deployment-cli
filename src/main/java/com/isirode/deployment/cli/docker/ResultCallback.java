package com.isirode.deployment.cli.docker;

import com.github.dockerjava.api.model.Frame;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

// TODO : implement this class correctly
// It needs to have a blocking method which does not return until complete and/or close are called
// Like a promise which would be resolved at this point
@Slf4j
public class ResultCallback implements com.github.dockerjava.api.async.ResultCallback<Frame> {

    @Override
    public void onStart(Closeable closeable) {
        log.info("Started");
    }

    @Override
    public void onNext(Frame object) {
        log.info("Next: {}", object);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("An error occurred", throwable);
    }

    @Override
    public void onComplete() {
        log.info("Complete");
    }

    @Override
    public void close() throws IOException {
        log.info("closing");
    }

}
