package sh.miles.raven.core.interfacing.processing;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

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

    public static Field[] getFieldsOfType(Class<?> clazz, Class<?> type) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getType().equals(type))
                .toArray(Field[]::new);
    }

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

    public static boolean classHasInterface(Class<?> clazz, Class<?> interfaceClass) {
        return Arrays.stream(clazz.getInterfaces()).anyMatch(interfaceClass::equals);
    }

}
