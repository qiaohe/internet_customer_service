package com.threeti.ics.server.dao.mobiledata;

import com.threeti.ics.server.dao.core.AbstractGenericDaoImpl;
import com.threeti.ics.server.dao.core.GenericDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.domain.mobile.MobileDevice;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "mobileDeviceDao")
public class MobileDeviceDao extends AbstractGenericDaoImpl<MobileDevice> implements GenericDao<MobileDevice> {
    public MobileDeviceDao() {
        super(MobileDevice.class);
    }

    protected MobileDeviceDao(Class<MobileDevice> type) {
        super(type);
    }

    protected Long getId(MobileDevice device) {
        String id = template.opsForValue().get(KeyUtils.mobileDeviceUid(device.getUid()));
        if (id != null) return Long.valueOf(id);
        return device.getId() == null ? idCounter.incrementAndGet() : device.getId();
    }

    @SuppressWarnings("unchecked")
    public MobileDevice create(MobileDevice device) {
        final Long id = getId(device);
        device.setId(id);
        template.opsForHash().putAll(getIdKey(id), getProperties(device));
        template.opsForValue().set(KeyUtils.mobileDeviceUid(device.getUid()), id.toString());
        entities.addFirst(id.toString());
        return device;
    }

    @SuppressWarnings("unchecked")
    public MobileDevice update(MobileDevice device) {
        final String id = String.valueOf(getId(device));
        template.opsForHash().putAll(id, getProperties(device));
        template.delete(KeyUtils.mobileDeviceUid(device.getUid()));
        template.opsForValue().set(KeyUtils.mobileDeviceUid(device.getUid()), id);
        return device;
    }

    public boolean remove(MobileDevice device) {
        if (!entities.contains(device.getId().toString()))
            throw new IllegalArgumentException("record doest not exist.");
        template.opsForHash().delete("id", device.getId());
        template.opsForHash().delete("cpu", device.getCpu());
        template.opsForHash().delete("uid", device.getUid());
        template.opsForHash().delete("resolution", device.getResolution());
        template.opsForHash().delete("model", device.getModel());
        template.opsForHash().delete("network", device.getNetwork());
        template.opsForHash().delete("carrier", device.getCarrier());
        template.delete(getIdKey(device.getId()));
        template.delete(KeyUtils.mobileDeviceUid(device.getUid()));
        entities.remove(device.getId().toString());
        return true;
    }
}
