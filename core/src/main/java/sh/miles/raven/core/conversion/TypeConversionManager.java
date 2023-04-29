package sh.miles.raven.core.conversion;

import java.util.HashMap;
import java.util.Map;

import sh.miles.raven.api.conversion.TypeConverter;

/**
 * Manages the type conversion process for the Raven API
 */
@SuppressWarnings("unchecked")
public final class TypeConversionManager {

    public static final String ERROR_MESSAGE = "No type converter found for %s to %s";
    public static final String ALREADY_REGISTERED = "Type converter already registered for %s";

    private static TypeConversionManager instance;

    private final Map<Class<?>, TypeConverter<?, ?>> converters;

    /**
     * Creates a new instance of {@link TypeConversionManager}
     */
    private TypeConversionManager() {
        this.converters = new HashMap<>();
    }

    /**
     * Registers a new {@link TypeConverter} with the manager
     * 
     * @param converter the {@link TypeConverter} to register
     */
    public void register(TypeConverter<?, ?> converter) {
        final TypeConverter<?, ?> old = converters.putIfAbsent(converter.getOriginalType(), converter);
        if (old != null) {
            throw new IllegalArgumentException(String.format(ALREADY_REGISTERED, converter.getOriginalType()));
        }
    }

    public <O, C> C convert(O original, Class<C> converted) {
        final TypeConverter<O, C> converter = getConverter((Class<O>) original.getClass(), converted);
        if (converter == null) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE, original.getClass(), converted));
        }
        return converter.convert(original);
    }

    public <O, C> O revert(C converted, Class<O> original) {
        final TypeConverter<O, C> converter = getConverter(original, (Class<C>) converted.getClass());
        if (converter == null) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE, converted.getClass(), original));
        }
        return converter.revert(converted);
    }

    public <O, C> TypeConverter<O, C> getConverter(Class<O> original, Class<C> converted) {
        final TypeConverter<?, ?> converter = converters.get(original);
        if (converter == null) {
            return null;
        }
        if (converter.getConvertedType() != converted) {
            return null;
        }
        return (TypeConverter<O, C>) converter;
    }

    /**
     * Removes a {@link TypeConverter} from the manager
     * 
     * @param converter the {@link TypeConverter} to remove
     * @deprecated This method is not encouraged to be used. It is here for more
     *             full control over the conversion process. Before using this
     *             method think if you really NEED to use it. If you do, please
     *             exercise caution. This method may break internal logic of the API
     *             if implemented improperly.
     */
    @Deprecated
    public void unregister(TypeConverter<?, ?> converter) {
        converters.remove(converter.getOriginalType());
    }

    /**
     * Checks if the manager has a {@link TypeConverter} registered for the given
     * type. When registered, the manager uses the Original Type of the object as a
     * key so keep that in mind if you come across any unexpected behavior.
     * 
     * @param type the type to check
     * @return true if the manager has a {@link TypeConverter} registered for the
     */
    public boolean isRegistered(Class<?> type) {
        return converters.containsKey(type);
    }

    /**
     * Returns the instance of {@link TypeConversionManager}
     * 
     * @return the instance of {@link TypeConversionManager}
     */
    public static TypeConversionManager getInstance() {
        if (instance == null) {
            instance = new TypeConversionManager();
        }
        return instance;
    }

}
