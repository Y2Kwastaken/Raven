package sh.miles.pluginpal.core.locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import sh.miles.pluginpal.core.AbstractTest;

public class PluginLocaleThoroughTest extends AbstractTest {

    @Test
    public void testLocaleSophisticated() {
        final PluginLocaleManager plm = new PluginLocaleManager(getPlugin(), new String[] {
                "easy/config.yml",
                "hard/config.yml",
        });

        plm.loadLocale("sph_lang/en_bundle", new Locale("en"));
        plm.loadLocale("sph_lang/es_bundle", new Locale("es"));

        plm.defaultLocale(new Locale("en"));
        plm.currentLocale(new Locale("es"));

        assertEquals("facil", plm.getTranslation("easy"));
        assertEquals("difícil", plm.getTranslation("hard"));

        assertEquals("super easy", plm.getTranslation("easy2"));
        assertEquals("super hard", plm.getTranslation("hard2"));
    }

}
