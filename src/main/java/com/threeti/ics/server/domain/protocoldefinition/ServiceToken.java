package com.threeti.ics.server.domain.protocoldefinition;

import com.threeti.ics.server.domain.mobile.MobileApp;
import com.threeti.ics.server.domain.mobile.MobileDevice;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
public class ServiceToken {
    private String macAddress;
    private String mobileAppName;

    public ServiceToken() {
    }

    public ServiceToken(String macAddress, String mobileAppName) {
        this();
        this.macAddress = macAddress;
        this.mobileAppName = mobileAppName;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setMobileAppName(String mobileAppName) {
        this.mobileAppName = mobileAppName;
    }

    public String getTokenAsString() {
        return DigestUtils.md5Hex(toString());
    }

    @Override
    public String toString() {
        return macAddress + mobileAppName;
    }
}
