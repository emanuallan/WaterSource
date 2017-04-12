package com.tristenallen.watersource.model;

/**
 * Created by David on 3/28/17.
 * Interface for performing retrieval and update method.
 */


public interface DataSource {

    /**
     * method for add user to the db
     * @param id int new user assigned id
     * @param password String password for the user
     * @param address String address of the user
     * @param title String title of the user
     * @param user User object containing basic user info
     */
    void createUser(int id, String password, String address, String title, User user);

    /**
     * method for check email
     * @param email String email for the user
     * @return true if the email already exist in User DB, false otherwise
     */
    boolean checkEmail(String email);

    /**
     * method for validate email and password
     * @param email String email for the user
     * @param password String password for the user
     * @return true if the email matches password, false otherwise
     */
    boolean validate(String email, String password);

    /**
     * method for get the id of user from email
     * @param email String email for the user
     * @return the id int of the user
     */
    int getIDbyEmail(String email);

    /**
     * method for get the user from id
     * @param id int id of the user
     * @return User the user of the id
     */
    User getUserByID(int id);

    /**
     * method for get the number of users from DB
     * @return int the number of user
     */
    int getUserCount();
}
