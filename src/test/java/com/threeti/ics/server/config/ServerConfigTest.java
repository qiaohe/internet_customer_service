package com.threeti.ics.server.config;

import junit.framework.Assert;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/4/12
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerConfigTest {
    @Test
    public void testGet() {
        Assert.assertEquals("127.0.0.1", ServerConfig.getInstance().get("socketServer.host"));
    }

    @Test
    public void testGetImageServerInfo() {
        ImageServerInfo isi = ServerConfig.getInstance().getImageServerInfo();
        Assert.assertEquals("http://192.168.2.91:8080/images/", isi.getBaseUrl());
    }

    @Test
    public  void testSearchServerIndexDir() {
        final String indexDir = ServerConfig.getInstance().getSearchServerIndexDir();
        Assert.assertTrue(StringUtils.isNotEmpty(indexDir));
    }
}
