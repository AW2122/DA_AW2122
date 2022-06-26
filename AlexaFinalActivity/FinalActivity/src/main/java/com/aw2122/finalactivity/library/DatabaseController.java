package com.aw2122.finalactivity.library;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DatabaseController {
        protected SessionFactory sessionFactory;
        protected HibernateCRUD<UsersEntity> userCRUD;
        protected HibernateCRUD<BooksEntity> bookCRUD;
        protected HibernateCRUD<LendingEntity> lendingEntityCRUD;

        public DatabaseController() {
                sessionFactory = new Configuration().configure().buildSessionFactory();
                userCRUD = new HibernateCRUD<>(new UsersEntity(), sessionFactory);
                bookCRUD = new HibernateCRUD<>(new BooksEntity(), sessionFactory);
                lendingEntityCRUD = new HibernateCRUD<>(new LendingEntity(), sessionFactory);
        }

        /**
         * This method takes two strings and depending on what the field name is, uses the crud of that type and calls
         * the GetData method from HibernateCRUD.
         * @param searchParameter
         * @param fieldName
         * @return
         * @throws Exception
         */
        public List<Object> GetObject (String searchParameter, String fieldName) throws Exception {
                List result = null;
                        if (fieldName.equals("code")) {
                                result = userCRUD.GetData(searchParameter, fieldName);

                        }
                        if (fieldName.equals("isbn")) {
                                result = bookCRUD.GetData(searchParameter, fieldName);
                        }
                return result;
        }

        /**
         * This method calls the InsertObject method from HibernateCRUD and depending on the instance type of the
         * object, it uses a different CRUD type.
         * @param object
         * @return
         */
        public boolean Insert(Object object) {
                boolean success;
                try {
                        if (object instanceof UsersEntity) {
                                userCRUD.InsertObject((UsersEntity) object);
                        }
                        else if (object instanceof BooksEntity) {
                                bookCRUD.InsertObject((BooksEntity) object);
                        }
                        else if (object instanceof LendingEntity) {
                                lendingEntityCRUD.InsertObject((LendingEntity) object);
                        }
                        success = true;

                } catch (Exception e) {
                        success = false;
                }
                return success;
        }

        /**
         * This method calls the UpdateObject method from HibernateCRUD, and depending on what the object is an
         * instance of it uses a different CRUD type.
         * @param object
         * @return
         */
        public boolean Update(Object object) {
                boolean success;
                try {
                        if (object instanceof UsersEntity) {
                                userCRUD.UpdateObject((UsersEntity) object);
                        }
                        else if (object instanceof BooksEntity) {
                                bookCRUD.UpdateObject((BooksEntity) object);
                        }
                        else  if (object instanceof  LendingEntity) {
                                lendingEntityCRUD.UpdateObject((LendingEntity) object);
                        }
                        success = true;
                }
                catch (Exception e) {
                        success = false;
                }
                return success;
        }

        /**
         * This method recieves a book object and a user object and returns the lending object that contains both
         * objects.
         * @param user
         * @param book
         * @return
         * @throws Exception
         */

        public LendingEntity getLending(UsersEntity user, BooksEntity book) throws Exception {
                LendingEntity lending = null;
                try {
                        lending = lendingEntityCRUD.GetLending(user, book);

                } catch (Exception e){

                }
                return lending;
        }

        /**
         * This method created a reservation, using the API
         * @param reservation
         * @throws Exception
         */
        public void postReservation(ReservationsEntity reservation) throws Exception {
                HttpURLConnection connection = null;
                String jsonInputString = new JSONObject()
                        .put("date", reservation.getDate())
                        .put("book", reservation.getBook().getIsbn())
                        .put("borrower", reservation.getBorrower().getCode()).toString();
                try {
                        URL url = new URL("http://localhost:8080/aw2122-api-rest/reservations");
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json; utf-8");
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setDoOutput(true);
                        try (OutputStream os = connection.getOutputStream()) {
                                byte[] input = jsonInputString.getBytes("utf-8");
                                os.write(input, 0, input.length);
                        }
                        if (connection.getResponseCode() != 200) {
                                System.out.println(connection.getResponseCode());
                        }
                } catch (Exception e) {
                        throw e;
                } finally {
                        if (connection != null)
                                connection.disconnect();
                }
        }

        /**
         * This method deletes the reservation, using the API.
         * @param reservation
         * @throws Exception
         */
        public void deleteReservation(ReservationsEntity reservation) throws Exception {
                HttpURLConnection connection = null;
                try {
                        URL url = new URL("http://localhost:8080/aw2122-api-rest/reservations/" + reservation.getId());
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");
                        if (connection.getResponseCode() != 200) {
                                throw new Exception("Reservation DELETE request failed.");
                        }

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                } finally {
                        if (connection != null)
                                connection.disconnect();
                }
        }
}