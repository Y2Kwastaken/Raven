package sh.miles.raven.provider.nitrite.database;

import static org.dizitart.no2.collection.Document.createDocument;
import static org.dizitart.no2.filters.FluentFilter.where;

import java.util.List;
import java.util.logging.Logger;

import org.dizitart.no2.collection.Document;
import org.dizitart.no2.collection.NitriteCollection;
import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.collection.UpdateOptions;
import org.jetbrains.annotations.NotNull;

import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.database.exception.DatabaseInsertionException;

public class NitriteDatabaseSection implements DatabaseSection {

    private final NitriteCollection collection;
    private final String id;
    private String root;

    public NitriteDatabaseSection(NitriteCollection collection, String id) {
        this.collection = collection;
        this.id = id;
        this.root = "";
    }

    public NitriteDatabaseSection(NitriteCollection collection, String id, String root) {
        this.collection = collection;
        this.id = id;
        this.root = root + ".";
    }

    @Override
    public String getString(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getString'");
    }

    @Override
    public int getInt(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInt'");
    }

    @Override
    public long getLong(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLong'");
    }

    @Override
    public double getDouble(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDouble'");
    }

    @Override
    public boolean getBoolean(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoolean'");
    }

    @Override
    public byte getByte(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByte'");
    }

    @Override
    public byte[] getBytes(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytes'");
    }

    @Override
    public Object get(@NotNull String key) {
        final Document document = collection.getById(NitriteId.createId(id));
        Logger.getGlobal().info(document.toString());

        return document.get(key);
    }

    @Override
    public DatabaseSection getSection(@NotNull String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSection'");
    }

    @Override
    public List<String> getStringList(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStringList'");
    }

    @Override
    public List<Integer> getIntegerList(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIntegerList'");
    }

    @Override
    public List<Long> getLongList(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLongList'");
    }

    @Override
    public List<Double> getDoubleList(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDoubleList'");
    }

    @Override
    public List<Boolean> getBooleanList(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooleanList'");
    }

    @Override
    public List<Byte> getByteList(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByteList'");
    }

    @Override
    public List<byte[]> getBytesList(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytesList'");
    }

    @Override
    public List<Object> getList(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getList'");
    }

    @Override
    public void set(@NotNull String key, @NotNull Object value) throws DatabaseInsertionException {
        collection.update(
                where("_id").eq(id),
                createDocument("$set",
                        createDocument(key, value)),
                UpdateOptions.updateOptions(true));
    }

    @Override
    public void remove(@NotNull String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

}
