package sh.miles.raven.provider.mongodb.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.exception.DatabaseConnectionException;

public class MongoDatabaseTest {

    public static final String CONNECTION_STRING = System.getenv("MONGO_ATLAS_CONNECTION");
    public static final String DATABASE_NAME = "test";
    public static final String COLLECTION_NAME = "test-collection";
    public static final String DOCUMENT_ID = "test-document";

    @Test
    public void testGetSection() throws DatabaseConnectionException {
        final var connection = new MongoDatabaseConnection();
        connection.connect(CONNECTION_STRING);

        final var database = connection.getDatabase(DATABASE_NAME);
        final var section = database.getSection(COLLECTION_NAME, DOCUMENT_ID);
        assertNotNull(section, "Section should not be null");
    }

    @Test
    public void testCreateCollection() throws DatabaseConnectionException {
        final var connection = new MongoDatabaseConnection();
        connection.connect(CONNECTION_STRING);

        final var database = connection.getDatabase(DATABASE_NAME);
        final var section = database.createCollection("test-test-collection");
        assertTrue(section, "Section should not be null");
    }

    @Test
    public void testHasCollection() throws DatabaseConnectionException {
        final var connection = new MongoDatabaseConnection();
        connection.connect(CONNECTION_STRING);

        final var database = connection.getDatabase(DATABASE_NAME);
        final var section = database.hasCollection("test-test-collection");
        assertTrue(section, "Section should not be null");
    }

    @Test
    public void deleteCollection() throws DatabaseConnectionException {
        final var connection = new MongoDatabaseConnection();
        connection.connect(CONNECTION_STRING);

        final var database = connection.getDatabase(DATABASE_NAME);
        final var section = database.deleteCollection("test-test-collection");
        assertTrue(section, "Section should not be null");
    }

}
