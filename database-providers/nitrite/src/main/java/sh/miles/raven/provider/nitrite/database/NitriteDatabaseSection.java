package sh.miles.raven.provider.nitrite.database;

import static org.dizitart.no2.filters.FluentFilter.where;
import static sh.miles.raven.provider.nitrite.database.NitriteDatabase.ID_INDEX;

import java.util.List;

import javax.annotation.Nullable;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.collection.Document;
import org.dizitart.no2.collection.NitriteCollection;
import org.dizitart.no2.collection.UpdateOptions;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.database.exception.DatabaseInsertionException;
import sh.miles.raven.api.utils.DotPathUtils;
import sh.miles.raven.provider.nitrite.database.utils.NitriteUtils;

@SuppressWarnings("unchecked")
public class NitriteDatabaseSection implements DatabaseSection {

    private final Nitrite database;
    private final NitriteCollection collection;
    private final String id;
    private String root;

    public NitriteDatabaseSection(Nitrite database, NitriteCollection collection, String id) {
        this.database = database;
        this.collection = collection;
        this.id = id;
        this.root = "";
    }

    public NitriteDatabaseSection(Nitrite database, NitriteCollection collection, String id, String root) {
        this.database = database;
        this.collection = collection;
        this.id = id;
        this.root = root + ".";
    }

    @Override
    public String getString(@NotNull String key) {
        return (String) get(key);
    }

    @Override
    public int getInt(@NotNull String key) {
        return getWrapperValue(key, Integer.class);
    }

    @Override
    public long getLong(@NotNull String key) {
        return getWrapperValue(key, Long.class);
    }

    @Override
    public double getDouble(@NotNull String key) {
        return getWrapperValue(key, Double.class);
    }

    @Override
    public boolean getBoolean(@NotNull String key) {
        return getWrapperValue(key, Boolean.class);
    }

    @Override
    public byte getByte(@NotNull String key) {
        return getWrapperValue(key, Byte.class);
    }

    @Override
    public byte[] getBytes(@NotNull String key) {
        return (byte[]) get(key);
    }

    @Override
    public Object get(@NotNull String key) {
        Preconditions.checkNotNull(key, "key must not be null");
        key = root + key;

        final Document document = collection.find(where(ID_INDEX).eq(id)).firstOrNull();
        if (document == null) {
            return null;
        }

        if (DotPathUtils.isDotPath(key)) {
            return getDocumentValue(root, key, document);
        }

        return document.get(key);
    }

    private <T> T getWrapperValue(@NotNull String key, @NotNull Class<T> clazz) {
        Preconditions.checkNotNull(key, "key must not be null");
        Preconditions.checkNotNull(clazz, "clazz must not be null");

        final T value = (T) get(key);
        if (value == null) {
            try {
                return (T) clazz.getField("MIN_VALUE").get(null);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }

        return (T) get(key);
    }

    @Override
    public DatabaseSection getSection(@NotNull String name) {
        return new NitriteDatabaseSection(database, collection, id, root + name);
    }

    @Override
    public List<String> getStringList(@NotNull String key) {
        return (List<String>) get(key);
    }

    @Override
    public List<Integer> getIntegerList(@NotNull String key) {
        return (List<Integer>) get(key);
    }

    @Override
    public List<Long> getLongList(@NotNull String key) {
        return (List<Long>) get(key);
    }

    @Override
    public List<Double> getDoubleList(@NotNull String key) {
        return (List<Double>) get(key);
    }

    @Override
    public List<Boolean> getBooleanList(@NotNull String key) {
        return (List<Boolean>) get(key);
    }

    @Override
    public List<Byte> getByteList(@NotNull String key) {
        return (List<Byte>) get(key);
    }

    @Override
    public List<byte[]> getBytesList(@NotNull String key) {
        return (List<byte[]>) get(key);
    }

    @Override
    public List<Object> getList(@NotNull String key) {
        return (List<Object>) get(key);
    }

    @Override
    public void set(@NotNull String key, @Nullable Object value) throws DatabaseInsertionException {

        final Document document = collection.find(where(ID_INDEX).eq(id)).firstOrNull().clone();

        if (document == null) {
            throw new DatabaseInsertionException("Document is null");
        }

        setDocumentValue(document, root, key, value);

        collection.remove(where(ID_INDEX).eq(id));
        NitriteUtils.andCommit(
                () -> collection.update(where(ID_INDEX).eq(id), document, UpdateOptions.updateOptions(true)),
                database);
    }

    @Override
    @SuppressWarnings("java:S2259") // compiler bug no it won't
    public void remove(@NotNull String key) {
        final Document document = collection.find(where(ID_INDEX).eq(id)).firstOrNull().clone();
        if (document == null) {
            throw new IllegalArgumentException("document must not be null");
        }

        removeDocumentValue(null, document, root, key);

        collection.remove(where(ID_INDEX).eq(id));
        NitriteUtils.andCommit(
                () -> collection.update(where(ID_INDEX).eq(id), document, UpdateOptions.updateOptions(true)), database);

    }

    private static final void removeDocumentValue(Document parent, Document document, String root, String dotPath) {
        if (!(DotPathUtils.isDotPath(dotPath))) {
            document.remove(dotPath);
            if (document.size() == 0) {
                parent.remove(root);
            }
            return;
        }

        root = DotPathUtils.getRoot(dotPath);
        dotPath = DotPathUtils.removeRoot(dotPath);
        final Document newDocument = document.get(root, Document.class);
        if (newDocument == null) {
            return;
        }
        parent = document;
        document = document.get(root, Document.class);
        removeDocumentValue(parent, document, root, dotPath);
    }

    private static final Object getDocumentValue(String root, String dotPath, Document document) {
        if (!(DotPathUtils.isDotPath(dotPath))) {
            return document.get(dotPath);
        }

        root = DotPathUtils.getRoot(dotPath);
        dotPath = DotPathUtils.removeRoot(dotPath);
        final Object value = document.get(root);
        if (!(value instanceof Document)) {
            return value;
        }
        return getDocumentValue(root, dotPath, (Document) value);
    }

    private static final void setDocumentValue(Document document, String root, String dotPath, Object value) {
        if (!(DotPathUtils.isDotPath(dotPath))) {
            document.put(dotPath, value);
            return;
        }

        root = DotPathUtils.getRoot(dotPath);
        dotPath = DotPathUtils.removeRoot(dotPath);
        final Document newDocument = document.get(root, Document.class);
        if (newDocument == null) {
            document.put(root, Document.createDocument());
        }
        document = document.get(root, Document.class);
        setDocumentValue(document, root, dotPath, value);
    }

}
