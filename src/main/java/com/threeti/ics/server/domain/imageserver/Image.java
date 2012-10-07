package com.threeti.ics.server.domain.imageserver;

import org.apache.commons.io.FilenameUtils;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/4/12
 * Time: 9:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Image {
    private int height;
    private int width;
    private String name;

    public Image(int height, int width, String name) {
        this.height = height;
        this.width = width;
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseName() {
       return FilenameUtils.getBaseName(name);
    }
}
