package com.threeti.ics.server.domain.messagequeue;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public enum QueueType {
    PUBLIC(0, "public_queue"), SESSION(1, "session_queue"), CUSTOMERMESSAGE(3, "customer_message_queue"), ACTIONREQUIRED(4, "action_required_queue");

    private final int id;
    private final String name;

    QueueType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static QueueType from(final String name) {
        for (QueueType t : values()) {
            if (name.equals(t.getName())) {
                return t;
            }
        }
        return null;
    }

    public String getQueueName(final String customerServiceName) {
        switch (this) {
            case PUBLIC:
                return this.name;
            case CUSTOMERMESSAGE:
                return this.name;
            case SESSION:
                return String.format("%s:%s", name, customerServiceName);
            case ACTIONREQUIRED:
                return String.format("%s:%s", name, customerServiceName);
        }
        return null;
    }
}
