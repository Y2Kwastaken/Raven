package sh.miles.raven.api.interfacing.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sh.miles.raven.api.conversion.TypeConverter;

/**
 * This annotation is used to mark a field that is a nested object in a
 * {@link DataSection}. This class can get away with not being annotated with a
 * {@link DataSection}. The fields contained in this class must be supported by
 * the database or be annotated with {@link NestedObject}.
 * 
 * This annotation is not required for nested objects because of the existence
 * of {@link TypeConverter}. TypeConverter is the recommended way to convert a
 * object.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NestedObject {
}
