package com.threeti.ics.server.domain.imageserver;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/4/12
 * Time: 9:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public final class ImageServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServer.class);

    public void start() {
    }

    public void stop() {
    }

    public boolean isAvailable() {
        return true;
    }

    public List<String> search(final String keyWord) {
        return null;
    }

    public synchronized String store(final String imageBase64String) {
        try {
            byte[] bytes = Base64.decodeBase64(imageBase64String);
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(bytes));
            ImageFileName imageFileName = new ImageFileName();
            ImageIO.write(bufImg, ImageFileName.DEFAULT_EXTENSION, new File(imageFileName.getFullName()));
            return imageFileName.getUrl();
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }
}
