package sh.miles.raven.provider.mongodb.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class MongoDatabaseConnectionTest {

    public static final String CONNECTION_STRING = System.getenv("MONGO_ATLAS_CONNECTION");
    public static final String DATABASE_NAME = "test";

    @Test
    public void testConnect() {
        MongoDatabaseConnection connection = new MongoDatabaseConnection();
        assertNotNull(CONNECTION_STRING, "Connection string should not be null");
        assertDoesNotThrow(() -> connection.connect(CONNECTION_STRING), "Connection should not throw an exception");
    }

    @Test
    public void testDatabase() {
        final var connection = new MongoDatabaseConnection();
        assertDoesNotThrow(() -> connection.connect(CONNECTION_STRING), "Connection should not throw an exception");

        final var database = connection.getDatabase(DATABASE_NAME);
        assertNotNull(database, "Database should not be null");
    }

}
