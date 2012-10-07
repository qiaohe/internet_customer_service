package com.threeti.ics.server.domain.protocoldefinition.commandrequest;

import com.threeti.ics.server.dao.mobiledata.MobileAppDao;
import com.threeti.ics.server.domain.mobile.MobileApp;
import com.threeti.ics.server.domain.mobile.MobileDevice;
import com.threeti.ics.server.domain.mobile.ServiceToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12/09/12
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class SdkVerificationRequest {
    public static final String UID = "uid";
    @Autowired
    private MobileAppDao mobileAppDao;
    private String appKey;
    private String cpu;
    private String uid;
    private String resolution;
    private String model;
    private String network;
    private String carrier;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public MobileDevice getMobileDevice() {
        MobileDevice result = new MobileDevice();
        result.setCarrier(carrier);
        result.setUid(uid);
        result.setResolution(resolution);
        result.setCpu(cpu);
        result.setModel(model);
        result.setNetwork(network);
        return result;
    }

    public MobileApp getMobileApp() {
        return mobileAppDao.findBy(appKey);
    }

    public ServiceToken getServiceToken() {
        return new ServiceToken(getMobileApp(), getMobileDevice());
    }

    public String getToken() {
        return getServiceToken().getToken();
    }
    
    public boolean isFromWeb() {
        return StringUtils.isEmpty(cpu) && StringUtils.isEmpty(model);
    }
}
