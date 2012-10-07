package com.threeti.ics.server.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeti.ics.server.BaseTest;
import com.threeti.ics.server.common.ObjectJsonMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/2/12
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomerServiceControllerTest extends BaseTest {
    AnnotationMethodHandlerAdapter adapter;
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    ObjectMapper mapper;
    @Autowired
    private CustomerServiceController customerServiceController;

    @Before
    public void setUp() {
        adapter = new AnnotationMethodHandlerAdapter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        mapper = new ObjectMapper();
    }

    @Test
    public void testChangePassword() {
        request.setContentType(MediaType.APPLICATION_JSON.toString());
        request.setMethod("GET");
        request.setRequestURI("/changePassword/abcdef0001");
        request.addParameter("newPwd", "0000");
        request.addParameter("oldPwd", "123456");
        HttpMessageConverter[] messageConverters = {new MappingJackson2HttpMessageConverter()};
        adapter.setMessageConverters(messageConverters);
        try {
            adapter.handle(request, response, customerServiceController);
            ResponseResult pr = ObjectJsonMapper.getObjectBy(response.getContentAsString(), ResponseResult.class);
            assertEquals(200, response.getStatus());
            assertEquals(pr.getCode(), "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
