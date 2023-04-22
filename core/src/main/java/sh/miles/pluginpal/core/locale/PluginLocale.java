package sh.miles.pluginpal.core.locale;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import sh.miles.pluginpal.core.internal.ConfigUtils;

/**
 * Helper class for loading translations from a plugin. easily
 * 
 * @author
 */
public class PluginLocale {

    private final Locale locale;
    private final Map<String, String> map;
    private final Map<String, FileConfiguration> files;

    public PluginLocale(@NotNull Plugin plugin, @NotNull Locale locale, @NotNull String folderName,
            @NotNull String[] fileNames) {
        this.locale = locale;
        this.files = ConfigUtils.createConfigFiles(plugin, folderName, fileNames);
        this.map = getAllStrings(files);
    }

    /**
     * Retrieves a string from the map if it exists, otherwise returns null
     * 
     * @param key the key to get the string for
     * @return the string if it exists, otherwise null
     */
    public String getTranslation(@NotNull String key) {
        Preconditions.checkArgument(key != null, "key cannot be null");

        return map.get(key);
    }

    /**
     * Gets a config file from the map with the given file name
     * 
     * @param fileName the name of the file to get
     * @return the config file if it exists, otherwise null
     */
    public FileConfiguration getConfig(@NotNull String fileName) {
        Preconditions.checkArgument(fileName != null, "fileName cannot be null");

        return files.get(fileName);
    }

    /**
     * Formats a string with the values in the map. For example, if you have a
     * string
     * : "Hello {name}, how are you?" and you have a map with the following: name:
     * John
     * the output will be: "Hello John, how are you?"
     * 
     * If no map value exists for the key, the key will be left in the string
     * 
     * @param str the string to format
     * @return the formatted string
     */
    public String format(@NotNull String str) {
        Preconditions.checkArgument(str != null, "str cannot be null");

        for (final Map.Entry<String, String> entry : map.entrySet()) {
            str = str.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return str;
    }

    /**
     * Returns a map of all the key and values in the locale
     * 
     * @return a map of all the key and values in the locale
     */
    public Map<String, String> map() {
        return new HashMap<>(map);
    }

    /**
     * Returns the locale
     * 
     * @return the locale
     */
    public Locale locale() {
        return locale;
    }

    /**
     * This method will return a map of all the key and values in the file
     * the key is the index of the string in the file, the value is the string
     * value. Useful for getting all translations in the file. For example,
     * if you have a file with the following:
     * dog: dog
     * cat: cat
     * 
     * This method will return a map with the following:
     * Map.Entry("dog", "dog")
     * Map.Entry("cat", "cat")
     * 
     * @param files the files to get the strings from (the key is the file name)
     * @return
     */
    public static final Map<String, String> getAllStrings(@NotNull final Map<String, FileConfiguration> files) {
        final Map<String, String> strings = new HashMap<>();
        for (final Map.Entry<String, FileConfiguration> entry : files.entrySet()) {
            final FileConfiguration file = entry.getValue();
            for (final String key : file.getKeys(false)) {
                strings.put(key, file.getString(key));
            }
        }
        return strings;
    }

}
