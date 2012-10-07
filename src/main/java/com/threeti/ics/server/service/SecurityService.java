package com.threeti.ics.server.service;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
public interface SecurityService {

    public boolean login(String userName, String password);

    public boolean logout();
}
