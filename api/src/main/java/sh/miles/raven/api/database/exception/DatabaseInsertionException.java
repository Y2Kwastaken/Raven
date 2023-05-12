package sh.miles.raven.api.database.exception;

/**
 * DatabaseInsertionException is thrown when an error occurs while inserting
 * data
 */
public class DatabaseInsertionException extends Exception {

    public DatabaseInsertionException(String message) {
        super(message);
    }

    DatabaseInsertionException(String message, Throwable cause) {
        super(message, cause);
    }
}
