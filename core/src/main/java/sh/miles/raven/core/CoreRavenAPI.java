package sh.miles.raven.core;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import sh.miles.raven.api.RavenAPI;
import sh.miles.raven.api.conversion.TypeConverter;
import sh.miles.raven.api.database.DatabaseConnection;
import sh.miles.raven.api.support.DatabaseType;
import sh.miles.raven.core.conversion.TypeConversionManager;
import sh.miles.raven.core.support.SupportedDatabase;

public class CoreRavenAPI implements RavenAPI {

    @Override
    public DatabaseConnection getDatabaseConnection(@NotNull DatabaseType type) {
        Preconditions.checkNotNull(type, "Database type cannot be null");

        final SupportedDatabase supportedDatabase = SupportedDatabase.fromDatabaseType(type);
        return supportedDatabase.getConnection();
    }

    public void registerTypeConverter(@NotNull TypeConverter<?, ?> converter){
        Preconditions.checkNotNull(converter, "Type converter cannot be null");

        TypeConversionManager.getInstance().register(converter);
    }

}
