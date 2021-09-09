package by.akimova.CartAPI.exception;

/**
 * This is exception class for catching not valid meaning.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}