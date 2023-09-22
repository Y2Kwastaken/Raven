package sh.miles.raven.datamapper.v2.data;

/**
 * DataTuple is a class that is much like a DataField, but ensures immutability of the data. This allows for data to be
 * more explicitly marked as unchanging or not to be changed.
 *
 * @param <T> The type of the data to be stored in the DataTuple.
 */
public class DataTuple<T> extends DataField<T> {

    private boolean lock = false;

    /**
     * Creates a new DataTuple with the given key and value.
     *
     * @param key   The key of the DataTuple.
     * @param value The value of the DataTuple.
     */
    public DataTuple(String key, T value) {
        super(key, value);
    }

    @Override
    public void setKey(String key) {
        throw new UnsupportedOperationException("You can not change DataTuple values");
    }

    @Override
    public void setValue(T value) {
        if (lock) {
            return;
        }
        super.setValue(value);
    }

    public void lock() {
        lock = true;
    }
}

