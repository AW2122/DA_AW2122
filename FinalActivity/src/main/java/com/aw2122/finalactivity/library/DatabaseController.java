package com.aw2122.finalactivity.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.query.QueryParameter;

import java.lang.reflect.Method;
import java.util.List;

public class DatabaseController {
        protected SessionFactory sessionFactory;
        public DatabaseController() {
                sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        public void InsertUsers(UsersEntity user) {
                try (Session session = sessionFactory.openSession()) {
                        Transaction transaction = session.beginTransaction();
                        session.save(user);
                        transaction.commit();
                } catch (Exception e) {
                        System.out.println();
                }
        }



}
