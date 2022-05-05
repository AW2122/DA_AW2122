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
        protected HibernateCRUD<UsersEntity> userCRUD = null;
        public DatabaseController() {
                sessionFactory = new Configuration().configure().buildSessionFactory();
                userCRUD = new HibernateCRUD<>(new UsersEntity(), sessionFactory);
        }


        public void GetUser(String searchParameter, String fieldName) {
                try {
                        System.out.println(userCRUD.GetData("c001", "code").get(0).getName());

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public void InsertUser(Object obj) {
                try {
                        userCRUD.InsertObject(obj);

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

}
