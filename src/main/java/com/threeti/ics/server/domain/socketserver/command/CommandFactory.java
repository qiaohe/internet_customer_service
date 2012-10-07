package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.domain.protocoldefinition.parser.ProtocolParser;
import org.apache.commons.lang3.StringUtils;

import static com.threeti.ics.server.domain.protocoldefinition.parser.ProtocolTypeEnum.getFromName;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class CommandFactory {
    private static final CommandFactory INSTANCE = new CommandFactory();

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    public Command createCommand(final Object message) {
        if (message == null || StringUtils.isEmpty(message.toString()))
            throw new IllegalArgumentException("message Can not be empty");
        ProtocolParser parser = new ProtocolParser(message.toString());
        switch (getFromName(parser.getType())) {
            case SDKVERIFICATION:
                return new SdkVerificationCommand(parser.getData());
            case BUILDCONVERSATION:
                return new BuildConversationCommand(parser.getData());
            case MESSAGETRANSFER:
                return new MessageTransferCommand(parser.getData());
            case CUSTOMERSERVICELOGIN:
                return new CustomerServiceLoginCommand(parser.getData());
            case SESSIONOPERATION:
                return new SessionOperationCommand(parser.getData());
            case ONLINECUSTOMERSERVICELIST:
                return new OnlineCustomerServiceListCommand(parser.getData());
            case MESSAGELIST:
                return new MessageListCommand(parser.getData());
            case QUEUECONVERSATIONLIST:
                return new QueueConversationListCommand(parser.getData());
            case UPLOADIMAGE:
                return new UploadImageCommand(parser.getData());
            case CUSTOMERSERVICESTATUSCHANGE:
                return new CustomerServiceStatusChangeCommand(parser.getData());
            case VISITORCONVERSATION:
                return new VisitorConversationCommand(parser.getData());
            case MESSAGESTATUSCHANGE:
                return new MessageStatusChangeCommand(parser.getData());
        }
        return null;
    }
}
