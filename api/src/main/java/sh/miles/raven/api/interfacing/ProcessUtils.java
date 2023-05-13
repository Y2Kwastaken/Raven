package sh.miles.raven.api.interfacing;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import sh.miles.raven.api.interfacing.annotation.DataSection;

/**
 * Utility class for processing classes.
 */
public final class ProcessUtils {

    private ProcessUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Creates a new instance of the given {@link Class}.
     * 
     * @param <T>   The type of the {@link Class}.
     * @param clazz The {@link Class} to create an instance of.
     * @return A new instance of the given {@link Class}.
     */
    public static <T> T createInstance(@NotNull final Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");

        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // silently fail
            return null;
        }
    }

    /**
     * Gets all fields of the given {@link Class} that are of the given type.
     * 
     * @param clazz The {@link Class} to get the fields from.
     * @param type  The type of the fields to get.
     * 
     * @return An array of {@link Field}s that are of the given type.
     */
    public static Field[] getFieldsOfType(Class<?> clazz, Class<?> type) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getType().equals(type))
                .toArray(Field[]::new);
    }

    /**
     * Gets the value of the given {@link Field} from the given instance.
     * 
     * @param field    the {@link Field} to get the value of
     * @param instance the instance to get the value from
     * @return the value of the given {@link Field} from the given instance
     */
    public static Object getFieldValue(Field field, Object instance) {
        try {
            field.setAccessible(true);
            Object obj = field.get(instance);
            field.setAccessible(false);
            return obj;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // silently fail
            return null;
        }
    }

    /**
     * Checks if the given {@link Class} has a {@link DataSection} annotation.
     * 
     * @param clazz          The {@link Class} to check.
     * @param interfaceClass The {@link Class} of the interface to check for.
     * @return {@code true} if the given {@link Class} has the given interface,
     *         {@code false} otherwise.
     */
    public static boolean classHasInterface(Class<?> clazz, Class<?> interfaceClass) {
        return Arrays.stream(clazz.getInterfaces()).anyMatch(interfaceClass::equals);
    }

}
