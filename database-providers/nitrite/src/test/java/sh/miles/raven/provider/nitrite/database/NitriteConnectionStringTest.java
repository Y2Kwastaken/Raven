package sh.miles.raven.provider.nitrite.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import sh.miles.raven.provider.nitrite.database.connection.NitriteConnectionString;

public class NitriteConnectionStringTest {

    public static final String CONNECTION_STRING_USERNAME = "myusername";
    public static final String CONNECTION_STRING_PASSWORD = "secure0password!";
    public static final String CONNECTION_STRING_FILEPATH = "/test";
    public static final String CONNECTION_STRING = String.format("nitrite:%s@%s:%s", CONNECTION_STRING_USERNAME,
            CONNECTION_STRING_PASSWORD, CONNECTION_STRING_FILEPATH);

    @Test
    public void testConnectionStringParse() {
        final String[] connectionStringParts = NitriteConnectionString.parseConnectionString(CONNECTION_STRING);
        assertEquals(3, connectionStringParts.length, "Connection string should be parsed correctly");
        assertEquals(CONNECTION_STRING_USERNAME, connectionStringParts[0], "Username should be parsed correctly");
        assertEquals(CONNECTION_STRING_PASSWORD, connectionStringParts[1], "Password should be parsed correctly");
        assertEquals(CONNECTION_STRING_FILEPATH, connectionStringParts[2], "Filepath should be parsed correctly");
    }

}
