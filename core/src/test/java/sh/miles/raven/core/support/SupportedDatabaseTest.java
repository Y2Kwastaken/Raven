package sh.miles.raven.core.support;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class SupportedDatabaseTest {

    @Test
    public void testGetConnectionClass() {
        final SupportedDatabase mongo = SupportedDatabase.MONGODB;
        assertDoesNotThrow(() -> mongo.getConnection(),
                "getConnectionClassPath() should not throw an IllegalStateException.");
    }
}
