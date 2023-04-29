package sh.miles.raven.provider.mongodb.database;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;
import com.mongodb.client.MongoCollection;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.api.database.DatabaseSection;

public class MongoDatabase implements Database {

    private com.mongodb.client.MongoDatabase database;

    public MongoDatabase(final com.mongodb.client.MongoDatabase database) {
        this.database = database;
    }

    @Override
    public DatabaseSection getSection(@NotNull String section) {
        Preconditions.checkNotNull(section, "section cannot be null");

        final MongoCollection<Document> collection = database.getCollection(section);
        Preconditions.checkNotNull(collection, "collection cannot be null");
        
        return new MongoDatabaseSection(collection);
    }

}
