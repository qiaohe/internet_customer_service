package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.domain.builder.ResponseBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public abstract class AbstractCommand implements Command {
    protected static final String RESULT_CONVERSATION_ID_KEY = "conversation";

    private Object request;
    private HashMap<String, Object> result = new HashMap<String, Object>();

    public AbstractCommand() {
    }

    public AbstractCommand(Object request) {
        this.request = request;
    }

    @Override
    public String getCommandName() {
        final String cn = this.getClass().getSimpleName();
        return cn.replace("Command", StringUtils.EMPTY).toLowerCase();
    }

    public Object getRequest() {
        return request;
    }

    public String getRequestAsString() {
        return request != null ? request.toString() : null;
    }

    public HashMap<String, Object> getResult() {
        return result;
    }

    protected void addResult(final String key, final Object value) {
        result.put(key, value);
    }

    protected void addResult(Map<String, Object> p) {
        result.putAll(p);
    }

    protected String getResults() {
        return new ResponseBuilder(this).getResponse();
    }

    @Override
    public void execute(IoSession session) throws IOException {

    }


}
