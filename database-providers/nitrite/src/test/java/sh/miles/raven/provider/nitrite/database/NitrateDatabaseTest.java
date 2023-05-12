package sh.miles.raven.provider.nitrite.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.exception.DatabaseConnectionException;

public class NitrateDatabaseTest {

    public static final String CONNECTION_STRING = System.getenv("NITRITE_CONNECTION");
    public static final String DATABASE_NAME = "nitrite";

    @Test
    public void testCreateCollection() {
        final var database = getDatabase();

        assertDoesNotThrow(() -> database.createCollection(SectionField.COLLECTION_NAME), "Should not throw");
    }

    @Test
    public void testHasCollection() {
        final var database = getDatabase();

        assertTrue(database.hasCollection(SectionField.COLLECTION_NAME), "Should return true");
    }

    @Test
    public void testDeleteCollection() {
        final var database = getDatabase();

        assertTrue(database.deleteCollection(SectionField.COLLECTION_NAME), "Should return true");
    }

    @Test
    public void testCreateSection() {
        final var database = getDatabase();

        assertDoesNotThrow(() -> database.createSection(SectionField.COLLECTION_NAME, SectionField.DOCUMENT_NAME),
                "Should not throw");
    }

    @Test
    public void testHasSection() {
        final var database = getDatabase();

        assertTrue(database.hasSection(SectionField.COLLECTION_NAME, SectionField.DOCUMENT_NAME), "Should return true");
    }

    @Test
    public void testDeleteSection() {
        final var database = getDatabase();

        assertTrue(database.deleteSection(SectionField.COLLECTION_NAME, SectionField.DOCUMENT_NAME),
                "Should have affected a document");
        assertFalse(database.hasSection(SectionField.COLLECTION_NAME, SectionField.DOCUMENT_NAME), "Should return false");
    }

    private static NitriteDatabase getDatabase() {
        final var connection = getConnection();
        return (NitriteDatabase) connection.getDatabase(DATABASE_NAME);
    }

    private static NitriteDatabaseConnection getConnection() {
        final var connection = new NitriteDatabaseConnection();
        try {
            connection.connect(CONNECTION_STRING);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
