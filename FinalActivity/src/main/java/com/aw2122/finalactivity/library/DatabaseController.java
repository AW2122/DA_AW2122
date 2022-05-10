package com.aw2122.finalactivity.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.query.QueryParameter;

import java.lang.reflect.Method;
import java.util.*;

public class DatabaseController {
        protected SessionFactory sessionFactory;
        protected HibernateCRUD<UsersEntity> userCRUD = null;
        protected HibernateCRUD<BooksEntity> bookCRUD = null;
        protected HibernateCRUD<LendingEntity> LendingEntityCRUD = null;

        public DatabaseController() {
                sessionFactory = new Configuration().configure().buildSessionFactory();
                userCRUD = new HibernateCRUD<>(new UsersEntity(), sessionFactory);
                bookCRUD = new HibernateCRUD<>(new BooksEntity(), sessionFactory);
                LendingEntityCRUD = new HibernateCRUD<>(new LendingEntity(), sessionFactory);
        }

        public List<Object> GetObject (String searchParameter, String fieldName) {
                List<Object> result = null;
                try {
                        result.add(userCRUD.GetData(searchParameter, fieldName));

                } catch (Exception e) {
                        e.printStackTrace();
                }
                return result;
        }

        public void Insert(Object obj) {
                try {
                        if (obj.getClass().getName().equals("UsersEntity")) {
                                userCRUD.InsertObject(obj);
                        }
                        else if (obj instanceof BooksEntity) {
                                bookCRUD.InsertObject(obj);
                        }
                        else {
                                System.out.println("Mal");
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public void Update(Object object) {
                try {
                        if (object instanceof UsersEntity) {
                                userCRUD.UpdateObject(object);
                        }
                        else {

                        }
                }
                catch (Exception e) {

                }

        }

}
