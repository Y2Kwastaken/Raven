package sh.miles.pluginpal.core.start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class PluginDependencyLoader {

    private final Map<PluginDependency, LoadState> dependencies;
    private final Map<PluginDependency, List<Class<?>>> spigotServices;
    private final Map<PluginDependency, Object> loadedServices;
    private boolean loaded;

    public PluginDependencyLoader() {
        this.dependencies = new HashMap<>();
        this.spigotServices = new HashMap<>();
        this.loadedServices = new HashMap<>();
        this.loaded = false;
    }

    public void load() {
        if (this.loaded) {
            throw new IllegalStateException("Cannot load twice");
        }

        this.dependencies.forEach((PluginDependency dependency, LoadState loadState) -> {
            if (loadState == LoadState.UNLOADED) {
                this.load(dependency);
            }
        });
    }

    /**
     * Forceably loads a dependency
     * 
     * @param dependency The dependency to load
     * 
     */
    private void load(final PluginDependency dependency) {

        if (!PluginDependency.isDependencyEnabled(dependency)) {
            if (dependency.required()) {
                throw new DependencyLoadException("Required dependency " + dependency.name() + " is not loaded");
            } else {
                this.dependencies.put(dependency, LoadState.FAILED);
                Bukkit.getLogger()
                        .warning("Optional dependency " + dependency.name()
                                + " is not loaded and will not be loaded if you have one of the following plugins installed this can be safely ignored: "
                                + String.join(", ", dependency.fallbacks()));
            }
        }

        this.dependencies.put(dependency, LoadState.LOADED);
        this.spigotServices.get(dependency).forEach((Class<?> service) -> {
            final RegisteredServiceProvider<?> provider = Bukkit.getServicesManager().getRegistration(service);
            if (provider == null) {
                throw new ServiceLoadException("Required service " + service.getName() + " is not loaded");
            }
            this.loadedServices.put(dependency, provider.getProvider());
        });
    }

    /**
     * Adds a dependency to the loader
     * 
     * @param dependency The dependency to add
     * @return The loader
     */
    public PluginDependencyLoader addDependency(PluginDependency dependency) {
        if (this.loaded) {
            throw new IllegalStateException("Cannot add dependency after loading");
        }
        this.dependencies.put(dependency, LoadState.UNLOADED);
        return this;
    }

    /**
     * Adds a dependency to the loader
     * 
     * @param name      The name of the plugin
     * @param required  If the plugin is required
     * @param fallbacks A list of fallback plugins that could be used instead
     * @param consumer  A consumer that is called when the plugin is enabled
     * @return The loader
     */
    public PluginDependencyLoader addDependency(String name, boolean required, String[] fallbacks,
            Consumer<Plugin> consumer) {

        if (this.loaded) {
            throw new IllegalStateException("Cannot add dependency after loading");
        }

        this.dependencies.put(new PluginDependency(name, required, Arrays.asList(fallbacks), consumer),
                LoadState.UNLOADED);
        return this;
    }

    /**
     * Loads the dependencies
     * 
     * @param dependency   The dependency to load
     * @param serviceClass The service class
     * @return The dependency
     */
    public PluginDependencyLoader addPluginService(PluginDependency dependency, Class<?> serviceClass) {
        if (this.loaded) {
            throw new IllegalStateException("Cannot add dependency after loading");
        }

        // Check if dependency exists if it does not add an empty list then continue
        this.spigotServices.computeIfAbsent(dependency, k -> new ArrayList<>());
        this.spigotServices.get(dependency).add(serviceClass);

        return this;
    }

    /**
     * Loads the dependencies
     * 
     * @param dependency   The dependency to load
     * @param serviceClass The service class
     * @return The dependency
     */
    public PluginDependencyLoader addPluginService(String dependency, Class<?> serviceClass) {
        return this.addPluginService(this.getDependencyByName(dependency), serviceClass);
    }

    /**
     * Loads the dependencies
     * 
     * @param <T>          The type of the service
     * @param dependency   The dependency to load
     * @param serviceClass The service class
     * @return The dependency
     */
    public <T> T getService(PluginDependency dependency, Class<T> serviceClass) {
        if (!this.loaded) {
            throw new IllegalStateException("Cannot get service before loading");
        }
        return serviceClass.cast(this.loadedServices.get(dependency));
    }

    /**
     * Loads the dependencies
     * 
     * @param <T>          The type of the service
     * @param dependency   The dependency to load
     * @param serviceClass The service class
     * @return The dependency
     */
    public <T> T getService(String dependency, Class<T> serviceClass) {
        if (!this.loaded) {
            throw new IllegalStateException("Cannot get service before loading");
        }
        return this.getService(this.getDependencyByName(dependency), serviceClass);
    }

    /**
     * Finds service for dependency or throws an exception
     * 
     * @param <T>          The type of the service
     * @param serviceClass The service class
     * @return The service
     */
    public <T> T getService(Class<T> serviceClass) {
        if (!this.loaded) {
            throw new IllegalStateException("Cannot get service before loading");
        }
        return this.loadedServices.values().stream().filter(serviceClass::isInstance).findFirst()
                .map(serviceClass::cast)
                .orElseThrow();
    }

    /**
     * Attempts to retrieve a dependency by its name and returns it, if it exists
     * otherwise throws an exception
     * 
     * @param name The name of the dependency
     * @return The dependency
     */
    public PluginDependency getDependencyByName(String name) {
        return this.dependencies.keySet().stream().filter(d -> d.name().equals(name)).findFirst().orElseThrow();
    }

    private static final class DependencyLoadException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public DependencyLoadException(String message) {
            super(message);
        }

    }

    private static final class ServiceLoadException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public ServiceLoadException(String message) {
            super(message);
        }

    }
}
