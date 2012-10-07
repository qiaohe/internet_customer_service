package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.message.MessageDao;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.MessageListRequest;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 18/09/12
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class MessageListCommand extends AbstractCommand implements Command {
    private static final String RESULT_MESSAGE_LIST_KEY = "messages";
    @Autowired
    private MessageDao messageDao;

    public MessageListCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        MessageListRequest request = ObjectJsonMapper.getObjectBy(getRequestAsString(), MessageListRequest.class);
        List<Message> messages = messageDao.findBy(request.getConversationId(), request.getPageFrom(), request.getPageSize());
        addResult(RESULT_MESSAGE_LIST_KEY, messages);
        session.write(new ResponseBuilder(this).getResponse());
    }
}
