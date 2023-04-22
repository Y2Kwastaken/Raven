package sh.miles.pluginpal.core.locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import sh.miles.pluginpal.core.AbstractTest;

public class PluginLocaleManagerTest extends AbstractTest {

    @Test
    public void testPluginLocaleManager() {
        PluginLocaleManager plm = new PluginLocaleManager(getPlugin(), new String[] { "config.yml" });
        plm.loadLocale("locale/en", new Locale("en"));
        plm.loadLocale("locale/es", new Locale("es"));

        plm.defaultLocale(new Locale("en"));
        plm.currentLocale(new Locale("es"));

        assertEquals("Hola, bienvenido al servidor!", plm.getTranslation("welcome"), "Locale was either null or not in spanish");
        assertNotNull(plm.getTranslation("goodbye"));
    }

}
