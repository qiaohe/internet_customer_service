package com.threeti.ics.server.domain.mobile;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class ServiceToken {
    private Long id;
    private MobileDevice device;
    private MobileApp app;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceToken(MobileApp app, MobileDevice device) {
        this.device = device;
        this.app = app;
    }

    public MobileDevice getDevice() {
        return device;
    }

    public void setDevice(MobileDevice device) {
        this.device = device;
    }

    public MobileApp getApp() {
        return app;
    }

    public void setApp(MobileApp app) {
        this.app = app;
    }

    public String getToken() {
        return DigestUtils.md5Hex(new StringBuilder(app.getAppKey()).append(device.getUid()).toString());
    }
   
    public String getVirtualUserId() {
        return "V" + id;
    }
}
