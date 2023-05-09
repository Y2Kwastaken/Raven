package sh.miles.raven.provider.nitrite.database.connection;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.mvstore.MVStoreModule;

public class NitriteConnectionManager {

    private final NitriteConnectionString connectionString;
    private final Map<String, Nitrite> databases;

    public NitriteConnectionManager(NitriteConnectionString connectionString) {
        this.connectionString = connectionString;
        this.databases = new HashMap<>();
    }

    public Nitrite getDatabase(String databaseName) {
        if (databases.containsKey(databaseName)) {
            return databases.get(databaseName);
        }

        final String path = connectionString.getFilePath() + File.separator + databaseName + ".db";

        final MVStoreModule storeModule = MVStoreModule.withConfig()
                .filePath(path)
                .compress(true)
                .build();

        final Nitrite database = Nitrite.builder()
                .loadModule(storeModule)
                .openOrCreate(connectionString.getUsername(), connectionString.getPassword());

        databases.put(databaseName, database);

        return database;
    }

    public void closeDatabase(String databaseName) {
        if (!databases.containsKey(databaseName)) {
            return;
        }

        final Nitrite database = databases.get(databaseName);
        database.close();
        databases.remove(databaseName);
    }

    public boolean hasDatabase(String databaseName) {
        final File databaseDirectory = new File(connectionString.getFilePath());
        final File databaseFile = new File(databaseDirectory, databaseName + ".db");
        return databaseFile.exists();
    }

    public void closeAll() {
        databases.values().forEach(Nitrite::close);
        databases.clear();
    }

}
