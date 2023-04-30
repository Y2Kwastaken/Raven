package sh.miles.raven.provider.mongodb.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.api.database.exception.DatabaseInsertionException;

public class MongoDatabaseSectionTest {

    public static final String CONNECTION_STRING = System.getenv("MONGO_ATLAS_CONNECTION");
    public static final String DATABASE_NAME = "test";
    public static final String COLLECTION_NAME = "test-collection";
    public static final String DOCUMENT_ID = "test-document";

    private static final MongoDatabaseSection section = connect();

    @Test
    public void testGetString() {
        final String string = section.getString("string");

        assertNotNull(string, "String should not be null");
        assertEquals("Hello, World!", string, "String should be \"Hello, World!\"");
    }

    @Test
    public void testGetInt() {
        final int integer = section.getInt("int");

        assertEquals(5, integer, "Integer should be 5");
    }

    @Test
    public void testGetLong() {
        final long longInteger = section.getLong("long");

        assertEquals(1003L, longInteger, "Long should be 1003");
    }

    @Test
    public void testGetDouble() {
        final double doubleNumber = section.getDouble("double");

        assertEquals(3.3, doubleNumber, "Double should be 3.3");
    }

    @Test
    public void testGetBoolean() {
        final boolean booleanValue = section.getBoolean("boolean");

        assertTrue(booleanValue, "Boolean should be true");
    }

    @Test
    public void testGetObject() {
        final var object = section.get("object");
        Document document = (Document) object;
        assertEquals("Hello, World!", document.getString("string"), "String should be \"Hello, World!\"");
        assertEquals(5, document.getInteger("int"), "Integer should be 5");

        assertNotNull(object, "Object should not be null");
    }

    @Test
    public void testGetStringList() {
        final var stringList = section.getStringList("stringList");

        assertNotNull(stringList, "String list should not be null");
        assertEquals("Hello", stringList.get(0), "String list should contain \"Hello\"");
        assertEquals("World", stringList.get(1), "String list should contain \"World\"");
    }

    @Test
    public void testGetDoubleList() {
        final var doubleList = section.getDoubleList("doubleList");

        assertNotNull(doubleList, "Double list should not be null");
        assertEquals(1.1, doubleList.get(0), "Double list should contain 1.1");
        assertEquals(2.2, doubleList.get(1), "Double list should contain 2.2");
    }

    @Test
    public void testGetLongList() {
        final var longList = section.getLongList("longList");

        assertNotNull(longList, "Long list should not be null");
        assertEquals(1003L, longList.get(0), "Long list should contain 1003");
        assertEquals(1004L, longList.get(1), "Long list should contain 1004");
    }

    @Test
    public void testDotPathGet() {
        final var string = section.getString("object.string");
        assertEquals("Hello, World!", string, "String should be \"Hello, World!\"");
    }

    @Test
    public void testGetSection() {
        final var object = section.getSection("object");
        assertNotNull(object, "Section should not be null");
        assertEquals("Hello, World!", object.getString("string"), "String should be \"Hello, World!\"");
        // get nested object now
        final var nestedObject = object.getSection("object");
        assertNotNull(nestedObject, "Nested section should not be null");
        assertEquals("Happy!", nestedObject.getString("string"), "String should be \"Happy!\"");
        assertEquals(6, nestedObject.getInt("int"), "Integer should be 6");

        assertEquals(5, object.getInt("int"), "Integer should be 5");
    }

    @Test
    public void testSetValue() {
        try {
            final List<String> list = List.of("test0", "test1");
            section.set("test-set-value", list);
        } catch (DatabaseInsertionException e) {
            e.printStackTrace();
        }

        final var stringList = section.getStringList("test-set-value");
        for (int i = 0; i < stringList.size(); i++) {
            assertEquals("test" + i, stringList.get(i), "String list should contain \"test" + i + "\"");
        }
    }

    @Test
    public void testRemoveValue() {
        section.remove("test-set-value");
        final var stringList = section.getStringList("test-set-value");
        assertNull(stringList, "String list should be null");
    }

    private static final MongoDatabaseSection connect() {
        final var connection = new MongoDatabaseConnection();
        try {
            connection.connect(CONNECTION_STRING);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }

        final var database = connection.getDatabase(DATABASE_NAME);
        return (MongoDatabaseSection) database.getSection(COLLECTION_NAME, DOCUMENT_ID);
    }

}
