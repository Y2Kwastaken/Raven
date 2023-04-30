package sh.miles.raven.provider.mongodb.database;

import java.util.List;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;
import com.mongodb.client.MongoCollection;

import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.database.exception.DatabaseInsertionException;
import sh.miles.raven.api.utils.DotPathUtils;

/**
 * MongoDatabaseSection is the implementation of DatabaseSection for MongoDB
 */
@SuppressWarnings("unchecked")
public class MongoDatabaseSection implements DatabaseSection {

    private final MongoCollection<Document> collection;
    private final String id;
    private final String root;

    /**
     * MongoDatabaseSection creates a new instance of MongoDatabaseSection
     * 
     * @param database the database to use
     */
    public MongoDatabaseSection(final MongoCollection<Document> collection, String id) {
        this.collection = collection;
        this.id = id;
        this.root = "";
    }

    public MongoDatabaseSection(final MongoCollection<Document> collection, final String id, final String root) {
        this.collection = collection;
        this.id = id;
        this.root = root + ".";
    }

    @Override
    public String getString(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (String) get(key);
    }

    @Override
    public int getInt(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (int) get(key);
    }

    @Override
    public long getLong(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (long) get(key);
    }

    @Override
    public double getDouble(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (double) get(key);
    }

    @Override
    public boolean getBoolean(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (boolean) get(key);
    }

    @Override
    public byte getByte(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (byte) getInt(key);
    }

    @Override
    public byte[] getBytes(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (byte[]) get(key);
    }

    @Override
    public Object get(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        final Document document = collection.find(new Document("_id", id)).first();

        if (DotPathUtils.isDotPath(key)) {
            return getNestedValue(document, key);
        }

        return get0(document, key);
    }

    private Object get0(Document document, String key) {

        final Object obj = document.get(key);

        if (root.isEmpty()) {
            return obj;
        }

        return getNestedDocument(document, root).get(key);
    }

    @Override
    public DatabaseSection getSection(String name) {
        Preconditions.checkNotNull(name, "name cannot be null");

        return new MongoDatabaseSection(collection, id, root + name);
    }

    @Override
    public List<String> getStringList(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (List<String>) get(key);
    }

    @Override
    public List<Integer> getIntegerList(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (List<Integer>) get(key);
    }

    @Override
    public List<Long> getLongList(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (List<Long>) get(key);
    }

    @Override
    public List<Double> getDoubleList(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (List<Double>) get(key);
    }

    @Override
    public List<Boolean> getBooleanList(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (List<Boolean>) get(key);
    }

    @Override
    public List<Byte> getByteList(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (List<Byte>) get(key);
    }

    @Override
    public List<byte[]> getBytesList(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (List<byte[]>) get(key);
    }

    @Override
    public List<Object> getList(String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return (List<Object>) get(key);
    }

    @Override
    public void set(String key, Object value) throws DatabaseInsertionException {
        Preconditions.checkNotNull(key, "key cannot be null");
        Preconditions.checkNotNull(value, "value cannot be null");

        collection.updateOne(new Document("_id", id), new Document("$set", new Document(key, value)));
    }

    @Override
    public void remove(@NotNull String key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        // delete value from database associated with key and root
        collection.updateOne(new Document("_id", id), new Document("$unset", new Document(key, "")));
    }

    private static Document getNestedDocument(final Document document, final String dotPath) {
        return document.getEmbedded(DotPathUtils.splitDotPathToList(
                DotPathUtils.removeHangingDot(
                        dotPath)),
                Document.class);

    }

    private static Object getNestedValue(final Document document, final String dotPath) {
        return document.getEmbedded(DotPathUtils.splitDotPathToList(
                DotPathUtils.removeHangingDot(
                        dotPath)),
                Object.class);

    }
}
