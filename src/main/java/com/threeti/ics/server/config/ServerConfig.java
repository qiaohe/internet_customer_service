package com.threeti.ics.server.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12-8-30
 * Time: 下午12:17
 * To change this templa
 * te use File | Settings | File Templates.
 */
public final class ServerConfig {
    private static final ServerConfig INSTANCE = new ServerConfig();
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfig.class);
    private static Configuration CONFIG;

    static {
        try {
            CONFIG = new PropertiesConfiguration("ics.properties");
        } catch (ConfigurationException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private ServerConfig() {
    }

    public static ServerConfig getInstance() {
        return INSTANCE;
    }

    public String get(final String key) {
        return CONFIG.getString(key);
    }

    public int getInt(final String key) {
        return CONFIG.getInt(key);
    }

    public boolean getBoolean(final String key) {
        return CONFIG.getBoolean(key);
    }

    public SocketServerInfo getSocketServerInfo() {
        return new SocketServerInfo(
                get("socketServer.host"),
                getInt("socketServer.port"),
                getInt("socketServer.threadPoolSize"));
    }

    public DBInfo getDBInfo() {
        return new DBInfo(
                get("db.host"),
                getInt("db.port"),
                getBoolean("db.usePool"));
    }

    public ImageServerInfo getImageServerInfo() {
        return new ImageServerInfo(
                get("imageServer.base.dir"),
                get("imageServer.host"),
                getInt("imageServer.port"),
                get("imageServer.context")
        );
    }

    public String getSearchServerIndexDir() {
        return get("searchServer.index.dir");
    }
}
