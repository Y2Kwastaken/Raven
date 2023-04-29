package sh.miles.raven.api.database;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import sh.miles.raven.api.database.exception.DatabaseInsertionException;

/**
 * DatabaseSection is a section of the database that is used to store and
 * retrieve data from the database in a structured way
 */
public interface DatabaseSection {

    /**
     * getString returns the string value of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    String getString(@NotNull String key);

    /**
     * getInteger returns the integer value of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    int getInt(@NotNull String key);

    /**
     * getLong returns the long value of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    long getLong(@NotNull String key);

    /**
     * getDouble returns the double value of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    double getDouble(@NotNull String key);

    /**
     * getBoolean returns the boolean value of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    boolean getBoolean(@NotNull String key);

    /**
     * getByte returns the byte value of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    byte getByte(@NotNull String key);

    /**
     * getBytes returns the byte array value of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    byte[] getBytes(@NotNull String key);

    /**
     * Gets the value of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    Object get(@NotNull String key);

    /**
     * getSection returns the section of the given name
     * 
     * @param name the name of the section
     * @return the section
     */
    DatabaseSection getSection(@NotNull String name);

    /**
     * getStringList returns the list of strings of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    List<String> getStringList(@NotNull String key);

    /**
     * getIntegerList returns the list of integers of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    List<Integer> getIntegerList(@NotNull String key);

    /**
     * getLongList returns the list of longs of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    List<Long> getLongList(@NotNull String key);

    /**
     * getDoubleList returns the list of doubles of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    List<Double> getDoubleList(@NotNull String key);

    /**
     * getBooleanList returns the list of booleans of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    List<Boolean> getBooleanList(@NotNull String key);

    /**
     * getByteList returns the list of bytes of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    List<Byte> getByteList(@NotNull String key);

    /**
     * getBytesList returns the list of byte arrays of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    List<byte[]> getBytesList(@NotNull String key);

    /**
     * getList returns the list of objects of the given key
     * 
     * @param key the key to get the value of
     * @return the value of the key
     */
    List<Object> getList(@NotNull String key);

    /**
     * set sets the value of the given key
     * 
     * @param key   the key to set the value of
     * @param value the value to set
     * @throws DatabaseInsertionException if an error occurs while inserting the
     *                                    value
     */
    void set(@NotNull String key, @NotNull Object value) throws DatabaseInsertionException;

    /**
     * Removes the given key from the database
     * 
     * @param key the key to remove
     */
    void remove(@NotNull String key);
}
