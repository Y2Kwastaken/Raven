package sh.miles.pluginpal.core.start;

/**
 * Enum showing state of a dependency
 */
public enum LoadState {
    /**
     * If the dependency is loaded
     */
    LOADED,
    /**
     * If the dependency failed to load
     */
    FAILED,
    /**
     * If the depndency is unloaded and has not failed to load
     */
    UNLOADED
}
