package com.threeti.ics.server.dao.core;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: johnson
 * Date: 9/16/12
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GenericDao<T> {
   public T create(T newInstance);

   public T findBy(Long id);

   public T update(T transientObject);

   public boolean remove(T persistentObject);

   public List<T> find();
}

