package sh.miles.raven.api.database;

import org.jetbrains.annotations.NotNull;

/**
 * Database is the main interface
 */
public interface Database {

    /**
     * Returns a DatabaseSection for the given section
     * 
     * @param section the section to get
     * @return the DatabaseSection
     */
    @NotNull
    DatabaseSection getSection(@NotNull String section);

}
