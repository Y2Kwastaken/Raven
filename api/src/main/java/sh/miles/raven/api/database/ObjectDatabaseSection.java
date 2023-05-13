package sh.miles.raven.api.database;

import org.jetbrains.annotations.NotNull;

import sh.miles.raven.api.database.exception.DatabaseInsertionException;
import sh.miles.raven.api.interfacing.object.DataObject;

/**
 * ObjectDatabaseSection specialized in retrieving entire objects versus
 * individual fields.
 * to use this, you must either use one of two methods: Annotation based or
 * interface based. Annotation based is the easiest to use, but interface based
 * is the most powerful as it allows default values and runtime section
 * selection.
 */
public interface ObjectDatabaseSection {

    /**
     * Gets a DataObject from the database.
     * 
     * @param <T>   The type of DataObject to return.
     * @param clazz The class of the DataObject to return.
     * @return The DataObject at the path.
     */
    <T extends DataObject> T getDataObject(@NotNull final Class<T> clazz);

    /**
     * Gets a DataObject from the database.
     * 
     * @deprecated the use of this method shows a possibly flawed data design. If
     *             you think you need to use this method, please continue to use it,
     *             but consider refactoring your data design.
     * 
     * @param <T>   The type of DataObject to return.
     * @param path  The path to the DataObject.
     * @param clazz The class of the DataObject to return.
     * @return The DataObject at the path.
     */
    @Deprecated
    <T extends DataObject> T getDataObject(@NotNull final String path, @NotNull final Class<T> clazz);

    /**
     * Gets a DataObject from the database.
     * 
     * @deprecated Use {@link #getDataObject(String, Class)} instead. This method
     *             may be removed or reworked there is no guarantee of it's long
     *             gevity.
     * 
     * @param <T>   The type of DataObject to return.
     * @param path  The path to the DataObject.
     * @param clazz The class of the DataObject to return.
     * @return The DataObject at the path.
     */
    @Deprecated
    <T> T getAnnotatedObject(@NotNull final Class<T> clazz);

    /**
     * Sets a DataObject in the database. This will overwrite any existing data at
     * the path.
     * 
     * @param object The DataObject to set.
     */
    void setDataObject(@NotNull final DataObject object) throws DatabaseInsertionException;
}
