package com.threeti.ics.server.listener;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 19/09/12
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public interface MessageDelegate {
    void handleMessage(String message) throws IOException;
}
