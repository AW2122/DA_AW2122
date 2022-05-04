package com.aw2122.unit05;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DatabaseController {
        SessionFactory sessionFactory;
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

        public void GetData (String code) {
                try (Session session = sessionFactory.openSession()) {
                        Query<UsersEntity> usersEntityQuery = session.createQuery("from com.aw2122.unit05.UsersEntity"); // where code = :code
                        //usersEntityQuery.setParameter("code", code);
                        List<UsersEntity> usersEntityList = usersEntityQuery.list();

                        for (Object userObject : usersEntityList) {
                                UsersEntity user = (UsersEntity) userObject;
                                System.out.println(user.getName());
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

}
