package sh.miles.raven.api.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Short list of reflection utilities.
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Creates an instance of the given class given it has an empty constructor.
     * 
     * @param <T>   The type of the class.
     * 
     * @param clazz The class to create an instance of.
     * @return The created instance.
     */
    public static <T> T createInstance(Class<T> clazz) {
        try {
            final Constructor<T> constructor = clazz.getDeclaredConstructor(new Class<?>[0]);
            constructor.setAccessible(true);
            T instance = constructor.newInstance(new Object[0]);
            constructor.setAccessible(false);
            return instance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
