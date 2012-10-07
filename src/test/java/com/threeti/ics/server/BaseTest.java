package com.threeti.ics.server;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/4/12
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:testDispatcher-servlet.xml",
        "classpath:testApplicationContext.xml"})
public class BaseTest {
    @Autowired
    protected ApplicationContext applicationContext;

    @Test
    public void testApplicationContext() {
        Assert.assertNotNull(applicationContext);
    }
}
