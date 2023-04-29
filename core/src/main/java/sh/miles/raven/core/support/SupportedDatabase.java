package sh.miles.raven.core.support;

import java.lang.reflect.InvocationTargetException;

import sh.miles.raven.api.database.DatabaseConnection;

public enum SupportedDatabase {

    MONGODB("sh.miles.raven.provider.mongodb.database.MongoDatabaseConnection");

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

}
