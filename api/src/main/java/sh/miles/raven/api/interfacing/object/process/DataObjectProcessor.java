package sh.miles.raven.api.interfacing.object.process;

import java.lang.reflect.Field;

import com.google.common.base.Preconditions;

import sh.miles.raven.api.conversion.TypeConversionManager;
import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.interfacing.ProcessUtils;
import sh.miles.raven.api.interfacing.object.DataField;
import sh.miles.raven.api.interfacing.object.DataObject;

/**
 * Class for processing {@link DataObject} objects.
 * 
 */
public final class DataObjectProcessor {

    private static final TypeConversionManager typeConversionManager = TypeConversionManager.getInstance();

    /**
     * Creates a new instance of a given {@link Class} and adds it the given data
     */
    private DataObjectProcessor() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Creates a new instance of a given {@link Class} and adds it the given data
     * 
     * @param <T>           The {@link Class} to create an instance of.
     * @param clazz         The {@link Class} to create an instance of.
     * @param parentSection The {@link DatabaseSection} to get the data from.
     * @return A new instance of the given {@link Class} with the data from the
     */
    public static <T> T createDataInstance(final Class<T> clazz, final DatabaseSection parentSection) {
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");
        Preconditions.checkNotNull(parentSection, "The given parent section cannot be null.");

        return fillFieldsValues(ProcessUtils.createInstance(clazz), parentSection);
    }

    /**
     * Creates a new instance of a given {@link DataObject} and adds it the given
     * 
     * @param <T>           The {@link Class} to create an instance of.
     * @param instance      The {@link Class} to create an instance of.
     * @param parentSection The {@link DatabaseSection} to get the data from.
     * @return A new instance of the given {@link Class} with the data from the
     */
    public static <T extends DataObject> T createDataInstance(final T instance, final DatabaseSection parentSection) {
        Preconditions.checkNotNull(instance, "The given instance cannot be null.");
        Preconditions.checkNotNull(parentSection, "The given parent section cannot be null.");

        final DataField<Object>[] fields = getDataFieldsFromFields(instance);
        for (final DataField<Object> field : fields) {
            fillDataField(field, parentSection);
        }

        return instance;
    }

    private static <T> T fillFieldsValues(final T instance, final DatabaseSection section) {
        Preconditions.checkNotNull(instance, "The given instance cannot be null.");
        Preconditions.checkNotNull(section, "The given section cannot be null.");

        final DataField<Object>[] fields = getDataFieldsFromFields(instance);
        for (final DataField<Object> field : fields) {
            fillDataField(field, section);
        }

        return instance;
    }

    private static DataField<Object> fillDataField(final DataField<Object> dataField, DatabaseSection parentSection) {
        Preconditions.checkNotNull(dataField, "The given data field cannot be null.");
        Preconditions.checkNotNull(parentSection, "The given parent section cannot be null.");

        final Object value = parentSection.get(dataField.getKey());

        if (value.getClass().isPrimitive()) {
            dataField.setValue(value);
        } else if (typeConversionManager.isRegistered(value.getClass())) {
            dataField.setValue(typeConversionManager.convert(value, dataField.getValue().getClass()));
        } else if (ProcessUtils.classHasInterface(dataField.getValue().getClass(), DataObject.class)) {
            dataField.setValue(
                    createDataInstance(dataField.getValue().getClass(), parentSection.getSection(dataField.getKey())));
        } else {
            dataField.setValue(dataField.getValue().getClass().cast(value));
        }

        return dataField;
    }

    @SuppressWarnings("unchecked")
    private static DataField<Object>[] getDataFieldsFromFields(final Object instance) {
        Preconditions.checkNotNull(instance, "The given instance cannot be null.");
        final Field[] dataFieldFields = ProcessUtils.getFieldsOfType(instance.getClass(), DataField.class);

        final DataField<Object>[] dataFields = new DataField[dataFieldFields.length];
        for (int i = 0; i < dataFieldFields.length; i++) {
            dataFields[i] = (DataField<Object>) ProcessUtils.getFieldValue(dataFieldFields[i], instance);
        }
        return dataFields;
    }

}
