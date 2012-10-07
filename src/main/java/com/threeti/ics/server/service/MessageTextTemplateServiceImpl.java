package com.threeti.ics.server.service;

import com.threeti.ics.server.dao.texttemplate.MessageTextTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 24/09/12
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "messageTextTemplateService")
public class MessageTextTemplateServiceImpl implements MessageTextTemplateService {
    @Autowired
    private MessageTextTemplateDao messageTextTemplateDao;

    @Override
    public List<String> getGroups(String appKey) {
        return messageTextTemplateDao.getGroups(appKey);
    }

    @Override
    public Map<String, List<String>> getAll(String appKey) {
        return messageTextTemplateDao.getAll(appKey);
    }

    @Override
    public List<String> getByGroup(String appKey, String groupName) {
        return messageTextTemplateDao.getByGroup(appKey, groupName);
    }

    @Override
    public void addTemplate(String appKey, String group, String template) {
        List<String> templates = new ArrayList<String>();
        templates.add(template);
        messageTextTemplateDao.add(appKey, group, templates);
    }

    @Override
    public void removeTemplate(String appKey, String group, String template) {
        List<String> templates = new ArrayList<String>();
        templates.add(template);
        messageTextTemplateDao.remove(appKey, group, templates);
    }
}
