package sh.miles.raven.api.conversion;

/**
 * This class is a simple implementation of {@link TypeConverter} that takes in
 * one class parameter. It is used as a sort of "blank template when no
 * conversion is needed"
 * 
 * @param <O> the original type
 */
public class LikeTypeConverter<O> implements TypeConverter<O, O> {

    private final Class<O> originalType;

    /**
     * Creates a new instance of {@link LikeTypeConverter}
     * 
     * @param originalType the original type
     */
    public LikeTypeConverter(Class<O> originalType) {
        this.originalType = originalType;
    }

    @Override
    public O convert(O original) {
        return original;
    }

    @Override
    public O revert(O converted) {
        return converted;
    }

    @Override
    public Class<O> getOriginalType() {
        return originalType;
    }

    @Override
    @SuppressWarnings("java:S4144")
    public Class<O> getConvertedType() {
        return originalType;
    }

}
