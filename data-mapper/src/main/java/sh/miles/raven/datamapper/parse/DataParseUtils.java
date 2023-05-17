package sh.miles.raven.datamapper.parse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import sh.miles.raven.datamapper.api.DataContainer;
import sh.miles.raven.datamapper.api.fields.DataField;
import sh.miles.raven.datamapper.api.fields.DataTuple;

public final class DataParseUtils {

    private DataParseUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Sets a field of an instance to a value
     * 
     * @param instance  the instance to set the field of
     * @param fieldName the name of the field to set
     * @param value     the value to set the field to
     * @return true if the field was set successfully, false otherwise
     */
    public static boolean setField(Object instance, String fieldName, Object value) {
        try {
            final Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
            field.setAccessible(false);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the value of a field of an instance
     * 
     * @param instance  the instance to get the field of
     * @param fieldName the name of the field to get
     * @return the value of the field
     */
    public static Field getField(Object instance, String fieldName) {
        try {
            final Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getFieldValue(Object instance, String fieldName) {
        try {
            final Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            final Object value = field.get(instance);
            field.setAccessible(false);
            return value;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static DataField<Object>[] getFields(DataContainer container) {
        final Field[] fields = getFieldsOfType(container.getClass(), DataField.class);
        return Arrays.stream(fields).map(field -> (DataField<Object>) getFieldValue(container, field.getName()))
                .toArray(DataField[]::new);
    }

    @SuppressWarnings("unchecked")
    public static DataTuple<Object>[] getTuples(DataContainer container) {
        final Field[] fields = getFieldsOfType(container.getClass(), DataTuple.class);
        return Arrays.stream(fields).map(field -> (DataTuple<Object>) getFieldValue(container, field.getName()))
                .toArray(DataTuple[]::new);
    }

    /**
     * Creates a new instance of a class
     * 
     * @param <T>   the type of the class
     * @param clazz the class to create an instance of
     * @return the new instance of the class
     */
    public static <T> T createInstance(Class<T> clazz) {
        try {
            final Constructor<T> constructor = clazz.getConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            constructor.setAccessible(false);
            return instance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field[] getFieldsOfType(Class<?> clazz, Class<?> type) {
        final Field[] fields = clazz.getDeclaredFields();
        // return array with size of fields of type that has no nulls
        return Arrays.stream(fields).filter(field -> type.isAssignableFrom(field.getType())).toArray(Field[]::new);
    }

    public static <T, I> T runMethod(I instance, String methodName, Class<?>[] args, Object[] values,
            Class<T> returnType) {
        try {
            final Class<?> clazz = instance.getClass();
            final java.lang.reflect.Method method = clazz.getDeclaredMethod(methodName, args);
            method.setAccessible(true);
            final Object result = method.invoke(instance, values);
            method.setAccessible(false);
            return returnType.cast(result);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the declared field no matter of visibility. This method will recurse all
     * classes until it finds the target declaredField it will always expose this
     * field set its value than make it unexposed.
     * 
     * @param instance  the instance to set the field of
     * @param fieldName the name of the field to set
     * @param value     the value to set the field to
     */
    public static void setDeclaredField(Object instance, String fieldName, Object value) {
        // check until we find the fieldName
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            if (!hasField(clazz, fieldName)) {
                clazz = clazz.getSuperclass();
            } else {
                try {
                    final Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(instance, value);
                    field.setAccessible(false);
                } catch (NoSuchFieldException
                        | SecurityException
                        | IllegalArgumentException
                        | IllegalAccessException e) {
                    e.printStackTrace();
                }

                break;
            }
        }
    }

    public static boolean hasField(Class<?> clazz, String fieldName) {
        final Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

}
