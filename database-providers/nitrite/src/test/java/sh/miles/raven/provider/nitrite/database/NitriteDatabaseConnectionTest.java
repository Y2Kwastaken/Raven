package sh.miles.raven.provider.nitrite.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class NitriteDatabaseConnectionTest {

    public static final String CONNECTION_STRING = System.getenv("NITRITE_CONNECTION");

    @Test
    public void testConnect() {
        final NitriteDatabaseConnection connection = new NitriteDatabaseConnection();

        assertDoesNotThrow(() -> connection.connect(CONNECTION_STRING), "Failed to connect to database");
        connection.disconnect();
    }

    @Test
    public void testDatabase() {
        final NitriteDatabaseConnection connection = new NitriteDatabaseConnection();

        assertDoesNotThrow(() -> connection.connect(CONNECTION_STRING), "Failed to connect to database");

        connection.getDatabase("nitrite");

        connection.disconnect();
    }

}
