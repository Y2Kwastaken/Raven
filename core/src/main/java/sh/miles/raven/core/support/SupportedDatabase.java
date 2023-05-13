package sh.miles.raven.core.support;

import java.lang.reflect.InvocationTargetException;

import sh.miles.raven.api.database.DatabaseConnection;
import sh.miles.raven.api.support.DatabaseType;

public enum SupportedDatabase {

    MONGODB("sh.miles.raven.provider.mongodb.database.MongoDatabaseConnection"),
    NITRITE("sh.miles.raven.provider.nitrite.database.NitriteDatabaseConnection");

    private final String connectionClassPath;

    SupportedDatabase(final String connectionClassPath) {
        this.connectionClassPath = connectionClassPath;
    }

    public String getConnectionClassPath() {
        return connectionClassPath;
    }

    @SuppressWarnings("java:S2658")
    public DatabaseConnection getConnection() {
        try {
            final Class<?> clazz = Class.forName(connectionClassPath);
            return (DatabaseConnection) clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (final ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException | ClassCastException e) {
            throw new IllegalStateException(e);
        }
    }

    public static SupportedDatabase fromDatabaseType(final DatabaseType databaseType) {
        for (final SupportedDatabase supportedDatabase : SupportedDatabase.values()) {
            if (supportedDatabase.name().equalsIgnoreCase(databaseType.name())) {
                return supportedDatabase;
            }
        }
        throw new IllegalArgumentException("Database type " + databaseType.name() + " is not supported");
    }

}
