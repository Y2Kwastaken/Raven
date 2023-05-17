package sh.miles.raven.provider.nitrite.database;

import static org.dizitart.no2.filters.FluentFilter.where;

import java.util.Collection;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.collection.Document;
import org.dizitart.no2.collection.NitriteCollection;
import org.dizitart.no2.index.IndexDescriptor;
import org.dizitart.no2.index.IndexOptions;
import org.dizitart.no2.index.IndexType;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.provider.nitrite.database.utils.NitriteUtils;

public class NitriteDatabase implements Database {

    public static final String ID_INDEX = "doc_id";

    private final Nitrite database;

    public NitriteDatabase(@NotNull Nitrite database) {
        this.database = database;
    }

    @Override
    public @NotNull DatabaseSection getSection(@NotNull String collection, @NotNull String id) {
        Preconditions.checkNotNull(collection, "section cannot be null");
        Preconditions.checkNotNull(id, "id cannot be null");

        final NitriteCollection nitriteCollection = database.getCollection(collection);
        Preconditions.checkNotNull(nitriteCollection, "collection cannot be null");

        return new NitriteDatabaseSection(database, nitriteCollection, id);
    }

    @Override
    public @NotNull DatabaseSection createSection(@NotNull String collection, @NotNull String id) {
        Preconditions.checkNotNull(collection, "section cannot be null");
        Preconditions.checkNotNull(id, "id cannot be null");

        final NitriteCollection nitriteCollection = database.getCollection(collection);
        Preconditions.checkNotNull(nitriteCollection, "collection cannot be null");

        if (!isIndex(ID_INDEX, nitriteCollection.listIndices())) {
            nitriteCollection.createIndex(IndexOptions.indexOptions(IndexType.UNIQUE), ID_INDEX);
        }

        NitriteUtils.andCommit(() -> {
            final Document element = Document.createDocument(ID_INDEX, id);
            nitriteCollection.update(element, true);
        }, database);

        return new NitriteDatabaseSection(database, nitriteCollection, id);
    }

    @Override
    public boolean hasSection(@NotNull String collection, @NotNull String id) {
        Preconditions.checkNotNull(collection, "section cannot be null");
        Preconditions.checkNotNull(id, "id cannot be null");

        final NitriteCollection nitriteCollection = database.getCollection(collection);
        Preconditions.checkNotNull(nitriteCollection, "collection cannot be null");

        return nitriteCollection.find(where(ID_INDEX).eq(id)).size() > 0;
    }

    @Override
    public boolean deleteSection(@NotNull String collection, @NotNull String id) {
        Preconditions.checkNotNull(collection, "section cannot be null");
        Preconditions.checkNotNull(id, "id cannot be null");

        final NitriteCollection nitriteCollection = database.getCollection(collection);
        Preconditions.checkNotNull(nitriteCollection, "collection cannot be null");

        return NitriteUtils.andCommit(() -> nitriteCollection.remove(where(ID_INDEX).eq(id)).getAffectedCount() > 0,
                database);
    }

    @Override
    public boolean createCollection(@NotNull String collection) {
        Preconditions.checkNotNull(collection, "collection cannot be null");
        Preconditions.checkArgument(!database.hasCollection(collection), "collection already exists");

        NitriteUtils.andCommit(() -> database.getCollection(collection), database);
        return true; // always true
    }

    @Override
    public boolean hasCollection(@NotNull String collection) {
        Preconditions.checkNotNull(collection, "collection cannot be null");

        return database.hasCollection(collection);
    }

    @Override
    public boolean deleteCollection(@NotNull String collection) {
        Preconditions.checkNotNull(collection, "collection cannot be null");

        NitriteUtils.andCommit(() -> database.getCollection(collection).drop(), database);
        return true; // could throw exception
    }

    private static boolean isIndex(final String field, final Collection<IndexDescriptor> entries) {
        return entries.stream().anyMatch(entry -> entry.getIndexFields().getFieldNames().contains(field));
    }
}
