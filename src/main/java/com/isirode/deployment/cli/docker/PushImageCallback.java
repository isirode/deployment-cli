package com.isirode.deployment.cli.docker;

import com.github.dockerjava.api.model.PushResponseItem;
import com.github.dockerjava.core.command.PushImageResultCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

// FIXME : same code as BuildImageCallback
@Slf4j
public class PushImageCallback extends PushImageResultCallback {

    @Override
    public void onNext(PushResponseItem item) {
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
