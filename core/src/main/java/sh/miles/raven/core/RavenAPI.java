package sh.miles.raven.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sh.miles.raven.core.conversion.TypeConversionManager;

/**
 * The main API class for Raven
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RavenAPI {

    public static final String VERSION = "2023.4.24-SNAPSHOT";
    public static final String BASE_PACKAGE = "sh.miles.raven";

    public static TypeConversionManager getTypeConversionManager() {
        return TypeConversionManager.getInstance();
    }

}
