package by.akimova.CartAPI.exception;

/**
 * This is exception class for catching not valid meaning.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public class NotValidUsernameException extends Exception {

    public NotValidUsernameException(String message) {
        super(message);
    }
}