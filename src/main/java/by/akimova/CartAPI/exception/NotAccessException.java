package by.akimova.CartAPI.exception;

/**
 * This is exception class for catching exception if the user doesn't have access.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public class NotAccessException extends Exception {

    public NotAccessException(String message) {
        super(message);
    }
}
