package sh.miles.raven.datamapper.api.fields;

/**
 * DataTuple is a class that is much like a DataField, but ensures immutability
 * of the data. This allows for data to be more explicitly marked as unchanging
 * or not to be changed.
 * 
 * @param <T> The type of the data to be stored in the DataTuple.
 */
public class DataTuple<T> {

    private final String key;
    private T value;

    /**
     * Creates a new DataTuple with the given key and value.
     * 
     * @param key   The key of the DataTuple.
     * @param value The value of the DataTuple.
     */
    public DataTuple(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public T getValue() {
        return this.value;
    }

}
