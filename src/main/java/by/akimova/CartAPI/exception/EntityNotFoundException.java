package by.akimova.CartAPI.exception;

/**
 * This is exception class for catching empty entity.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
