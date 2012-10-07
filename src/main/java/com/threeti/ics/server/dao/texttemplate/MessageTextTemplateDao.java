package com.threeti.ics.server.dao.texttemplate;

import com.threeti.ics.server.dao.core.KeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 24/09/12
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "messageTextTemplateDao")
public class MessageTextTemplateDao {
    private static final String KEY_DELIMITER = ":";
    @Autowired
    private RedisTemplate<String, String> template;

    private static String getGroup(final String key) {
        return StringUtils.substringAfterLast(key, KEY_DELIMITER);
    }

    public List<String> getGroups(String appKey) {
        List<String> result = new ArrayList<String>();
        for (String k : template.keys(KeyUtils.textTemplateAppKeyAndGroupKeyPrefix(appKey))) {
            result.add(getGroup(k));
        }
        return result;
    }

    public List<String> getByGroup(String appKey, String groupName) {
        return new ArrayList<String>(template.opsForSet().members(KeyUtils.textTemplateAppKeyAndGroupKey(appKey, groupName)));
    }

    public Map<String, List<String>> getAll(String appKey) {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        for (String k : template.keys(KeyUtils.textTemplateAppKeyAndGroupKeyPrefix(appKey))) {
            result.put(getGroup(k), new ArrayList<String>(template.opsForSet().members(k)));
        }
        return result;
    }

    public void add(String appKey, String group, List<String> templates) {
        for (String t : templates) {
            template.opsForSet().add(KeyUtils.textTemplateAppKeyAndGroupKey(appKey, group), t);
        }
    }

    public void remove(String appKey, String group, List<String> templates) {
        for (String t : templates) {
            template.opsForSet().remove(KeyUtils.textTemplateAppKeyAndGroupKey(appKey, group), t);
        }
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:\\working\\projects\\ics\\web\\WEB-INF\\applicationContext.xml");
        MessageTextTemplateDao dao = ctx.getBean("messageTextTemplateDao", MessageTextTemplateDao.class);
        List<String> a = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            a.add("helloworld" + i);
        }
        dao.add("8890fgdkj90fdg89f88", "group1", a);
        for (String s : dao.getGroups("8890fgdkj90fdg89f88")) {
            System.out.println(s);
        }


    }

}
