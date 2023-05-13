package sh.miles.raven.api.interfacing.annotation.process;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import sh.miles.raven.api.conversion.TypeConversionManager;
import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.api.interfacing.ProcessUtils;
import sh.miles.raven.api.interfacing.annotation.DataSection;
import sh.miles.raven.api.interfacing.annotation.Key;
import sh.miles.raven.api.interfacing.annotation.NestedObject;

/**
 * Class for processing {@link DataSection} annotations and annotation related
 * objects.
 */
public final class DataAnnotationProcessor {

    private static final TypeConversionManager typeConversionManager = TypeConversionManager.getInstance();

    private DataAnnotationProcessor() {
        throw new AssertionError("This class should not be instantiated.");
    }

    /**
     * Creates a new instance of a given {@link Class} and adds it the given data
     * from the given {@link DatabaseSection}. This class must have a annotation of
     * {@link DataSection}. and all related fields must be annotated with
     * {@link Key} and be of a type that is supported by the database.
     * 
     * @param clazz         The {@link Class} to create an instance of.
     * @param parentSection The {@link DatabaseSection} to get the data from.
     * 
     * @return A new instance of the given {@link Class} with the data from the
     *         given {@link DatabaseSection}.
     */
    public static <T> T createDataInstance(@NotNull final Class<T> clazz,
            @NotNull final DatabaseSection parentSection) {
        if (!(isClassDataSection(clazz))) {
            throw new IllegalArgumentException(
                    "The given class is not a DataSection and it must be. Annotate it with @DataSection.");
        }

        DataSection dataSection = processDataSection(clazz);

        return fillObjectValues(clazz, dataSection.name(), parentSection);
    }

    private static <T> T fillObjectValues(@NotNull Class<T> clazz, @NotNull String key,
            @NotNull DatabaseSection parentSection) {
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");
        Preconditions.checkNotNull(key, "The given key cannot be null.");
        Preconditions.checkNotNull(parentSection, "The given section cannot be null.");

        DatabaseSection section = parentSection.getSection(key);
        if (section == null) {
            throw new IllegalArgumentException("The given section does not have a section with the key " + key);
        }

        T instance = ProcessUtils.createInstance(clazz);
        if (instance == null) {
            throw new IllegalArgumentException("The given class does not have a default constructor.");
        }

        Field[] fields = getKeyFields(clazz);
        fillFieldsValues(instance, fields, section);

        return instance;
    }

    private static <T> void fillFieldsValues(T instance, Field[] fields, DatabaseSection section) {
        for (Field field : fields) {
            String key = processKey(field);
            final Object value = section.get(key);
            if (value == null) {
                throw new IllegalArgumentException(
                        "The given DatabaseSection does not have a value with the key " + key);
            }

            final Class<?> type = field.getType();
            if (typeConversionManager.isRegistered(type)) {
                setFieldValue(instance, field, typeConversionManager.revert(value, type));
            } else if (field.isAnnotationPresent(NestedObject.class)) {
                setFieldValue(instance, field, fillObjectValues(type, key, section));
            } else if (type.isPrimitive()) {
                setFieldValue(instance, field, value);
            } else {
                // Casting is last resort
                setFieldValue(instance, field, type.cast(value));
            }
        }
    }

    /**
     * Returns all {@link Field}s of the given {@link Class} that are annotated with
     * a {@link Key} annotation.
     * 
     * @param clazz The {@link Class} to process.
     * @return All {@link Field}s of the given {@link Class} that are annotated with
     */
    public static Field[] getKeyFields(@NotNull final Class<?> clazz) {
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");

        Field[] fields = clazz.getDeclaredFields();

        return Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Key.class)).toArray(Field[]::new);
    }

    /**
     * Returns true if the given {@link Class} is a data section, else false.
     * 
     * @param clazz The {@link Class} to check.
     * @return True if the given {@link Class} is a data section, else false.
     */
    public static boolean isClassDataSection(@NotNull final Class<?> clazz) {
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");

        return clazz.isAnnotationPresent(DataSection.class);
    }

    /**
     * Returns the {@link DataSection} annotation of the given {@link Class}.
     * 
     * @param clazz The {@link Class} to process.
     * @return The {@link DataSection} annotation of the given {@link Class}.
     */
    public static DataSection processDataSection(@NotNull final Class<?> clazz) {
        Preconditions.checkNotNull(clazz, "The given class cannot be null.");

        if (!(clazz.isAnnotationPresent(DataSection.class))) {
            throw new IllegalArgumentException("The given class does not have a DataSection annotation.");
        }

        return clazz.getAnnotation(DataSection.class);
    }

    /**
     * Returns a key associated with the given {@link AnnotatedElement}.
     * The key is generated from the {@link Key} annotation on the given
     * 
     * @param element The {@link AnnotatedElement} to process.
     * @return The key associated with the given {@link AnnotatedElement}. else
     *         throws an {@link IllegalArgumentException}.
     */
    public static String processKey(@NotNull final AnnotatedElement element) {
        Preconditions.checkNotNull(element, "The given AnnotatedElement cannot be null.");

        if (!(element.isAnnotationPresent(Key.class))) {
            throw new IllegalArgumentException("The given AnnotatedElement does not have a Key annotation.");
        }

        Key key = element.getAnnotation(Key.class);
        return key.name();
    }

    /**
     * Attempts to set the given {@link Field} of the given {@link Object} to the
     * given {@link Object}.
     * 
     * @param object The {@link Object} to set the {@link Field} of.
     * @param field  The {@link Field} to set.
     * @param value  The {@link Object} to set the {@link Field} to.
     * 
     * @return True if the {@link Field} was set successfully, else false.
     */
    public static boolean setFieldValue(@NotNull final Object object, @NotNull final Field field,
            @NotNull final Object value) {
        Preconditions.checkNotNull(object, "The given object cannot be null.");
        Preconditions.checkNotNull(field, "The given field cannot be null.");
        Preconditions.checkNotNull(value, "The given value cannot be null.");

        try {
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // silently fail
            return false;
        }
        return true;
    }
}
