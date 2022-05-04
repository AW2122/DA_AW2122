package com.aw2122.finalactivity.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.lang.reflect.Method;

public class HibernateCRUD {
    protected SessionFactory sessionFactory;
    private final Class entity;

    public <T> HibernateCRUD(T t) {
        this.entity = t.getClass();
    }

    public <T> void GetData(String code, String dataName) {
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery("from com.aw2122.finalactivity.library.UsersEntity where code = :code");
            query.setParameter(code, code);
            Method method = entity.getClass().getMethod(dataName, null);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
