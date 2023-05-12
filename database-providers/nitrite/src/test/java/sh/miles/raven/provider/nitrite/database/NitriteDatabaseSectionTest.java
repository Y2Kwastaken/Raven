package sh.miles.raven.provider.nitrite.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.api.database.exception.DatabaseInsertionException;

public class NitriteDatabaseSectionTest {

    public static final String CONNECTION_STRING = System.getenv("NITRITE_CONNECTION");
    public static final String DATABASE_NAME = "nitrite";

    private static final NitriteDatabaseSection section = getSection();

    @Test
    public void testSetString() throws DatabaseInsertionException {
        final SectionField string = SectionField.STRING;
        section.set(string.getKey(), string.getValue());
        assertNotNull(section.getString(string.getKey()),
                "String value should not be null after insertion, updated value: "
                        + section.getString(string.getKey()));

        final SectionField singleEmbedString = SectionField.OBJECT_STRING;
        section.set(singleEmbedString.getKey(), singleEmbedString.getValue());
        assertNotNull(section.getString(singleEmbedString.getKey()),
                "String value should not be null after insertion, updated value: "
                        + section.getString(singleEmbedString.getKey()));
    }

    @Test
    public void testGetString() {
        final SectionField string = SectionField.STRING;
        section.getString(string.getKey());
        assertEquals(string.getValue(), section.getString(string.getKey()), "String value does not match, expected: "
                + string.getValue() + ", actual: " + section.getString(string.getKey()));

        final SectionField singleEmbedString = SectionField.OBJECT_STRING;
        section.getString(singleEmbedString.getKey());
        assertEquals(singleEmbedString.getValue(), section.getString(singleEmbedString.getKey()),
                "String value does not match, expected: " + singleEmbedString.getValue() + ", actual: "
                        + section.getString(singleEmbedString.getKey()));
    }

    @Test
    public void testRemoveString() {
        final SectionField string = SectionField.STRING;
        section.remove(string.getKey());

        final SectionField singleEmbedString = SectionField.OBJECT_STRING;
        section.remove(singleEmbedString.getKey());
        assertNull(section.getString(singleEmbedString.getKey()),
                "String value should be null after removal, updated value: "
                        + section.getString(singleEmbedString.getKey()));
    }

    @Test
    public void testSetInt() throws DatabaseInsertionException {
        final SectionField integer = SectionField.INT;
        section.set(integer.getKey(), integer.getValue());
        assertEquals(integer.getValue(), section.getInt(integer.getKey()),
                "Integer value should not be null after insertion, updated value: "
                        + section.getInt(integer.getKey()));

        final SectionField singleEmbedInteger = SectionField.OBJECT_INT;
        section.set(singleEmbedInteger.getKey(), singleEmbedInteger.getValue());
        assertEquals(singleEmbedInteger.getValue(), section.getInt(singleEmbedInteger.getKey()),
                "Integer value should not be null after insertion, updated value: "
                        + section.getInt(singleEmbedInteger.getKey()));
    }

    @Test
    public void testGetInt() {
        final SectionField integer = SectionField.INT;
        section.getInt(integer.getKey());
        assertEquals(integer.getValue(), section.getInt(integer.getKey()),
                "Integer value does not match, expected: " + integer.getValue() + ", actual: "
                        + section.getInt(integer.getKey()));

        final SectionField singleEmbedInteger = SectionField.OBJECT_INT;
        section.getInt(singleEmbedInteger.getKey());
        assertEquals(singleEmbedInteger.getValue(), section.getInt(singleEmbedInteger.getKey()),
                "Integer value does not match, expected: " + singleEmbedInteger.getValue() + ", actual: "
                        + section.getInt(singleEmbedInteger.getKey()));
    }

    @Test
    public void testRemoveInt() {
        final SectionField integer = SectionField.INT;
        section.remove(integer.getKey());
        assertEquals(Integer.MIN_VALUE, section.getInt(integer.getKey()),
                "Integer value should be 0 after removal, updated value: " + section.getInt(integer.getKey()));

        final SectionField singleEmbedInteger = SectionField.OBJECT_INT;
        section.remove(singleEmbedInteger.getKey());
        assertEquals(Integer.MIN_VALUE, section.getInt(singleEmbedInteger.getKey()),
                "Integer value should be 0 after removal, updated value: "
                        + section.getInt(singleEmbedInteger.getKey()));
    }

    @Test
    public void testSetDouble() throws DatabaseInsertionException {
        final SectionField doubleValue = SectionField.DOUBLE;
        section.set(doubleValue.getKey(), doubleValue.getValue());
        assertEquals(doubleValue.getValue(), section.getDouble(doubleValue.getKey()),
                "Double value should not be null after insertion, updated value: "
                        + section.getDouble(doubleValue.getKey()));
    }

    @Test
    public void testGetDouble() {
        final SectionField doubleValue = SectionField.DOUBLE;
        section.getDouble(doubleValue.getKey());
        assertEquals(doubleValue.getValue(), section.getDouble(doubleValue.getKey()),
                "Double value does not match, expected: " + doubleValue.getValue() + ", actual: "
                        + section.getDouble(doubleValue.getKey()));
    }

    @Test
    public void testRemoveDouble() {
        final SectionField doubleValue = SectionField.DOUBLE;
        section.remove(doubleValue.getKey());
        assertEquals(Double.MIN_VALUE, section.getDouble(doubleValue.getKey()),
                "Double value should be 0 after removal, updated value: " + section.getDouble(doubleValue.getKey()));
    }

    @Test
    public void testSetLong() throws DatabaseInsertionException {
        final SectionField longValue = SectionField.LONG;
        section.set(longValue.getKey(), longValue.getValue());
        assertEquals(longValue.getValue(), section.getLong(longValue.getKey()),
                "Long value should not be null after insertion, updated value: " + section.getLong(longValue.getKey()));
    }

    @Test
    public void testGetLong() {
        final SectionField longValue = SectionField.LONG;
        section.getLong(longValue.getKey());
        assertEquals(longValue.getValue(), section.getLong(longValue.getKey()),
                "Long value does not match, expected: " + longValue.getValue() + ", actual: "
                        + section.getLong(longValue.getKey()));
    }

    @Test
    public void testRemoveLong() {
        final SectionField longValue = SectionField.LONG;
        section.remove(longValue.getKey());
        assertEquals(Long.MIN_VALUE, section.getLong(longValue.getKey()),
                "Long value should be 0 after removal, updated value: " + section.getLong(longValue.getKey()));
    }

    @Test
    public void testSetDoubleList() throws DatabaseInsertionException {
        final SectionField doubleList = SectionField.DOUBLE_LIST;
        section.set(doubleList.getKey(), doubleList.getValue());
        assertEquals(doubleList.getValue(), section.getDoubleList(doubleList.getKey()),
                "Double list value should not be null after insertion, updated value: "
                        + section.getDoubleList(doubleList.getKey()));
    }

    @Test
    public void testGetDoubleList() {
        final SectionField doubleList = SectionField.DOUBLE_LIST;
        section.getDoubleList(doubleList.getKey());
        assertEquals(doubleList.getValue(), section.getDoubleList(doubleList.getKey()),
                "Double list value does not match, expected: " + doubleList.getValue() + ", actual: "
                        + section.getDoubleList(doubleList.getKey()));
    }

    @Test
    public void testRemoveDoubleList() {
        final SectionField doubleList = SectionField.DOUBLE_LIST;
        section.remove(doubleList.getKey());
        assertNull(section.getDoubleList(doubleList.getKey()),
                "Double list value should be empty after removal, updated value: "
                        + section.getDoubleList(doubleList.getKey()));
    }

    @Test
    public void testSetDoubleNestedObject() throws DatabaseInsertionException {
        final SectionField objectObjectString = SectionField.OBJECT_OBJECT_STRING;
        section.set(objectObjectString.getKey(), objectObjectString.getValue());
        assertEquals(objectObjectString.getValue(),
                section.getSection(objectObjectString.getKey()).getString(objectObjectString.getKey()),
                "Double nested object value should not be null after insertion, updated value: "
                        + section.getSection(objectObjectString.getKey()).getString(objectObjectString.getKey()));

        final SectionField objectObjectInt = SectionField.OBJECT_OBJECT_INT;
        section.set(objectObjectInt.getKey(), objectObjectInt.getValue());
        assertEquals(objectObjectInt.getValue(),
                section.getSection(objectObjectInt.getKey()).getInt(objectObjectInt.getKey()),
                "Double nested object value should not be null after insertion, updated value: "
                        + section.getSection(objectObjectInt.getKey()).getInt(objectObjectInt.getKey()));
    }

    @Test
    public void testGetDoubleNestedObject() {
        final SectionField objectObjectString = SectionField.OBJECT_OBJECT_STRING;
        section.getSection(objectObjectString.getKey());
        assertEquals(objectObjectString.getValue(),
                section.getSection(objectObjectString.getKey()).getString(objectObjectString.getKey()),
                "Double nested object value does not match, expected: " + objectObjectString.getValue() + ", actual: "
                        + section.getSection(objectObjectString.getKey()).getString(objectObjectString.getKey()));

        final SectionField objectObjectInt = SectionField.OBJECT_OBJECT_INT;
        section.getSection(objectObjectInt.getKey());
        assertEquals(objectObjectInt.getValue(),
                section.getSection(objectObjectInt.getKey()).getInt(objectObjectInt.getKey()),
                "Double nested object value does not match, expected: " + objectObjectInt.getValue() + ", actual: "
                        + section.getSection(objectObjectInt.getKey()).getInt(objectObjectInt.getKey()));
    }

    @Test
    public void testRemoveDoubleNestedObject() {
        final SectionField objectObjectString = SectionField.OBJECT_OBJECT_STRING;
        section.remove(objectObjectString.getKey());
        assertNull(section.getString(objectObjectString.getKey()),
                "Double nested object value should be null after removal, updated value: "
                        + section.getString(objectObjectString.getKey()));

        final SectionField objectObjectInt = SectionField.OBJECT_OBJECT_INT;
        section.remove(objectObjectInt.getKey());
        assertEquals(Integer.MIN_VALUE, section.getInt(objectObjectInt.getKey()),
                "Double nested object value should be 0 after removal, updated value: "
                        + section.getInt(objectObjectInt.getKey()));
    }

    private static NitriteDatabaseSection getSection() {
        return (NitriteDatabaseSection) getDatabase().getSection(SectionField.COLLECTION_NAME,
                SectionField.DOCUMENT_NAME);
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
