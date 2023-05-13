package sh.miles.raven.api.utils;

import java.util.Arrays;
import java.util.List;

/**
 * DotPathUtils is a utility class for working with dot paths.
 * 
 * @implNote This class is intended for internal use, but can safely be used by
 *           external projects. There is no intent in ever removing this class
 *           nor deprecating it.
 */
public final class DotPathUtils {

    private DotPathUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Add a child to a dot path
     * 
     * @param path  the dot path
     * @param child the child to add
     * @return the new dot path
     */
    public static String addDotPath(final String path, final String child) {
        return path + "." + child;
    }

    /**
     * getRoot returns the first part of a dot path (e.g. "root" in "root.child")
     * 
     * @param path the dot path
     * @return the root of the dot path
     */
    public static String getRoot(final String path) {
        return splitDotPath(path)[0];
    }

    /**
     * splitDotPath splits a dot path into an array of strings
     * 
     * @param path the dot path
     * @return the array of strings
     */
    public static String[] splitDotPath(final String path) {
        return path.split("\\.");
    }

    /**
     * splitDotPathToList splits a dot path into a list of strings
     * 
     * @param path the dot path
     * @return the list of strings
     */
    public static List<String> splitDotPathToList(final String path) {
        return Arrays.asList(splitDotPath(path));
    }

    /**
     * isDotPath checks if a string is a dot path
     * 
     * @implNote This method won't work on normal sentences and should only be used
     *           for dot paths (e.g. "root.child") however it will also detect (Have
     *           a nice day. Bye!) as a dot path
     * @param path the string to check
     * @return true if the string is a dot path, false otherwise
     */
    public static boolean isDotPath(final String path) {
        return path.contains(".");
    }

    /**
     * removeHangingDot removes a hanging dot from a dot path (e.g. "root.child." to
     * "root.child")
     * 
     * @param path the dot path
     * @return the dot path without the hanging dot
     */
    public static String removeHangingDot(final String path) {
        if (path.endsWith(".")) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * removeRoot removes the root from a dot path (e.g. "root.child" to "child")
     * 
     * @param path the dot path
     * @return the dot path without the root
     */
    public static String removeRoot(final String path) {
        return path.substring(path.indexOf(".") + 1);
    }

    /**
     * toDotPath converts a list of strings to a dot path (e.g. ["root", "child"] to
     * "root.child")
     * 
     * @param path the list of strings
     * @return the dot path
     */
    public static String toDotPath(final String... path) {
        return String.join(".", path);
    }

}
