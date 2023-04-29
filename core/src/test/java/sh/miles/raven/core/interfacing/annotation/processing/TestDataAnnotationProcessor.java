package sh.miles.raven.core.interfacing.annotation.processing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.api.database.exception.DatabaseInsertionException;
import sh.miles.raven.provider.mongodb.database.MongoDatabaseConnection;
import sh.miles.raven.provider.mongodb.database.MongoDatabaseSection;

public class TestDataAnnotationProcessor {

    public static final String CONNECTION_STRING = System.getenv("MONGO_ATLAS_CONNECTION");
    public static final String DATABASE_NAME = "test";
    public static final String COLLECTION_NAME = "test-collection";

    private static final MongoDatabaseSection section = connect();

    @Test
    public void testCreateDataInstance() throws DatabaseInsertionException {
        final TestMockProcessClass object = DataAnnotationProcessor.createDataInstance(TestMockProcessClass.class,
                section);
        assertNotNull(object, "The object should not be null.");
        
        assertEquals("Hello, World!", object.getString(), "The string should be equal.");
        assertEquals(5, object.getInteger(), "The integer should be equal.");
        
        final TestMockProcessClass.TestInternalMock internalObject = object.getInternal();
        assertNotNull(internalObject, "The internal object should not be null.");
    }

    private static final MongoDatabaseSection connect() {
        final var connection = new MongoDatabaseConnection();
        try {
            connection.connect(CONNECTION_STRING);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }

        final var database = connection.getDatabase(DATABASE_NAME);
        return (MongoDatabaseSection) database.getSection(COLLECTION_NAME);
    }
}
