package sh.miles.raven.datamapper.parse;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.datamapper.api.DataContainer;
import sh.miles.raven.datamapper.api.fields.DataField;
import sh.miles.raven.datamapper.api.fields.DataTuple;

public final class DataContainerParser {

    private DataContainerParser() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Creates a new instance of a data container
     * 
     * @param <T>     the type of the data container
     * @param clazz   the class of the data container
     * @param section the section to create the data container from
     * @return the new instance of the data container
     */
    public static <T extends DataContainer> T createDataContainer(@NotNull final Class<T> clazz,
            @NotNull final DatabaseSection section) {
        Preconditions.checkNotNull(clazz, "clazz cannot be null");
        Preconditions.checkNotNull(section, "section cannot be null");

        final T instance = DataParseUtils.createInstance(clazz);
        if (instance == null) {
            throw new IllegalStateException("Failed to create instance of " + clazz.getName());
        }

        final DataField<Object>[] dataFields = DataParseUtils.getFields(instance);
        for (final DataField<Object> dataField : dataFields) {
            DataFieldParser.fillField(section, dataField);
        }

        final DataTuple<Object>[] dataTuples = DataParseUtils.getTuples(instance);
        for (final DataTuple<Object> dataTuple : dataTuples) {
            DataFieldParser.fillTuple(section, dataTuple);
        }

        return instance;
    }

    /**
     * Writes a data container to a section
     * 
     * @param <T>       the type of the data container
     * @param container the data container
     * @param section   the section to write the data container to
     */
    public static <T extends DataContainer> void writeDataContainer(@NotNull final T container,
            @NotNull final DatabaseSection section) {
        Preconditions.checkNotNull(container, "container cannot be null");
        Preconditions.checkNotNull(section, "section cannot be null");

        final DataField<Object>[] dataFields = DataParseUtils.getFields(container);
        for (final DataField<Object> dataField : dataFields) {
            DataFieldParser.writeField(section, dataField);
        }

        final DataTuple<Object>[] dataTuples = DataParseUtils.getTuples(container);
        for (final DataTuple<Object> dataTuple : dataTuples) {
            DataFieldParser.writeTuple(section, dataTuple);
        }
    }

    public static <T extends DataContainer> void removeDataContainer(@NotNull final T container,
            @NotNull final DatabaseSection section) {
        Preconditions.checkNotNull(container, "container cannot be null");
        Preconditions.checkNotNull(section, "section cannot be null");

        final DataField<Object>[] dataFields = DataParseUtils.getFields(container);
        for (final DataField<Object> dataField : dataFields) {
            DataFieldParser.removeField(section, dataField);
        }

        final DataTuple<Object>[] dataTuples = DataParseUtils.getTuples(container);
        for (final DataTuple<Object> dataTuple : dataTuples) {
            DataFieldParser.removeTuple(section, dataTuple);
        }
    }

}
