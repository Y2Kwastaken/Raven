package sh.miles.raven.datamapper.v2.data;

/**
 * DataField is a class that represents a field in a Database. It is represented by a key value pair. The key is the
 * name of the field in the database and the value represents the value of the field. The value can be any type as long
 * as it supported for retrieval by the database.
 *
 * @param <T> The type of the data to be stored in the DataField.
 */
public class DataField<T> {

    private String key;
    private T value;

    /**
     * Creates a new DataField with the given key and value.
     *
     * @param key          The key of the DataField.
     * @param defaultValue The value of the DataField.
     */
    public DataField(String key, T defaultValue) {
        this.key = key;
        this.value = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
