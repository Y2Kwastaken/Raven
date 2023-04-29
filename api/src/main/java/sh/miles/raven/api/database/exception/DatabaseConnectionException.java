package sh.miles.raven.api.database.exception;

/**
 * Thrown when a connection to a database could not be established as expected.
 */
public class DatabaseConnectionException extends Exception {

    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
