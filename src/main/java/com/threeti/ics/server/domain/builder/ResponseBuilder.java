package com.threeti.ics.server.domain.builder;

import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.socketserver.command.Command;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 17/09/12
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public class ResponseBuilder {
    private Command command;

    public ResponseBuilder(Command command) {
        this.command = command;
    }

    public String getResponse() {
        CommonObject result = new CommonObject(command.getCommandName());
        result.setProperties(command.getResult());
        return result.toJsonString();
    }
}
