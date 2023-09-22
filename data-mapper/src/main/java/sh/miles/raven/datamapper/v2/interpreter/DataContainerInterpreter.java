package sh.miles.raven.datamapper.v2.interpreter;

import org.jetbrains.annotations.NotNull;
import sh.miles.raven.api.database.DatabaseSection;
import sh.miles.raven.datamapper.v2.DataContainer;
import sh.miles.raven.datamapper.v2.data.DataField;
import sh.miles.raven.datamapper.v2.data.DataTuple;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataContainerInterpreter {

    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static final Map<Class<? extends DataContainer>, List<MethodHandle>> varHandles = new HashMap<>();
    private static final Map<Class<? extends DataContainer>, MethodHandle> constructors = new HashMap<>();

    @SuppressWarnings("all")
    public static <T extends DataContainer> T getContainerFromSection(@NotNull final DatabaseSection section, Class<T> containerClass) {
        if (!varHandles.containsKey(containerClass)) {
            varHandles.put(containerClass, initializeDataFieldHandles(containerClass));
            constructors.put(containerClass, initializeConstructor(containerClass));
        }

        try {
            T instance = (T) constructors.get(containerClass).invoke();
            final List<MethodHandle> variables = varHandles.get(containerClass);
            for (MethodHandle variable : variables) {
                DataField field = (DataField) variable.bindTo(instance).invoke();
                field.setValue(section.get(field.getKey()));
                if (field instanceof DataTuple tuple) {
                    tuple.lock();
                }
            }
            return instance;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void setContainerToSection(@NotNull final DatabaseSection section, DataContainer container) {
        if (!varHandles.containsKey(container.getClass())) {
            varHandles.put(container.getClass(), initializeDataFieldHandles(container.getClass()));
            constructors.put(container.getClass(), initializeConstructor(container.getClass()));
        }

        final List<MethodHandle> variables = varHandles.get(container.getClass());
        try {
            for (MethodHandle variable : variables) {
                final DataField<?> field = (DataField<?>) variable.bindTo(container).invoke();
                section.set(field.getKey(), field.getValue());
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeContainerFromSection(@NotNull final DatabaseSection section, DataContainer container) {
        if (!varHandles.containsKey(container.getClass())) {
            varHandles.put(container.getClass(), initializeDataFieldHandles(container.getClass()));
            constructors.put(container.getClass(), initializeConstructor(container.getClass()));
        }

        final List<MethodHandle> variables = varHandles.get(container.getClass());
        try {
            for (MethodHandle variable : variables) {
                final DataField<?> field = (DataField<?>) variable.bindTo(container).invoke();
                section.remove(field.getKey());
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static List<MethodHandle> initializeDataFieldHandles(@NotNull final Class<?> clazz) {
        final List<MethodHandle> handles = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (DataField.class.isAssignableFrom(declaredField.getType())) {
                try {
                    declaredField.setAccessible(true);
                    handles.add(lookup.unreflectGetter(declaredField));
                    declaredField.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return handles;
    }

    private static MethodHandle initializeConstructor(@NotNull final Class<?> clazz) {
        try {
            final Constructor<?> constructor = clazz.getConstructor();
            return lookup.unreflectConstructor(constructor);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
