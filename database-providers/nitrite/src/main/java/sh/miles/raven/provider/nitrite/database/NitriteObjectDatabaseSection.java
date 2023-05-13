package sh.miles.raven.provider.nitrite.database;

import org.jetbrains.annotations.NotNull;

import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.database.ObjectDatabaseSection;
import sh.miles.raven.api.interfacing.object.DataObject;
import sh.miles.raven.core.interfacing.annotation.processing.DataAnnotationProcessor;
import sh.miles.raven.core.interfacing.object.processing.DataObjectProcessor;

public class NitriteObjectDatabaseSection implements ObjectDatabaseSection {

    private final DatabaseSection section;

    /**
     * NitriteObjectDatabaseSection creates a new instance of NitriteDatabaseSection
     * 
     * @param section the section to use
     */
    public NitriteObjectDatabaseSection(DatabaseSection section) {
        this.section = section;
    }

    @Override
    public <T extends DataObject> T getDataObject(@NotNull Class<T> clazz) {
        return DataObjectProcessor.createDataInstance(clazz, section);
    }

    @Override
    public <T extends DataObject> T getDataObject(@NotNull String path, @NotNull Class<T> clazz) {
        return DataObjectProcessor.createDataInstance(clazz, section.getSection(path));
    }

    @Override
    public <T> T getAnnotatedObject(@NotNull Class<T> clazz) {
        return DataAnnotationProcessor.createDataInstance(clazz, section);
    }

}
