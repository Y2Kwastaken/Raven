package sh.miles.raven.provider.mongodb.database;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.database.ObjectDatabaseSection;
import sh.miles.raven.api.database.exception.DatabaseInsertionException;
import sh.miles.raven.api.interfacing.annotation.process.DataAnnotationProcessor;
import sh.miles.raven.api.interfacing.object.DataField;
import sh.miles.raven.api.interfacing.object.DataObject;
import sh.miles.raven.api.interfacing.object.process.DataObjectProcessor;

public class MongoObjectDatabaseSection implements ObjectDatabaseSection {

    private DatabaseSection section;

    /**
     * MongoObjectDatabaseSection creates a new instance of MongoDatabaseSection
     * 
     * @param database the database to use
     */
    public MongoObjectDatabaseSection(DatabaseSection section) {
        this.section = section;
    }

    @Override
    public <T extends DataObject> T getDataObject(@NotNull Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");

        return DataObjectProcessor.createDataInstance(clazz, section);
    }

    @Override
    public <T extends DataObject> T getDataObject(@NotNull String path, @NotNull Class<T> clazz) {
        Preconditions.checkNotNull(path, "The given path cannot be null.");
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");

        return DataObjectProcessor.createDataInstance(clazz, section.getSection(path));
    }

    @Override
    public <T> T getAnnotatedObject(@NotNull Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");

        return DataAnnotationProcessor.createDataInstance(clazz, section);
    }

    @Override
    public void setDataObject(@NotNull DataObject object) throws DatabaseInsertionException {
        Preconditions.checkNotNull(object, "The given object cannot be null.");

        final DataField<Object>[] fields = DataObjectProcessor.getDataFieldsFromFields(object);
        for (final DataField<Object> field : fields) {
            section.set(field.getKey(), field.getValue());
        }
    }

}
