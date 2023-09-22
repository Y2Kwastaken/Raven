package sh.miles.raven.datamapper.v2.interpreter;

import org.dizitart.no2.collection.Document;
import org.junit.jupiter.api.Test;
import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.datamapper.v2.DataContainerMock;
import sh.miles.raven.provider.nitrite.database.NitriteDatabaseConnection;

import static org.junit.jupiter.api.Assertions.*;

public class DataContainerInterpreterTest {

    public static final String NITRITE_CONNECTION_STRING = System.getenv("NITRITE_CONNECTION");
    public static final String DATABASE_NAME = "nitrite";
    public static final String COLLECTION_NAME = "test-collection";
    public static final String DOCUMENT_NAME = "test-document";

    @Test
    public void should_Parse_Container() {
        final DatabaseSection section = getDatabaseSection();
        final DataContainerMock mock = assertDoesNotThrow(() -> DataContainerInterpreter.getContainerFromSection(section, DataContainerMock.class));
        assertNotNull(mock);
        assertEquals("Hello, World!", mock.getString());
        assertEquals(5, mock.getInteger());
    }

    @Test
    public void should_Set_Container() {
        final DatabaseSection section = getDatabaseSection();
        final DataContainerMock mock = new DataContainerMock();
        mock.setString("!Jello!");
        DataContainerInterpreter.setContainerToSection(section.getSection("scontainer"), mock);
        assertEquals("!Jello!", section.get("scontainer.string"));
        assertEquals(-1, section.get("scontainer.int"));
    }

    @Test
    public void should_Remove_Container() {
        final DatabaseSection section = getDatabaseSection();
        final DataContainerMock mock = new DataContainerMock();
        mock.setString("!Jello!");
        DataContainerInterpreter.removeContainerFromSection(section.getSection("scontainer"), mock);
        assertNull(section.get("scontainer.string"));
        assertNull(section.get("scontainer.int"));
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
