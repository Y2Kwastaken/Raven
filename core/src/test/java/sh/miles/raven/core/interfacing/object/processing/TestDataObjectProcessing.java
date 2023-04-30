package sh.miles.raven.core.interfacing.object.processing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import sh.miles.raven.api.database.exception.DatabaseConnectionException;
import sh.miles.raven.api.interfacing.object.DataField;
import sh.miles.raven.core.interfacing.processing.ProcessUtils;
import sh.miles.raven.provider.mongodb.database.MongoDatabaseConnection;
import sh.miles.raven.provider.mongodb.database.MongoDatabaseSection;

public class TestDataObjectProcessing {

    public static final String CONNECTION_STRING = System.getenv("MONGO_ATLAS_CONNECTION");
    public static final String DATABASE_NAME = "test";
    public static final String COLLECTION_NAME = "test-collection";

    private static final MongoDatabaseSection section = connect();

    @Test
    @SuppressWarnings("unchecked")
    public void testGetDataFieldsFromFields() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final MockObjectProcess object = ProcessUtils.createInstance(MockObjectProcess.class);
        final Method method = DataObjectProcessor.class.getDeclaredMethod("getDataFieldsFromFields", Object.class);
        method.setAccessible(true);
        DataField<Object>[] fields = (DataField<Object>[]) method.invoke(null, object);
        method.setAccessible(false);
        assertNotNull(fields, "The fields should not be null.");
                
        for(final DataField<Object> field : fields) {
            Logger.getGlobal().info(field.getKey());
            assertNotNull(field, "The field should not be null.");
        }
    }

    @Test
    public void testDataObjectParse() {
        final MockObjectProcess object = DataObjectProcessor.createDataInstance(MockObjectProcess.class, section);
        assertNotNull(object, "The object should not be null.");

        assertEquals("Hello, World!", object.getStringField().getValue(),
                "The string field should be \"Hello, World!\".");
        assertEquals(5, object.getIntegerField().getValue(), "The integer field should be 5.");

        final MockObjectProcess.InternalMockObjectProcess internalObject = object.getInternalObjectField().getValue();
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
