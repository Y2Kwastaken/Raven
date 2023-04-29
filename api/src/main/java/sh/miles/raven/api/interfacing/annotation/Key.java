package sh.miles.raven.api.interfacing.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a field as a key in a {@link DataSection}.
 * The class must be annotated with {@link DataSection} and the field must be of
 * a type that is supported by the database.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
    /**
     * The name of the key. If not specified, the name of the field will be used.
     * 
     * @return the name of the key
     */
    String name() default "";
}
