package com.threeti.ics.server.service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 24/09/12
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */
public interface MessageTextTemplateService {
    public List<String> getGroups(String appKey);

    public Map<String, List<String>> getAll(String appKey);
    
    public List<String> getByGroup(String appKey, String groupName);

    public void addTemplate(String appKey, String group, String content);

    public void removeTemplate(String appKey, String s, String template);

}
