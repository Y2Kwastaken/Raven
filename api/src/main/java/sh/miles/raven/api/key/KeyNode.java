package sh.miles.raven.api.key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;

/**
 * KeyNode defines a node in a tree structure of keys
 */
public class KeyNode {

    private String key;
    private List<KeyNode> children;

    /**
     * KeyNode creates a new KeyNode
     * 
     * @param key      the key of the node
     * @param children the children of the node
     */
    public KeyNode(@NotNull String key, @NotNull List<KeyNode> children) {
        Preconditions.checkNotNull(key, "key should not be null");
        Preconditions.checkNotNull(children, "children list should not be null");

        this.key = key;
        this.children = new ArrayList<>(children);
    }

    /**
     * KeyNode creates a new KeyNode with no children
     * 
     * @param key the key of the node
     */
    public KeyNode(@NotNull String key) {
        this(key, new ArrayList<>());
    }

    /**
     * addChild adds a child to the node
     * 
     * @param child the child to add
     */
    public void addChild(KeyNode child) {
        children.add(child);
    }

    /**
     * getChild returns the child with the given key
     * 
     * @param key the key of the child
     * @return the child with the given key
     */
    @Nullable
    public KeyNode getChild(String key) {
        return children.stream().filter(child -> child.getKey().equals(key)).findFirst().orElse(null);
    }

    /**
     * getKey returns the key of the node
     * 
     * @return the key of the node
     */
    public String getKey() {
        return key;
    }

    /**
     * getChildren returns the children of the node
     * 
     * @return the children of the node
     */
    public List<KeyNode> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * hasChildren returns true if the node has children
     * 
     * @return true if the node has children
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * hasChild returns true if the node has a child with the given key
     * 
     * @param key the key to check
     * @return true if the node has a child with the given key
     */
    public boolean hasChild(String key) {
        return children.stream().anyMatch(child -> child.getKey().equals(key));
    }

    /**
     * toMap converts the node to a map of keys to children
     * 
     * @return the map of keys to children
     */
    public Map<String, Object> toMap() {
        return toMap(this);
    }

    @Override
    public String toString() {
        return "KeyNode [key=" + key + ", children=" + children + "]";
    }

    /**
     * toMap converts the node to a map of keys to children
     * 
     * @param node the node to convert
     * @return the map of keys to children
     */
    public static Map<String, Object> toMap(KeyNode node) {
        Map<String, Object> map = new HashMap<>();
        map.put(node.getKey(),
                node.getChildren().stream().map((KeyNode keyNode) -> keyNode.toMap()).collect(Collectors.toList()));
        return map;
    }

}
