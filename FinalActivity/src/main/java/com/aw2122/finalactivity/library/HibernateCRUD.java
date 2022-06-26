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

    /**
     * This methid is used to retrieve an object list that corresponds with the search parameters.
     * @param searchParameter
     * @param fieldName
     * @return
     * @throws Exception
     */
    public List<T> GetData(String searchParameter, String fieldName) throws Exception {
        Query<T> query;
        List<T> list;
        Session session = sessionFactory.openSession();
        query = session.createQuery("from " + entity + " where " + fieldName + " like " + "('%" + searchParameter + "%')");
        list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * This method inserts an object into the database.
     * @param t
     * @throws Exception
     */
    public void InsertObject(T t) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        session.close();
    }

    /**
     * This method updates the specified object.
     * @param t
     * @throws Exception
     */
    public void UpdateObject(T t) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();
        session.close();
    }

    /**
     * This method returns a LendingEntity that contains the book and the user that are passed as parameters.
     * @param user
     * @param book
     * @return
     * @throws Exception
     */
    public LendingEntity GetLending(UsersEntity user, BooksEntity book) throws Exception {
        Query<LendingEntity> query;
        Session session = sessionFactory.openSession();
        query = session.createQuery("from " + entity + " lending where lending.book.isbn = '" + book.getIsbn() + "' and lending.borrower.code = '" + user.getCode() + "'");
        LendingEntity lending = query.getSingleResult();
        session.close();
        return lending;
    }
}