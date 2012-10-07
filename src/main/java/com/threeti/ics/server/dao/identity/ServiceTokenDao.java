package com.threeti.ics.server.dao.identity;

import com.threeti.ics.server.dao.core.AbstractGenericDaoImpl;
import com.threeti.ics.server.dao.core.GenericDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.domain.mobile.ServiceToken;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "serviceTokenDao")

public class ServiceTokenDao extends AbstractGenericDaoImpl<ServiceToken> implements GenericDao<ServiceToken> {
    public ServiceTokenDao() {
        super(ServiceToken.class);
    }

    protected Long getId(ServiceToken token) {
        String id = template.opsForValue().get(KeyUtils.serviceTokenOfToken(token.getToken()));
        if (id != null) return Long.valueOf(id);
        return token.getId() == null ? idCounter.incrementAndGet() : token.getId();
    }

    @SuppressWarnings("unchecked")
    public ServiceToken create(final ServiceToken serviceToken) {
        final Long id = getId(serviceToken);
        serviceToken.setId(id);
        template.opsForHash().putAll(getIdKey(id), getProperties(serviceToken));
        template.opsForValue().set(KeyUtils.serviceTokenOfToken(serviceToken.getToken()), id.toString());
        entities.addFirst(id.toString());
        return serviceToken;
    }

    public Long findBy(String token) {
        final String id = template.opsForValue().get(KeyUtils.serviceTokenOfToken(token));
        return Long.valueOf(id);
    }
}
