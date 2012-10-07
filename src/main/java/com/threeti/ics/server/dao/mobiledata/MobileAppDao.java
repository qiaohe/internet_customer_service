package com.threeti.ics.server.dao.mobiledata;

import com.threeti.ics.server.dao.core.AbstractGenericDaoImpl;
import com.threeti.ics.server.dao.core.GenericDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.domain.mobile.MobileApp;
import com.threeti.ics.server.domain.protocoldefinition.CustomerServiceStatus;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUserRole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "mobileAppDao")
@SuppressWarnings("unchecked")
public class MobileAppDao extends AbstractGenericDaoImpl<MobileApp> implements GenericDao<MobileApp> {

    public MobileAppDao() {
        super(MobileApp.class);
    }

    protected Long getId(MobileApp app) {
        String id = template.opsForValue().get(KeyUtils.mobileDeviceUid(app.getAppKey()));
        if (id != null) return Long.valueOf(id);
        return app.getId() == null ? idCounter.incrementAndGet() : app.getId();
    }

    public MobileApp findBy(String appKey) {
        final String id = template.opsForValue().get(KeyUtils.mobileAppKey(appKey));
        return findBy(Long.valueOf(id));
    }

    public MobileApp create(MobileApp app) {
        final Long id =getId(app);
        app.setId(id);
        template.opsForHash().putAll(getIdKey(id), getProperties(app));
        template.opsForValue().set(KeyUtils.mobileAppKey(app.getAppKey()), id.toString());
        entities.addFirst(id.toString());
        return app;
    }

    @Override
    public MobileApp findBy(Long id) {
        MobileApp result = new MobileApp();
        result.setId(id);
        result.setAppKey(template.opsForHash().get(getIdKey(id), "appKey").toString());
        result.setAppName(template.opsForHash().get(getIdKey(id), "appName").toString());
        return result;
    }

    public MobileApp update(MobileApp app) {
        final String id = String.valueOf(getId(app));
        template.opsForHash().putAll(id, getProperties(app));
        template.delete(KeyUtils.mobileAppKey(app.getAppKey()));
        template.opsForValue().set(KeyUtils.mobileAppKey(app.getAppKey()), id);
        return app;
    }

    public boolean remove(MobileApp app) {
        if (!entities.contains(app.getId().toString())) throw new IllegalArgumentException("record doest not exist.");
        template.opsForHash().delete("id", app.getId());
        template.opsForHash().delete("appKey", app.getAppKey());
        template.opsForHash().delete("appName", app.getAppName());
        template.delete(getIdKey(app.getId()));
        template.delete(KeyUtils.mobileAppKey(app.getAppKey()));
        entities.remove(app.getId().toString());
        return true;
    }
}
