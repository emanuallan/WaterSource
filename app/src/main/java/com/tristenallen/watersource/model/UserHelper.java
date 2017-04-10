package com.tristenallen.watersource.model;

/**
 * Created by tristen on 2/21/17.
 *
 * Holds a list of users and provides methods for getting new users and adding new users.
 * Automatically assigns users an ID number and associates their email with that ID number.
 * Also stores a list of passwords for the users.
 */
@SuppressWarnings("FeatureEnvy")
public final class UserHelper {
    private static final UserHelper INSTANCE = new UserHelper();
    private int currentID;

    private UserHelper() {
        currentID = 0;
    }

    /**
     * Method for getting an instance of the UserHelper class.
     * @return this class's private instance of itself.
     */
    public static UserHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new user from the given information. Returns false
     * if there is already a user associated with the given email.
     * @param user User containing the new user's information.
     * @param email String specifying the new user's login email.
     * @param password String specifying the new user's password.
     * @param data the DataSource object used to get information from the database.
     * @return true or false depending on whether the method was successful or not.
     */
    public boolean addUser(User user, String email, String password, DataSource data) {
        currentID = data.getUserCount();
        if (data.checkEmail(email)) {
            return false;
        } else {
            data.createUser(currentID,password,user.getEmail(),user.getRole(),
                    user.getAddress(),user.getTitle(), user.getLastName(),user.getFirstName());
            return true;
        }
    }

}
