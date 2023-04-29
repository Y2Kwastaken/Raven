package sh.miles.raven.provider.mongodb.database;

import org.jetbrains.annotations.NotNull;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseConnection;
import sh.miles.raven.api.database.exception.DatabaseConnectionException;

public class MongoDatabaseConnection implements DatabaseConnection {

    private static final String CONNECTION_EXCEPTION_MESSAGE = "Connection to MongoDB failed. with string: [%s]";

    private MongoClient client;

    @Override
    public void connect(@NotNull String connectionString) throws DatabaseConnectionException {
        try {
            client = MongoClients.create(connectionString);
        } catch (MongoException e) {
            throw new DatabaseConnectionException(String.format(CONNECTION_EXCEPTION_MESSAGE, connectionString), e);
        }
    }

    @Override
    public void disconnect() {
        client.close();
    }

    public MongoClient getClient() {
        return client;
    }

    @Override
    public Database getDatabase(@NotNull String databaseName) {
        return new MongoDatabase(client.getDatabase(databaseName));
    }
}
