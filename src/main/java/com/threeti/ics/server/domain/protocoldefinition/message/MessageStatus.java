package com.threeti.ics.server.domain.protocoldefinition.message;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public enum MessageStatus {
    NOTREAD(0), READ(1), SENT(2), FAIL(3);

    private int code;

    MessageStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MessageStatus getFrom(int code) {
        for (MessageStatus status : MessageStatus.values()) {
            if (status.getCode() == code) return status;
        }
        return null;
    }
}
