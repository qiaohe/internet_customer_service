package com.threeti.ics.server.config;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/4/12
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBInfo {
    private final String host;
    private final int port;
    private final boolean usePool;

    public DBInfo(String host, int port, boolean usePool) {
        this.host = host;
        this.port = port;
        this.usePool = usePool;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isUsePool() {
        return usePool;
    }

    public String getUrl() {
        return null;
    }
}
