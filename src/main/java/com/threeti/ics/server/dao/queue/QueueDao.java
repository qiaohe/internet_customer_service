package com.threeti.ics.server.dao.queue;

import com.threeti.ics.server.dao.core.AbstractGenericDaoImpl;
import com.threeti.ics.server.dao.core.GenericDao;
import com.threeti.ics.server.domain.messagequeue.QueueType;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 17/09/12
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "queueDao")
public class QueueDao extends AbstractGenericDaoImpl<QueueType> implements GenericDao<QueueType> {

    public QueueDao() {
        super(QueueType.class);
    }

    public void remove(final String queueName, final Long conId) {
        template.opsForSet().remove(queueName, conId);
    }

    public void add(final String queueName, final Long conId) {
        template.opsForSet().add(queueName, conId.toString());
    }

    public void move(final String sourceQueue, final String destinationQueue, final Long conId) {
        template.opsForSet().move(sourceQueue, conId.toString(), destinationQueue);
    }

    public Long sizeOf(final String queueName) {
        return template.opsForSet().size(queueName);
    }

    public List<String> getVisitors(final String customerServiceUserName) {
        return template.sort(SortQueryBuilder.sort(QueueType.SESSION.getQueueName(customerServiceUserName))
                .by("conversation:*")
                .get("conversation:*->visitor")
                .order(SortParameters.Order.DESC)
                .build());
    }
}
