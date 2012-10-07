package com.threeti.ics.server.web;

import com.threeti.ics.server.domain.imageserver.ImageFileName;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 20/09/12
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UploadImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadImageController.class);

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = {"content-type=multipart/form-data"})
    public void uploadImage(byte[] uploadData, Writer writer, HttpServletRequest request) {
        try {
            OutputStream out = new FileOutputStream(new ImageFileName().getFullName());
            IOUtils.copy(new ByteArrayInputStream(uploadData), out);
            out.flush();
            out.close();
            writer.write("file upload success!");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
