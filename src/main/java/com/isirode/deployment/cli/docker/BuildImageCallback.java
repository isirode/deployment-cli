package com.isirode.deployment.cli.docker;

import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuildImageCallback extends BuildImageResultCallback {

    @Override
    public void onNext(BuildResponseItem item) {
        super.onNext(item);
        if (item.getStream() != null) {
            log.info(item.getStream());
        } else {
            log.info(String.format("%s: %s", item.getId(), item.getStatus()));
        }
        if (item.getErrorDetail() != null) {
            log.error(item.getErrorDetail().getMessage());
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        log.info("Build done");
    }

    @SneakyThrows
    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        if (throwable != null) {
            log.error("An error occurred", throwable);
            throw throwable;
        }
    }
}
