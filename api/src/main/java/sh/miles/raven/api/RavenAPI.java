package sh.miles.raven.api;

import org.jetbrains.annotations.NotNull;

import sh.miles.raven.api.conversion.TypeConverter;
import sh.miles.raven.api.database.DatabaseConnection;
import sh.miles.raven.api.support.DatabaseType;

/**
 * The Raven interface is the main interface for accessing the Raven API. It
 * provides all methods required to use the Raven API. Any reflection-based
 * hacks to access internal methods are not supported.
 */
public interface RavenAPI {

    /**
     * Returns a {@link DatabaseConnection} for the specified {@link DatabaseType}.
     * 
     * @param type The {@link DatabaseType} to get a {@link DatabaseConnection} for.
     *             Cannot be null.
     * @return A {@link DatabaseConnection} for the specified {@link DatabaseType}.
     */
    DatabaseConnection getDatabaseConnection(@NotNull final DatabaseType type);

    /**
     * Registers a {@link TypeConverter} for use with the Raven API.
     * 
     * @param converter The {@link TypeConverter} to register. Cannot be null.
     */
    void registerTypeConverter(@NotNull TypeConverter<?, ?> converter);
}
    