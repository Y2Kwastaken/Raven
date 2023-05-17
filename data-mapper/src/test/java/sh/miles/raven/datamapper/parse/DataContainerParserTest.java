package sh.miles.raven.datamapper.parse;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.datamapper.DataContainerMock;
import sh.miles.raven.provider.nitrite.database.NitriteDatabaseConnection;

public class DataContainerParserTest {

    public static final String NITRITE_CONNECTION_STRING = System.getenv("NITRITE_CONNECTION");
    public static final String DATABASE_NAME = "nitrite";
    public static final String COLLECTION_NAME = "test-collection";
    public static final String DOCUMENT_NAME = "test-document";

    @Test
    public void testCreateDataContainer() {
        final DataContainerMock dataContainer = DataContainerParser.createDataContainer(DataContainerMock.class,
                getDatabaseSection());

        assertNotNull(dataContainer, "dataContainer cannot be null");

        final String string = dataContainer.getString();
        assertNotEquals("default", string, "string cannot be default");
        
        final int integer = dataContainer.getInteger();
        assertNotEquals(-1, integer, "integer cannot be default");
    }

    public DatabaseSection getDatabaseSection() {
        final NitriteDatabaseConnection connection = new NitriteDatabaseConnection();
        try {
            connection.connect(NITRITE_CONNECTION_STRING);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        }

        final Database database = connection.getDatabase(DATABASE_NAME);

        return database.getSection(COLLECTION_NAME, DOCUMENT_NAME);
    }

}
