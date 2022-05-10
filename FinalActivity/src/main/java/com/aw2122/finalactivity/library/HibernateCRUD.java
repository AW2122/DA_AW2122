package com.aw2122.finalactivity.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateCRUD<T> {
    protected SessionFactory sessionFactory;
    protected String entity;

    public HibernateCRUD(T entity, SessionFactory sessionFactory) {
        this.entity = entity.getClass().getName();
        this.sessionFactory = sessionFactory;
    }

    public List<T> GetData(String searchParameter, String fieldName) throws Exception {
        Query<T> query;
        List<T> list;
        Session session = sessionFactory.openSession();
        query = session.createQuery("from " + entity + " where " + fieldName + " like " + "('%" + searchParameter + "%')");
        list = query.getResultList();
        session.close();
        return list;
    }

    public void InsertObject(Object t) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        session.close();
    }

    public void UpdateObject(Object t) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();
        session.close();
    }
}
