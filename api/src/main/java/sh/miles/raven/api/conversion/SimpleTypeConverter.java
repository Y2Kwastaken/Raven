package sh.miles.raven.api.conversion;

import java.util.function.Function;

/**
 * This class is a simple implementation of {@link TypeConverter} that takes in
 * multiple functions
 * 
 * @param <O> the original type
 * @param <C> the converted type
 */
public final class SimpleTypeConverter<O, C> implements TypeConverter<O, C> {

    private final Class<O> originalType;
    private final Class<C> convertedType;
    private final Function<O, C> convertFunction;
    private final Function<C, O> revertFunction;

    /**
     * Creates a new instance of {@link SimpleTypeConverter}
     * 
     * @param originalType    the original type
     * @param convertedType   the converted type
     * @param convertFunction the function to convert the original type to the
     *                        converted type
     * @param revertFunction  the function to convert the converted type to the
     *                        original type
     */
    private SimpleTypeConverter(final Class<O> originalType, final Class<C> convertedType,
            final Function<O, C> convertFunction, final Function<C, O> revertFunction) {
        this.originalType = originalType;
        this.convertedType = convertedType;
        this.convertFunction = convertFunction;
        this.revertFunction = revertFunction;
    }

    @Override
    public C convert(O original) {
        return convertFunction.apply(original);
    }

    @Override
    public O revert(C converted) {
        return revertFunction.apply(converted);
    }

    @Override
    public Class<O> getOriginalType() {
        return originalType;
    }

    @Override
    public Class<C> getConvertedType() {
        return convertedType;
    }

    /**
     * Creates a new instance of {@link SimpleTypeConverter}
     * 
     * @param <O>             the original type
     * @param <C>             the converted type
     * @param originalType    the original type
     * @param convertedType   the converted type
     * @param convertFunction the function to convert the original type to the
     *                        converted type
     * @param revertFunction  the function to convert the converted type to the
     *                        original type
     * @return a new instance of {@link SimpleTypeConverter}
     */
    public static <O, C> SimpleTypeConverter<O, C> of(final Class<O> originalType,
            final Class<C> convertedType, final Function<O, C> convertFunction,
            final Function<C, O> revertFunction) {
        return new SimpleTypeConverter<>(originalType, convertedType, convertFunction,
                revertFunction);
    }

}
