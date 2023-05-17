package sh.miles.raven.datamapper.api;

import java.util.HashMap;
import java.util.Map;

/**
 * DataBlocks take a more complex approach to storing data. DataBlocks are used
 * to block and organize data. DataBlocks can contain only DataContainers. Data
 * blocks should only be parsed after you add all relevant containers to the
 * block
 * e.g.
 * 
 * @param <T> the type of which the DataContainer should be indexed by. Note, T
 *            must have a valid
 */
public class DataBlock<T> {

    private final Map<T, Class<? extends DataContainer>> containerTypes;
    private Map<T, DataContainer> containers;

    /**
     * Creates a new DataBlock.
     */
    public DataBlock() {
        this.containerTypes = new HashMap<>();
        this.containers = new HashMap<>();
    }

    /**
     * Adds a container to the DataBlock. This container will be parsed when the
     * 
     * @param key           The key to index the container by.
     * @param containerType The type of the container to be added.
     */
    public void addContainer(T key, Class<? extends DataContainer> containerType) {
        this.containerTypes.put(key, containerType);
    }

    public Map<T, Class<? extends DataContainer>> getContainerTypes() {
        return new HashMap<>(this.containerTypes);
    }

    /**
     * Gets the container with the given key.
     * 
     * @param key The key of the container to get.
     * @return The container with the given key.
     */
    public DataContainer getContainer(T key) {
        return this.containers.get(key);
    }

    public Map<T, DataContainer> getContainers() {
        return new HashMap<>(this.containers);
    }
}
