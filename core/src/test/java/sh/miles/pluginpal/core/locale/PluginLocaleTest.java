package sh.miles.pluginpal.core.locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.junit.jupiter.api.Test;

import sh.miles.pluginpal.core.AbstractTest;
import sh.miles.pluginpal.core.internal.ConfigUtils;

public class PluginLocaleTest extends AbstractTest {

    @Test
    public void testGetAllStrings() {
        final Map<String, FileConfiguration> configs = ConfigUtils.createConfigFiles(getPlugin(), "test_cutils",
                "file1.yml", "file2.yml", "file3.yml");
        final Map<String, String> strings = PluginLocale.getAllStrings(configs);

        assertTrue(strings.containsKey("file1_thing"), "file1_thing should be in the map");
        assertTrue(strings.containsKey("file2_thing"), "file2_thing should be in the map");
        assertTrue(strings.containsKey("file3_thing"), "file3_thing should be in the map");
    }

    @Test
    public void testPluginLocale() {
        final PluginLocale locale = new PluginLocale(getPlugin(), new Locale("en"), "plugin_local/en",
                new String[] { "main.yml", "other.yml" });

        assertNotNull(locale.getTranslation("main"), "main should not be null");
        assertNotNull(locale.getTranslation("other"), "other should not be null");

        final String str = "main: [{main}] \n other: [{other}] test.";
        final String result = locale.format(str);

        assertEquals("main: [This is the main page] \n other: [Factions] test.", result, "result should be formatted");
    }

}
