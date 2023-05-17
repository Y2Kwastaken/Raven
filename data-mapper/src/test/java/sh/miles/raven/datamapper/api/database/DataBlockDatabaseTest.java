package sh.miles.raven.datamapper.api.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseConnection;
import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.datamapper.api.DataBlock;
import sh.miles.raven.datamapper.api.database.mock.DataContainerMock0;
import sh.miles.raven.datamapper.api.database.mock.DataContainerMock1;
import sh.miles.raven.provider.nitrite.database.NitriteDatabaseConnection;

public class DataBlockDatabaseTest {

    public static final String CONNECTION_STRING = System.getenv("NITRITE_CONNECTION");
    public static final String DATABASE_NAME = "nitrite";
    public static final String COLLECTION_NAME = "data-blocks";
    public static final String PARENT_DOCUMENT_NAME = "user";

    @Test
    public void testGetDataBlock() {
        final DataBlockDatabase database = getDataBlockDatabase();

        final DataBlock<String> block = database.getDataBlock(COLLECTION_NAME, PARENT_DOCUMENT_NAME, () -> {
            final DataBlock<String> b = new DataBlock<>();
            b.addContainer("container0", DataContainerMock0.class);
            b.addContainer("container1", DataContainerMock1.class);
            return b;
        });

        assertNotNull(block, "DataBlock should not be null");
        final DataContainerMock0 container0 = (DataContainerMock0) block.getContainer("container0");
        assertNotNull(container0, "DataContainerMock0 should not be null");
        assertEquals(DataContainerMock0.FIELD_0_DEFAULT, container0.getField0(), "DataContainerMock0 field0 should be equal to default value");
        assertEquals(DataContainerMock0.FIELD_1_DEFAULT, container0.getField1(), "DataContainerMock0 field1 should be equal to default value");

        final DataContainerMock1 container1 = (DataContainerMock1) block.getContainer("container1");
        assertNotNull(container1, "DataContainerMock1 should not be null");
        assertEquals(DataContainerMock1.FIELD_0_DEFAULT, container1.getField0(), "DataContainerMock1 field0 should be equal to default value");
        assertEquals(DataContainerMock1.FIELD_1_DEFAULT, container1.getField1(), "DataContainerMock1 field1 should be equal to default value");
    }   

    @Test
    public void testSetDataBlock(){
        final DataBlockDatabase database = getDataBlockDatabase();
        final DataBlock<String> block = database.getDataBlock(COLLECTION_NAME, PARENT_DOCUMENT_NAME, () -> {
            final DataBlock<String> b = new DataBlock<>();
            b.addContainer("container0", DataContainerMock0.class);
            b.addContainer("container1", DataContainerMock1.class);
            return b;
        });

        final DataContainerMock0 container0 = (DataContainerMock0) block.getContainer("container0");
        container0.setField0("value0");
        container0.setField1("value1");

        final DataContainerMock1 container1 = (DataContainerMock1) block.getContainer("container1");
        container1.setField0(0);
        container1.setField1(1);

        database.setDataBlock(COLLECTION_NAME, PARENT_DOCUMENT_NAME, block);

        final DataBlock<String> block2 = database.getDataBlock(COLLECTION_NAME, PARENT_DOCUMENT_NAME, () -> {
            final DataBlock<String> b = new DataBlock<>();
            b.addContainer("container0", DataContainerMock0.class);
            b.addContainer("container1", DataContainerMock1.class);
            return b;
        });

        final DataContainerMock0 container0_2 = (DataContainerMock0) block2.getContainer("container0");
        assertNotNull(container0_2, "DataContainerMock0 should not be null");
        assertEquals("value0", container0_2.getField0(), "DataContainerMock0 field0 should be equal to value0");
        assertEquals("value1", container0_2.getField1(), "DataContainerMock0 field1 should be equal to value1");

        final DataContainerMock1 container1_2 = (DataContainerMock1) block2.getContainer("container1");
        assertNotNull(container1_2, "DataContainerMock1 should not be null");
        assertEquals(0, container1_2.getField0(), "DataContainerMock1 field0 should be equal to 0");
    }

    @Test
    public void testRemoveDataBlock(){
        final DataBlockDatabase database = getDataBlockDatabase();
        assertDoesNotThrow(() -> database.removeDataBlock(COLLECTION_NAME, PARENT_DOCUMENT_NAME), "Should not throw exception");
    }

    public static final DataBlockDatabase getDataBlockDatabase() {
        return new DataBlockDatabase(getDatabase());
    }

    public static Database getDatabase() {
        final DatabaseConnection connection = new NitriteDatabaseConnection();
        try {
            connection.connect(CONNECTION_STRING);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return connection.getDatabase(DATABASE_NAME);
    }

}
