package sh.miles.raven.api.interfacing.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sh.miles.raven.api.database.DatabaseSection;

/**
 * This annotation is used to mark a class as a DataSection. A DataSection is
 * corresponding to a {@link DatabaseSection} and is used to store data in a
 * database.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSection {
    /**
     * The name of the data section
     * 
     * @return the name of the data section
     */
    String name();
}
