package com.threeti.ics.server.service;

import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "securityService")
public class SecurityServiceImpl implements SecurityService {
    @Override
    public boolean login(String userName, String password) {
        return ("admin".equalsIgnoreCase(userName) && "admin".equalsIgnoreCase(password));
    }

    @Override
    public boolean logout() {
        return false;
    }
}
