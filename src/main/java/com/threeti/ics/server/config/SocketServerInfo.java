package com.threeti.ics.server.config;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/4/12
 * Time: 12:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocketServerInfo {
    private final String host;
    private final int port;
    private final int threadPoolSize;

   public SocketServerInfo(String host, int port, int threadPoolSize) {
        this.host = host;
        this.port = port;
        this.threadPoolSize = threadPoolSize;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }
}

