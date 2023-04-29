package sh.miles.raven.api.conversion;

/**
 * This class is used to convert between different types.
 * 
 * @param <O> the original type
 * @param <C> the converted type
 */
public interface TypeConverter<O, C> {

    /**
     * Converts the original type to the converted type.
     * 
     * @param original the original type
     * @return the converted type
     */
    C convert(O original);

    /**
     * Converts the converted type to the original type.
     * 
     * @param converted the converted type
     * @return the original type
     */
    O revert(C converted);

    /**
     * Returns the original type.
     * 
     * @return the original type
     */
    Class<O> getOriginalType();

    /**
     * Returns the converted type.
     * 
     * @return the converted type
     */
    Class<C> getConvertedType();
}
