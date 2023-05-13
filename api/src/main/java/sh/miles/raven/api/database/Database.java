package sh.miles.raven.api.database;

import org.jetbrains.annotations.NotNull;

/**
 * Database is the main interface
 */
public interface Database {

    /**
     * Returns a DatabaseSection for the given section
     * 
     * @param collection the name of the collection
     * @param id         the id of the target section
     * @return the DatabaseSection
     */
    @NotNull
    DatabaseSection getSection(@NotNull String collection, @NotNull String id);

    /**
     * Returns a ObjectDatabaseSection for the given section
     * 
     * @param collection the name of the collection
     * @param id         the id of the target section
     * @return the ObjectDatabaseSection
     */
    @NotNull
    ObjectDatabaseSection getObjectSection(@NotNull String collection, @NotNull String id);

    /**
     * Creates a new DatabaseSection for the given section
     * 
     * @param collection the name of the collection
     * @param id         the id of the target section
     * @return the DatabaseSection
     */
    @NotNull
    DatabaseSection createSection(@NotNull String collection, @NotNull String id);

    /**
     * Checks if the given section exists
     * 
     * @param collection the name of the collection
     * @param id         the id of the target section
     * @return true if the section exists, false otherwise
     */
    boolean hasSection(@NotNull String collection, @NotNull String id);

    /**
     * Deletes the given section
     * 
     * @param collection the name of the collection
     * @param id         the id of the target section
     * @return true if the section was deleted, false otherwise
     */
    boolean deleteSection(@NotNull String collection, @NotNull String id);

    /**
     * Creates a new collection
     * 
     * @param collection the name of the collection
     * @return true if the collection was created, false otherwise
     */
    boolean createCollection(@NotNull String collection);

    /**
     * If the database has the given collection
     * 
     * @param collection the name of the collection
     * @return true if the database has the collection, false otherwise
     */
    boolean hasCollection(@NotNull String collection);

    /**
     * Deletes the given collection
     * 
     * @param collection the name of the collection
     * @return true if the collection was deleted, false otherwise
     */
    boolean deleteCollection(@NotNull String collection);
}
