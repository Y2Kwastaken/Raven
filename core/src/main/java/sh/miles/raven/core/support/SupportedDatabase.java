package sh.miles.raven.core.support;

import sh.miles.raven.api.database.DatabaseConnection;
import sh.miles.raven.api.support.DatabaseType;
import sh.miles.raven.api.utils.ReflectionUtils;
import sh.miles.raven.provider.mongodb.database.MongoDatabaseConnection;
import sh.miles.raven.provider.nitrite.database.NitriteDatabaseConnection;

/**
 * Bridges the gap between the API and the implementation.
 * ordinal order is always preserved assume this is the case always
 */
public enum SupportedDatabase {

    MONGODB(MongoDatabaseConnection.class),
    NITRITE(NitriteDatabaseConnection.class);

    private final Class<? extends DatabaseConnection> connectionClass;

    SupportedDatabase(final Class<? extends DatabaseConnection> connectionClass) {
        this.connectionClass = connectionClass;
    }

    public DatabaseConnection getConnection() {
        return ReflectionUtils.createInstance(connectionClass);
    }

    public static SupportedDatabase fromDatabaseType(final DatabaseType type) {
        return fromOrdinal(type.ordinal());
    }

    public static SupportedDatabase fromOrdinal(final int ordinal) {
        return SupportedDatabase.values()[ordinal];
    }

}
