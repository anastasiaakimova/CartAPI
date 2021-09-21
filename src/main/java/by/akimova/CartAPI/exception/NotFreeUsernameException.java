package by.akimova.CartAPI.exception;

/**
 * This is exception class for catching users those who add and use existing in the database usernames.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public class NotFreeUsernameException extends Exception {
    public NotFreeUsernameException(String message) {
        super(message);
    }
}