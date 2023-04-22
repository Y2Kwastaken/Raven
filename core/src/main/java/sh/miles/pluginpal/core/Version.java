package sh.miles.pluginpal.core;

import org.bukkit.Bukkit;

/**
 * Utility class for retrieving server version information
 */
public final class Version {

    private Version() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    /**
     * Gets the server version
     *
     * Formatted "1.19.3"
     *
     * @return Server version
     */
    public static String getServerVersion() {
        return Bukkit.getBukkitVersion().substring(0, Bukkit.getBukkitVersion().indexOf("-"));
    }

    /**
     * Gets the server version
     *
     * Formatted "1_19_3"
     *
     * @return Server version
     */
    public static String getServerVersionUnderscores() {
        return getServerVersion().replace(".", "_");
    }

    /**
     * Gets the servers NMS version
     *
     * Formatted "1_19_3_R1"
     *
     * @return Server NMS version
     */
    public static String getServerNMSVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

}