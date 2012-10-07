package com.threeti.ics.server.web;

import com.threeti.ics.server.service.MessageTextTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 24/09/12
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MessageTextTemplateController {
    @Autowired
    private MessageTextTemplateService messageTextTemplateService;

    @RequestMapping(value = "/textTemplate/{appKey}/{group}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getByGroup(@PathVariable String appKey, @PathVariable String group) throws UnsupportedEncodingException {
        return messageTextTemplateService.getByGroup(appKey, new String(group.getBytes("ISO-8859-1"), "UTF-8"));
    }

    @RequestMapping(value = "/textTemplate/{appKey}", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, List<String>> getALl(@PathVariable String appKey) {
        return messageTextTemplateService.getAll(appKey);
    }

    @RequestMapping(value = "/textTemplate/{appKey}/groups", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getGroups(@PathVariable String appKey) {
        return messageTextTemplateService.getGroups(appKey);
    }

    @RequestMapping(value = "/textTemplate/{appKey}/{group}/add", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseResult addTemplateForGroup(@PathVariable String appKey, @PathVariable String group, @RequestParam String template) throws UnsupportedEncodingException {
        messageTextTemplateService.addTemplate(appKey, new String(group.getBytes("ISO-8859-1"), "UTF-8"), new String(template.getBytes("ISO-8859-1"), "UTF-8"));
        return new ResponseResult(ResponseResult.SUCCESS_CODE, ResponseResult.SUCCESS);
    }

    @RequestMapping(value = "/textTemplate/{appKey}/{group}/remove", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseResult removeTemplateForGroup(@PathVariable String appKey, @PathVariable String group, @RequestParam String template) throws UnsupportedEncodingException {
        messageTextTemplateService.removeTemplate(appKey, new String(group.getBytes("ISO-8859-1"), "UTF-8"), new String(template.getBytes("ISO-8859-1"), "UTF-8"));
        return new ResponseResult(ResponseResult.SUCCESS_CODE, ResponseResult.SUCCESS);
    }

}
