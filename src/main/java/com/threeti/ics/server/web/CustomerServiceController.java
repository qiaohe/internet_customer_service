package com.threeti.ics.server.web;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.domain.imageserver.ImageFileName;
import com.threeti.ics.server.service.CustomerServiceUserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.jws.WebParam;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CustomerServiceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadImageController.class);


    @Autowired
    private CustomerServiceUserService customerServiceUserService;

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/changePassword/{userName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseResult updatePassword(@PathVariable String userName, @WebParam String oldPwd, @WebParam String newPwd) {
        return customerServiceUserService.changePassword(userName, oldPwd, newPwd);
    }

    @RequestMapping(value = "/changeNickName/{userName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseResult updateNickName(@PathVariable String userName, @WebParam String nickName) throws UnsupportedEncodingException {
        customerServiceUserService.changeNickName(userName, new String(nickName.getBytes("ISO-8859-1"), "UTF-8"));
        return new ResponseResult(ResponseResult.SUCCESS_CODE, ResponseResult.SUCCESS);
    }

    @RequestMapping(value = "/changeHeadPortrait/{userName}", method = RequestMethod.POST, headers = {"content-type=multipart/form-data"})
    public
    @ResponseBody
    ResponseResult updateHeadPortrait(@PathVariable String userName, byte[] headPortrait) throws FileNotFoundException {
        try {
            ImageFileName imageFileName = new ImageFileName(userName);
            OutputStream out = new FileOutputStream(imageFileName.getFullName());
            IOUtils.copy(new ByteArrayInputStream(headPortrait), out);
            out.flush();
            out.close();
            customerServiceUserService.changeHeadPortrait(userName, imageFileName.getUrl());
            return new ResponseResult(ResponseResult.SUCCESS_CODE, ResponseResult.SUCCESS);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
