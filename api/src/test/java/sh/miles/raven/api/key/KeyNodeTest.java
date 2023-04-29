package sh.miles.raven.api.key;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class KeyNodeTest {

    public static final Logger LOGGER = Logger.getLogger(KeyNodeTest.class.getName());

    @Test
    public void testKeyNodeShouldCreate() {
        KeyNode keyNode = new KeyNode("key", new ArrayList<>());
        assertEquals("key", keyNode.getKey(), "key should be equal");
    }

    @Test
    public void testKeyNodeNullFails() {
        assertThrows(
                NullPointerException.class,
                () -> new KeyNode("key", null),
                "children should throw when null");
        assertThrows(
                NullPointerException.class,
                () -> new KeyNode(null),
                "key should throw when null");
    }

    @Test
    public void testKeyNodeAddChild() {
        KeyNode keyNode = new KeyNode("key");
        keyNode.addChild(new KeyNode("child"));
        assertEquals(1, keyNode.getChildren().size(), "children should be 1");
    }

    @Test
    public void testKeyNodeGetChild() {
        KeyNode keyNode = new KeyNode("key");
        KeyNode child = new KeyNode("child");
        keyNode.addChild(child);
        assertEquals(child, keyNode.getChild("child"), "child should be equal");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testToMapAccurate() {
        final KeyNode keyNode = new KeyNode("key");

        for (int i = 0; i < 10; i++) {
            final KeyNode child = new KeyNode("child" + i);
            final KeyNode nestedChild = new KeyNode("nestedChild" + i);
            child.addChild(nestedChild);
            keyNode.addChild(child);
        }

        final Map<String, Object> map = keyNode.toMap();
        final String key = map.keySet().iterator().next();
        final List<Object> children = (List<Object>) map.get(key);

        for (int i = 0; i < 10; i++) {
            final Map<String, Object> child = (Map<String, Object>) children.get(i);
            final String childKey = child.keySet().iterator().next();
            assertEquals("child" + i, childKey, "child key should be equal");
            final List<Object> nestedChildren = (List<Object>) child.get(childKey);
            final Map<String, Object> nestedChild = (Map<String, Object>) nestedChildren.get(0);
            final String nestedChildKey = nestedChild.keySet().iterator().next();
            assertEquals("nestedChild" + i, nestedChildKey, "nested child key should be equal");
        }
    }

}