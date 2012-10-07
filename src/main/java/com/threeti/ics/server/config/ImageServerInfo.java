package com.threeti.ics.server.config;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/4/12
 * Time: 12:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageServerInfo {
    private final String baseDir;
    private final String host;
    private final int port;
    private final String context;

    public ImageServerInfo(String baseDir, String host, int port, String context) {
        this.baseDir = baseDir;
        this.host = host;
        this.port = port;
        this.context = context;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getContext() {
        return context;
    }

    public String getBaseUrl() {
       return String.format("http://%s:%d/%s", host, port, context);
    }
}
