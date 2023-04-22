package sh.miles.pluginpal.core.locale;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

/**
 * Manages all the locales for a plugin and provides a way to get the locale for
 * a specific language
 */
public class PluginLocaleManager {

    private final Plugin plugin;
    private final String[] files;
    private final Map<Locale, PluginLocale> locales;
    private PluginLocale defaultLocale;
    private PluginLocale currentLocale;

    /**
     * Creates a new PluginLocaleManager
     * 
     * @param files the files to load from the locales. This param is used to ensure
     *              standardized file names
     */
    public PluginLocaleManager(@NotNull final Plugin plugin, @NotNull final String[] files) {
        this.plugin = plugin;
        this.files = files.clone();
        this.locales = new HashMap<>();
    }

    public String getTranslation(@NotNull String key) {
        Preconditions.checkArgument(key != null, "key cannot be null");
        Preconditions.checkArgument(defaultLocale != null, "defaultLocale cannot be null");

        if (currentLocale == null) {
            return defaultLocale.getTranslation(key);
        }

        final String currentLocaleValue = currentLocale.getTranslation(key);
        return currentLocaleValue == null ? defaultLocale.getTranslation(key) : currentLocaleValue;
    }

    public FileConfiguration getConfig(@NotNull String fileName) {
        Preconditions.checkArgument(fileName != null, "fileName cannot be null");
        Preconditions.checkArgument(defaultLocale != null, "defaultLocale cannot be null");

        if (currentLocale == null) {
            return defaultLocale.getConfig(fileName);
        }

        final FileConfiguration currentLocaleValue = currentLocale.getConfig(fileName);
        return currentLocaleValue == null ? defaultLocale.getConfig(fileName) : currentLocaleValue;
    }

    public String format(@NotNull String str) {
        Preconditions.checkArgument(str != null, "str cannot be null");
        Preconditions.checkArgument(defaultLocale != null, "defaultLocale cannot be null");

        if (currentLocale == null) {
            return defaultLocale.format(str);
        }

        final String currentLocaleValue = currentLocale.format(str);
        return currentLocaleValue == null ? defaultLocale.format(str) : currentLocaleValue;
    }

    /**
     * Adds a locale to the manager
     * 
     * @param locale the locale to add
     */
    public void addLocale(@NotNull PluginLocale locale) {
        Preconditions.checkArgument(locale != null, "locale cannot be null");

        locales.put(locale.locale(), locale);
    }

    /**
     * Gets the locale for a specific language
     * 
     * @param locale the language to get the locale for
     * @return the locale for the language
     */
    public PluginLocale getLocale(@NotNull Locale locale) {
        Preconditions.checkArgument(locale != null, "locale cannot be null");

        return locales.get(locale);
    }

    /**
     * Attempts to load a locale from the plugin's jar file and add it to the
     * manager
     */
    public void loadLocale(@NotNull final String folderName, @NotNull final Locale locale) {
        Preconditions.checkArgument(folderName != null, "folderName cannot be null");
        Preconditions.checkArgument(locale != null, "locale cannot be null");

        locales.put(locale, new PluginLocale(plugin, locale, folderName, files));
    }

    /**
     * Sets the default locale for the plugin
     * This locale is used as a fallback if a string is not found in the current
     * 
     * @param locale the default locale
     */
    public void defaultLocale(@NotNull PluginLocale locale) {
        Preconditions.checkArgument(locale != null, "locale cannot be null");

        this.defaultLocale = locale;
    }

    /**
     * Sets the default locale for the plugin
     * This locale is used as a fallback if a string is not found in the current
     * 
     * @param locale the default locale
     */
    public void defaultLocale(@NotNull Locale locale) {
        Preconditions.checkArgument(locale != null, "locale cannot be null");
        final PluginLocale pluginLocale = locales.get(locale);
        if (pluginLocale == null) {
            throw new IllegalArgumentException("Locale " + locale + " does not exist");
        }
        this.defaultLocale = pluginLocale;
    }

    /**
     * Gets the default locale for the plugin
     * This locale is used as a fallback if a string is not found in the current
     * 
     * @return the default locale
     */
    public PluginLocale defaultLocale() {
        return defaultLocale;
    }

    /**
     * Sets the current locale for the plugin
     * 
     * @param locale the current locale
     */
    public void currentLocale(@NotNull PluginLocale locale) {
        Preconditions.checkArgument(locale != null, "locale cannot be null");

        this.currentLocale = locale;
    }

    /**
     * Sets the current locale for the plugin
     * 
     * @param locale the current locale
     */
    public void currentLocale(@NotNull Locale locale) {
        Preconditions.checkArgument(locale != null, "locale cannot be null");
        final PluginLocale pluginLocale = locales.get(locale);
        if (pluginLocale == null) {
            throw new IllegalArgumentException("Locale " + locale + " does not exist");
        }
        this.currentLocale = pluginLocale;
    }

    /**
     * Gets the current locale for the plugin
     * 
     * @return the current locale
     */
    public PluginLocale currentLocale() {
        return currentLocale;
    }
}
