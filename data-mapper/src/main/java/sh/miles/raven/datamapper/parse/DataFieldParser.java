package sh.miles.raven.datamapper.parse;

import org.jetbrains.annotations.NotNull;

import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.database.exception.DatabaseInsertionException;
import sh.miles.raven.datamapper.api.fields.DataField;
import sh.miles.raven.datamapper.api.fields.DataTuple;

public final class DataFieldParser {

    public static final String DATA_TUPLE_VALUE_FIELD_NAME = "value";

    private DataFieldParser() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @SuppressWarnings("unchecked")
    public static <T> void fillField(@NotNull final DatabaseSection section, @NotNull final DataField<T> field) {

        final Object value = section.get(field.getKey());
        if (value == null) {
            return;
        }

        field.setValue((T) value);
    }

    public static <T> void fillTuple(@NotNull final DatabaseSection section, @NotNull final DataTuple<T> field) {
        final Object value = section.get(field.getKey());
        if (value == null) {
            return;
        }

        final boolean result = DataParseUtils.setField(field, DATA_TUPLE_VALUE_FIELD_NAME, value);
        if (!result) {
            throw new IllegalStateException("Failed to set field value");
        }
    }

    /**
     * Writes a field to a section
     * 
     * @param <T>     the type of the field
     * @param section the section to write the field to
     * @param field   the field to write
     */
    public static <T> void writeField(@NotNull final DatabaseSection section, @NotNull final DataField<T> field) {
        try {
            section.set(field.getKey(), field.getValue());
        } catch (DatabaseInsertionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a tuple to a section
     * 
     * @param <T>     the type of the tuple
     * @param section the section to write the tuple to
     * @param field   the tuple to write
     */
    public static <T> void writeTuple(@NotNull final DatabaseSection section, @NotNull final DataTuple<T> field) {
        try {
            section.set(field.getKey(), field.getValue());
        } catch (DatabaseInsertionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a field from a section
     * 
     * @param <T>     the type of the field
     * @param section the section to remove the field from
     * @param field   the field to remove
     */
    public static <T> void removeField(@NotNull final DatabaseSection section, @NotNull final DataField<T> field) {
        section.remove(field.getKey());
    }

    /**
     * Removes a tuple from a section
     * 
     * @param <T>     the type of the tuple
     * @param section the section to remove the tuple from
     * @param field   the tuple to remove
     */
    public static <T> void removeTuple(@NotNull final DatabaseSection section, @NotNull final DataTuple<T> field) {
        section.remove(field.getKey());
    }
}
