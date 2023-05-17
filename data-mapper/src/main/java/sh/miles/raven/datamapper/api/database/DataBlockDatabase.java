package sh.miles.raven.datamapper.api.database;

import java.util.function.Supplier;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.datamapper.api.DataBlock;
import sh.miles.raven.datamapper.parse.DataBlockParser;

/**
 * A database for storing data blocks.
 */
@SuppressWarnings("all")
public class DataBlockDatabase {

    private final Database database;

    /**
     * Creates a new data block database.
     * 
     * @param database The database to use.
     */
    public DataBlockDatabase(Database database) {
        this.database = database;
    }

    /**
     * Gets a data block from the database.
     * 
     * @param <T>        The type of the data block.
     * @param <K>        The type of the key.
     * @param collection The collection to get the data block from.
     * @param id         The id of the data block.
     * @param supplier   The supplier to create the data block.
     * @return The data block.
     */
    public <T extends DataBlock<K>, K> T getDataBlock(String collection, String id, Supplier<T> supplier) {
        final DatabaseSection section;
        if (!this.database.hasSection(collection, id)) {
            section = this.database.createSection(collection, id);
        } else {
            section = this.database.getSection(collection, id);
        }

        final DataBlock<K> dataBlock = supplier.get();
        DataBlockParser.parseDataBlock(dataBlock, section);
        return (T) dataBlock;
    }

    /**
     * Sets a data block in the database.
     * 
     * @param <T>        The type of the data block.
     * @param <K>        The type of the key.
     * @param collection The collection to set the data block in.
     * @param id         The id of the data block.
     * @param dataBlock  The data block to set.
     */
    public <T extends DataBlock<K>, K> void setDataBlock(String collection, String id, T dataBlock) {
        final DatabaseSection section = this.database.getSection(collection, id);
        DataBlockParser.writeDataBlock(dataBlock, section);
    }

    /**
     * Removes a data block from the database.
     * 
     * @param collection the collection to remove the data block from
     * @param id         the id of the data block
     */
    public void removeDataBlock(String collection, String id) {
        this.database.deleteSection(collection, id);
    }

    /**
     * Checks if a data block exists in the database.
     * 
     * @param collection the collection to check
     * @param id         the id of the data block
     * @return true if the data block exists, false otherwise
     */
    public boolean hasSection(String collection, String id) {
        return this.database.hasSection(collection, id);
    }

    public Database getDatabase() {
        return database;
    }

}
