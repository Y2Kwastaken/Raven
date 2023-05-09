package sh.miles.raven.provider.nitrite.database.utils;

import org.dizitart.no2.Nitrite;

public final class NitriteUtils {

    private NitriteUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void andCommit(final Runnable runnable, final Nitrite database) {
        runnable.run();
        database.commit();
    }

    public static void andClose(final Runnable runnable, final AutoCloseable database) {
        runnable.run();
        try {
            database.close();
        } catch (Exception e) {
            // this will never happen unless O.o
        }
    }
}
