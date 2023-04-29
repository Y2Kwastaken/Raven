package sh.miles.raven.api.database;

import org.jetbrains.annotations.NotNull;

import sh.miles.raven.api.database.exception.DatabaseConnectionException;

/**
 * Represents a connection to a database.
 */
public interface DatabaseConnection {

    /**
     * Should be executed when the connection to the database is opened.
     * 
     * @param connectionString the connection string to use to connect to the
     *                         database.
     * 
     * @throws DatabaseConnectionException if the connection could not be
     *                                     established as expected.
     */
    void connect(@NotNull String connectionString) throws DatabaseConnectionException;

    /**
     * Should be executed when the connection to the database is closed.
     */
    void disconnect();

    /**
     * Returns a Database for the given database name.
     * 
     * @param databaseName the name of the database to get
     * @return the Database
     */
    Database getDatabase(@NotNull String databaseName);

}
