package sh.miles.raven.provider.nitrite.database;

import java.util.Arrays;
import java.util.List;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.collection.NitriteCollection;

import sh.miles.raven.provider.nitrite.database.connection.NitriteConnectionManager;
import sh.miles.raven.provider.nitrite.database.connection.NitriteConnectionString;

public class NitriteVisualizer {

    public static final String DATABASE_NAME = "nitrite";

    private static final List<String> filtered = Arrays.asList("_id", "_revision", "_modified");

    public static void main(){
        main(new String[]{});
    }

    public static void main(String[] args) {
        final NitriteConnectionString connectionString = new NitriteConnectionString(
                System.getenv("NITRITE_CONNECTION"));
        final NitriteConnectionManager connectionManager = new NitriteConnectionManager(connectionString);
        final var database = connectionManager.getDatabase(DATABASE_NAME);
        visualizeDatabase(database);
    }

    public static void visualizeDatabase(Nitrite database) {
        database.getStore().getCollectionNames().forEach(collectionName -> {
            System.out.println("Collection: " + collectionName);
            visualizeCollection(database.getCollection(collectionName));
            System.out.println("==========================================");
        });
    }

    public static void visualizeCollection(NitriteCollection collection) {
        collection.find().forEach(document -> {
            System.out.println("\tDocument Id: " + document.getId());
            System.out.println("\tRevision: " + document.getRevision());
            System.out.println("\tFields: ");
            document.forEach(pair -> {
                if (!filtered.contains(pair.getFirst())) {
                    System.out.println("\t\t" + pair.getFirst() + ": " + pair.getSecond());
                }
            });
        });
    }

}
