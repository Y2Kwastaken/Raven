package sh.miles.pluginpal.core.start;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Contains information about a plugin dependency
 * 
 * @param name      The name of the plugin
 * @param required  If the plugin is required
 * @param fallbacks A list of fallback plugins that could be used instead
 * @param consumer  A consumer that is called when the plugin is enabled
 */
public record PluginDependency(String name, boolean required, List<String> fallbacks, Consumer<Plugin> consumer) {

    public <T extends Plugin> T getPluginAs(Class<T> clazz) {
        return clazz.cast(getPlugin());
    }

    public Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin(name);
    }

    public static boolean isDependencyEnabled(final PluginDependency dependency) {
        return Bukkit.getServer().getPluginManager().isPluginEnabled(dependency.name());
    }

    public static boolean isDependencyEnabled(final String name) {
        return Bukkit.getServer().getPluginManager().isPluginEnabled(name);
    }

    public static Plugin getPlugin(final String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name);
    }

    public static Plugin getPlugin(final PluginDependency dependency) {
        return Bukkit.getServer().getPluginManager().getPlugin(dependency.name());
    }
}
