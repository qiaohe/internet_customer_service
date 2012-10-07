package com.threeti.ics.server.domain.imageserver;

import com.threeti.ics.server.config.ImageServerInfo;
import com.threeti.ics.server.config.ServerConfig;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 22/09/12
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class ImageFileName {
    public static final String DEFAULT_EXTENSION = ".jpg";

    private static final int DEFAULT_LENGTH = 20;
    private static final String FILENAME_PATTERN = "%s.%s";

    private String name;

    public ImageFileName() {
        name = RandomStringUtils.randomAlphanumeric(DEFAULT_LENGTH);
    }

    public ImageFileName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return String.format(FILENAME_PATTERN, name, DEFAULT_EXTENSION);
    }

    public String getFileName(final String ext) {
        return String.format(FILENAME_PATTERN, name, ext);
    }

    private ImageServerInfo getImageServerInfo() {
        return ServerConfig.getInstance().getImageServerInfo();
    }

    public String getFullName() {
        return getImageServerInfo().getBaseDir() + getFileName();
    }

    public String getUrl() {
        return getImageServerInfo().getBaseUrl() + getFileName();
    }
}