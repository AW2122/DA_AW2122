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
        protected HibernateCRUD<UsersEntity> userCRUD;
        protected HibernateCRUD<BooksEntity> bookCRUD;
        protected HibernateCRUD<LendingEntity> LendingEntityCRUD;

        public DatabaseController() {
                sessionFactory = new Configuration().configure().buildSessionFactory();
                userCRUD = new HibernateCRUD<>(new UsersEntity(), sessionFactory);
                bookCRUD = new HibernateCRUD<>(new BooksEntity(), sessionFactory);
                LendingEntityCRUD = new HibernateCRUD<>(new LendingEntity(), sessionFactory);
        }

        public List<Object> GetObject (String searchParameter, String fieldName, String status) throws Exception {
                List result = null;
                try {
                        if (status.contains("USER")) {
                                result = userCRUD.GetData(searchParameter, fieldName);
                        }
                        if (status.contains("BOOK")) {
                                result = bookCRUD.GetData(searchParameter, fieldName);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return result;
        }

        public boolean Insert(Object object) {
                boolean success;
                try {
                        if (object instanceof UsersEntity) {
                                userCRUD.InsertObject((UsersEntity) object);
                        }
                        else if (object instanceof BooksEntity) {
                                bookCRUD.InsertObject((BooksEntity) object);
                        }
                        else {
                        }
                        success = true;

                } catch (Exception e) {
                        e.printStackTrace();
                        success = false;
                }
                return success;
        }

        public boolean Update(Object object) {
                boolean success;
                try {
                        if (object instanceof UsersEntity) {
                                userCRUD.UpdateObject((UsersEntity) object);
                        }
                        else if (object instanceof BooksEntity) {
                                bookCRUD.UpdateObject((BooksEntity) object);
                        } else {

                        }
                        success = true;
                }
                catch (Exception e) {
                        success = false;
                }
                return success;
        }
}
