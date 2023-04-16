package com.isirode.deployment.cli.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DockerUtils {

    public static DockerClient getDefaultClient() {
        DockerClientConfig dockerConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost("tcp://localhost:2375")
            .build();

        return DockerClientBuilder
            .getInstance(dockerConfig)
            .build();
    }

}
