package sh.miles.raven.datamapper.parse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseConnection;
import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.datamapper.DataBlockMock;
import sh.miles.raven.datamapper.DataContainerMock;
import sh.miles.raven.datamapper.api.DataContainer;
import sh.miles.raven.provider.nitrite.database.NitriteDatabaseConnection;

public class DataBlockParserTest {

    public static final String NITRITE_CONNECTION_STRING = System.getenv("NITRITE_CONNECTION");
    public static final String DATABASE_NAME = "nitrite";

    @Test
    public void testParseDataBlock() {
        final DataBlockMock dataBlock = new DataBlockMock("test-document", getDatabase());
        assertNotNull(dataBlock, "dataBlock cannot be null");
        final DataContainer dataContainer = dataBlock.getContainer("object");
        assertNotNull(dataContainer, "dataContainer cannot be null");
        final DataContainerMock dataContainerMock = (DataContainerMock) dataContainer;
        assertEquals("Hello, World!", dataContainerMock.getString(),
                "dataContainerMock.testString must be \"Hello, World!\"");
        assertEquals(5, dataContainerMock.getInteger(), "dataContainerMock.testInteger must be 5");
    }

    public Database getDatabase() {
        DatabaseConnection connection = new NitriteDatabaseConnection();
        try {
            connection.connect(NITRITE_CONNECTION_STRING);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return connection.getDatabase(DATABASE_NAME);
    }
}
