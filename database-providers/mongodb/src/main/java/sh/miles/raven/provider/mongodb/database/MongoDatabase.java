package sh.miles.raven.provider.mongodb.database;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseSection;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MongoDatabase implements Database {

    private com.mongodb.client.MongoDatabase database;

    public MongoDatabase(final com.mongodb.client.MongoDatabase database) {
        this.database = database;
    }

    @NotNull
    @Override
    public DatabaseSection getSection(@NotNull String collection, @NotNull String id) {
        Preconditions.checkNotNull(collection, "section cannot be null");
        Preconditions.checkNotNull(id, "id cannot be null");

        final MongoCollection<Document> mongoCollection = database.getCollection(collection);
        Preconditions.checkNotNull(mongoCollection, "collection cannot be null");

        return new MongoDatabaseSection(mongoCollection, id);
    }

    @NotNull
    @Override
    public List<String> getAllSections(@NotNull String collection) {
        Preconditions.checkNotNull(collection, "collection can not be null");

        final MongoCollection<Document> mongoCollection = database.getCollection(collection);
        final List<String> sections = new ArrayList<>();
        try (final MongoCursor<Document> sectionIterator = mongoCollection.find().iterator()) {
            Document section;
            while (sectionIterator.hasNext()) {
                section = sectionIterator.next();
                sections.add(section.getString("_id"));
            }
        }
        return sections;
    }

    @Override
    public @NotNull DatabaseSection createSection(@NotNull String collection, @NotNull String id) {
        Preconditions.checkNotNull(collection, "section cannot be null");
        Preconditions.checkNotNull(id, "id cannot be null");

        // Creates an empty mongo document
        final Document document = new Document();
        document.put("_id", id);

        // Inserts the document into the collection
        final MongoCollection<Document> mongoCollection = database.getCollection(collection);
        Preconditions.checkNotNull(mongoCollection, "collection cannot be null");

        try {
            mongoCollection.insertOne(document);
            return new MongoDatabaseSection(mongoCollection, id);
        } catch (final MongoException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean hasSection(@NotNull String collection, @NotNull String id) {
        Preconditions.checkNotNull(collection, "section cannot be null");
        Preconditions.checkNotNull(id, "id cannot be null");

        // checks if document exists
        final MongoCollection<Document> mongoCollection = database.getCollection(collection);
        Preconditions.checkNotNull(mongoCollection, "collection cannot be null");

        return mongoCollection.find(new Document("_id", id)).first() != null;
    }

    @Override
    public boolean deleteSection(@NotNull String collection, @NotNull String id) {
        Preconditions.checkNotNull(collection, "section cannot be null");
        Preconditions.checkNotNull(id, "id cannot be null");

        // delete document from collection
        final MongoCollection<Document> mongoCollection = database.getCollection(collection);
        Preconditions.checkNotNull(mongoCollection, "collection cannot be null");

        try {
            return mongoCollection.deleteOne(new Document("_id", id)).getDeletedCount() > 0;
        } catch (final MongoException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean createCollection(@NotNull String collection) {
        Preconditions.checkNotNull(collection, "collection cannot be null");

        // creates a new collection
        try {
            database.createCollection(collection);
            return true;
        } catch (final MongoException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean hasCollection(@NotNull String collection) {
        Preconditions.checkNotNull(collection, "collection cannot be null");

        // checks if collection exists
        return database.listCollectionNames().into(new java.util.ArrayList<>()).contains(collection);
    }

    @Override
    public boolean deleteCollection(@NotNull String collection) {
        Preconditions.checkNotNull(collection, "collection cannot be null");

        // deletes collection
        try {
            database.getCollection(collection).drop();
            return true;
        } catch (final MongoException e) {
            throw new IllegalStateException(e);
        }
    }

}
