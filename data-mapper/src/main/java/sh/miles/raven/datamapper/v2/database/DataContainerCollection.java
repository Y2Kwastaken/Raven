package sh.miles.raven.datamapper.v2.database;

import org.jetbrains.annotations.NotNull;
import sh.miles.raven.api.database.Database;
import sh.miles.raven.datamapper.v2.DataContainer;
import sh.miles.raven.datamapper.v2.interpreter.DataContainerInterpreter;

/**
 * Maps all data to DataContainers
 */
public class DataContainerCollection {

    private final Database database;
    private final String collection;

    public DataContainerCollection(@NotNull final Database database, @NotNull final String collection) {
        this.database = database;
        this.collection = collection;
    }

    /**
     * Sets a container at a specified ID within this collection
     *
     * @param id        the id of the container
     * @param container the container
     */
    public void setContainer(@NotNull final String id, @NotNull final DataContainer container) {
        DataContainerInterpreter.setContainerToSection(database.getSection(this.collection, id), container);
    }

    /**
     * Gets a data container of the type from the specified ID
     *
     * @param id   the id
     * @param type the type
     * @param <T>  the Type
     * @return the DataContainer
     */
    public <T extends DataContainer> T getDataContainer(@NotNull final String id, Class<T> type) {
        return DataContainerInterpreter.getContainerFromSection(database.getSection(this.collection, id), type);
    }

    /**
     * Removes a data container from the database
     *
     * @param id        the id
     * @param container the container to remove
     */
    public void removeDataContainer(@NotNull final String id, DataContainer container) {
        DataContainerInterpreter.removeContainerFromSection(database.getSection(this.collection, id), container);
    }

}
