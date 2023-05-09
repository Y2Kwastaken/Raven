package sh.miles.raven.provider.nitrite.database;

import org.jetbrains.annotations.NotNull;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseConnection;
import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.provider.nitrite.database.connection.NitriteConnectionManager;
import sh.miles.raven.provider.nitrite.database.connection.NitriteConnectionString;

public class NitriteDatabaseConnection implements DatabaseConnection {

    private NitriteConnectionManager connectionManager;

    @Override
    public void connect(@NotNull String connectionString) throws DatabaseConnectionException {
        final NitriteConnectionString nitriteConnectionString = new NitriteConnectionString(connectionString);
        this.connectionManager = new NitriteConnectionManager(nitriteConnectionString);
    }

    @Override
    public void disconnect() {
        connectionManager.closeAll();
    }

    @Override
    public Database getDatabase(@NotNull String databaseName) {
        return new NitriteDatabase(connectionManager.getDatabase(databaseName));
    }

}
