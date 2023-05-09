package sh.miles.raven.provider.nitrite.database;

import java.util.List;

public enum SectionField {

    STRING("string", "Hello, World!"),
    INT("int", 5),
    LONG("long", 1003L),
    DOUBLE("double", 3.3),
    BOOLEAN("boolean", true),
    DOUBLE_LIST("doubleList", List.of(1.1, 2.2)),
    LONG_LIST("longList", List.of(1003L, 1004L)),
    OBJECT_STRING("object.string", "Hello, World!"),
    OBJECT_INT("object.int", 5),
    OBJECT_OBJECT_STRING("object.object.string", "Happy!"),
    OBJECT_OBJECT_INT("object.object.int", 6),;

    public static final String DOCUMENT_NAME = "test-document";
    public static final String COLLECTION_NAME = "test-collection";

    private final String key;
    private final Object value;

    SectionField(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }
}
