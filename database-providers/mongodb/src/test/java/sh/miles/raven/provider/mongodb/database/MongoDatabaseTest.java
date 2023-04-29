package sh.miles.raven.provider.mongodb.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.exception.DatabaseConnectionException;

public class MongoDatabaseTest {
    
    public static final String CONNECTION_STRING = System.getenv("MONGO_ATLAS_CONNECTION");
    public static final String DATABASE_NAME = "test";
    public static final String COLLECTION_NAME = "test-collection";

    @Test
    public void testGetSection() throws DatabaseConnectionException {
        final var connection = new MongoDatabaseConnection();
        connection.connect(CONNECTION_STRING);

        final var database = connection.getDatabase(DATABASE_NAME);
        final var section = database.getSection(COLLECTION_NAME);
        assertNotNull(section, "Section should not be null");
    }

}
