package com.isirode.deployment.cli.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Sftp {

    final Session session;

    // TODO : put dir
    // TODO : put dir using zipping

    public void put(String localFileName, String remoteFileName) throws JSchException, SftpException {
        ChannelSftp channelSftp = null;
        try {
            log.info(session.getHost());
            log.info(session.getUserName());
            log.info(String.valueOf(session.getPort()));
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            log.info(localFileName);
            log.info(remoteFileName);

            channelSftp.put(localFileName, remoteFileName);
        } catch (JSchException exception) {
            log.error("A SSH exception occurred", exception);
            throw exception;
        } catch (SftpException exception) {
            log.error("A SFTP exception occurred", exception);
            throw exception;
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
        }
    }

}
