package sh.miles.raven.api.interfacing.object;

/**
 * This interface is used to mark classes that are used to represent data
 * objects.
 */
public interface DataObject {

    /**
     * This method is used to get the name of the section in the database that this
     * object is stored in.
     * 
     * @return The name of the section in the database that this object is stored
     */
    String getDatabaseSectionName();
}
