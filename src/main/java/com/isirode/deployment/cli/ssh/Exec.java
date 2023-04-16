package com.isirode.deployment.cli.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.ByteArrayOutputStream;

@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class Exec {

    final Session session;
    boolean throwOnError;

    void run(String command) throws JSchException, InterruptedException {
        ChannelExec channel = null;
        try {
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);

            val responseStream = new ByteArrayOutputStream();
            val errorStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.setErrStream(errorStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            String responseString = new String(responseStream.toByteArray());
            System.out.println(responseString);

            String errorString = new String(errorStream.toByteArray());
            if (!errorString.isEmpty()) {
                if (throwOnError) {
                    throw new RuntimeException(errorString);
                } else {
                    log.error(errorString);
                }
            }
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

}
