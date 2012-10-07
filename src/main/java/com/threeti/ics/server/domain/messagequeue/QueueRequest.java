package com.threeti.ics.server.domain.messagequeue;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public class QueueRequest {
    private QueueType type;
    private int from;
    private int size;

    public QueueType getType() {
        return type;
    }

    public void setType(QueueType type) {
        this.type = type;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
