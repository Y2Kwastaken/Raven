package sh.miles.raven.datamapper.parse;

import java.util.HashMap;
import java.util.Map;

import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.datamapper.api.DataBlock;
import sh.miles.raven.datamapper.api.DataContainer;

public final class DataBlockParser {

    private DataBlockParser() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Creates a new instance of a data block
     * 
     * @param <T>           the type of the data block
     * @param blockInstance the instance of the data block
     * @param parentSection the section to create the data block from
     * @return the created data block
     */
    public static <T extends DataBlock<K>, K> void parseDataBlock(final T blockInstance,
            final DatabaseSection parentSection) {
        final Map<K, Class<? extends DataContainer>> containers = blockInstance.getContainerTypes();
        final Map<K, DataContainer> parsedContainers = new HashMap<>();

        containers.forEach((containerKey, clazz) -> {
            final DatabaseSection internalSection = parentSection.getSection(containerKey.toString());
            final DataContainer container = DataContainerParser.createDataContainer(clazz, internalSection);

            parsedContainers.put(containerKey, container);
        });

        DataParseUtils.setDeclaredField(blockInstance, "containers", parsedContainers);
    }

    /**
     * Writes a data block to a section
     * 
     * @param <T>           the type of the data block
     * @param blockInstance the instance of the data block
     * @param parentSection the section to write the data block to
     */
    public static <T extends DataBlock<K>, K> void writeDataBlock(final T blockInstance,
            final DatabaseSection parentSection) {
        final Map<K, DataContainer> containers = blockInstance.getContainers();

        containers.forEach((containerKey, container) -> {
            final DatabaseSection section = parentSection.getSection(containerKey.toString());
            DataContainerParser.writeDataContainer(container, section);
        });
    }

    public static <T extends DataBlock<K>, K> void removeDataBlock(final T blockInstance,
            final DatabaseSection parentSection) {
        final Map<K, DataContainer> containers = blockInstance.getContainers();

        containers.forEach((containerKey, container) -> {
            final DatabaseSection section = parentSection.getSection(containerKey.toString());
            DataContainerParser.removeDataContainer(container, section);
        });
    }
}
