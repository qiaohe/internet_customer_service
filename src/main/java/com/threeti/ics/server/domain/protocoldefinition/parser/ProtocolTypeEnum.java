package com.threeti.ics.server.domain.protocoldefinition.parser;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12/09/12
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public enum ProtocolTypeEnum {
    SDKVERIFICATION("001", "sdkverification"),
    BUILDCONVERSATION("002", "buildconversation"),
    MESSAGETRANSFER("003", "messagetransfer"),
    CUSTOMERSERVICELOGIN("004", "customerservicelogin"),
    SESSIONOPERATION("005", "sessionoperation"),
    ONLINECUSTOMERSERVICELIST("006", "onlinecustomerservicelist"),
    MESSAGELIST("007", "messagelist"),
    QUEUECONVERSATIONLIST("008", "queueconversationlist"),
    UPLOADIMAGE("009", "uploadimage"),
    CUSTOMERSERVICESTATUSCHANGE("010", "customerservicestatuschange"),
    VISITORCONVERSATION("011", "visitorconversation"),
    MESSAGESTATUSCHANGE("012", "messagestatuschange");


    private final String code;
    private final String name;

    ProtocolTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ProtocolTypeEnum getFrom(final String code) {
        for (ProtocolTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static ProtocolTypeEnum getFromName(final String name) {
        for (ProtocolTypeEnum type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
