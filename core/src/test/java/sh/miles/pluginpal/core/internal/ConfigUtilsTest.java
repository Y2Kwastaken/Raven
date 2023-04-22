package sh.miles.pluginpal.core.internal;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.junit.jupiter.api.Test;

import sh.miles.pluginpal.core.AbstractTest;

public class ConfigUtilsTest extends AbstractTest {

    @Test
    public void testCreateConfig() {
        final FileConfiguration config = ConfigUtils.createConfig(getPlugin(), "test_cutils/file1.yml");
        assertTrue(config.contains("file1_thing"), "file1_thing should be in the config");
    }

    @Test
    public void testCreateConfigs(){
        final Map<String, FileConfiguration> configs = ConfigUtils.createConfigFiles(getPlugin(), "test_cutils", "file1.yml", "file2.yml");
        Bukkit.getLogger().info(configs.toString());
        assertTrue(configs.containsKey("file1.yml"), "file1.yml should be in the map");
        assertTrue(configs.containsKey("file2.yml"), "file2.yml should be in the map");

        assertTrue(configs.get("file1.yml").contains("file1_thing"), "file1_thing should be in the config");
        assertTrue(configs.get("file2.yml").contains("file2_thing"), "file2_thing should be in the config");
    }
}
