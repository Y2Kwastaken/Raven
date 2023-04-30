package sh.miles.raven.api.interfacing.object;

import org.jetbrains.annotations.NotNull;

/**
 * This interface is used to mark classes that are used to represent data
 * fields. Each DataField in a DataObject are treated as a single key-value
 * pair.
 * 
 * @param <T> The type of the value of the field.
 */
public class DataField<T> {

    private final String key;
    private T value;

    /**
     * Creates a new DataField with the given key.
     * 
     * @param key          The key of the field.
     * @param defaultValue The default value of the field.
     */
    public DataField(String key, T defaultValue) {
        this.key = key;
        this.value = defaultValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @NotNull
    public String getKey() {
        return key;
    }

}
