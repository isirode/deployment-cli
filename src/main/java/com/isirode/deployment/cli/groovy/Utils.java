package com.isirode.deployment.cli.groovy;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Utils {

    public static Session getSSHSession(String username, String password, String host, Integer port) throws JSchException {
        var session = new JSch().getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    public static Session getSSHSession(String username, String password, String host, Long port) throws JSchException {
        return getSSHSession(username, password, host, port.intValue());
    }

}
