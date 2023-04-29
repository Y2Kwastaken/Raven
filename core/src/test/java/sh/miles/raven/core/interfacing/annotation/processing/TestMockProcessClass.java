package sh.miles.raven.core.interfacing.annotation.processing;

import lombok.Getter;
import sh.miles.raven.api.interfacing.annotation.DataSection;
import sh.miles.raven.api.interfacing.annotation.Key;
import sh.miles.raven.api.interfacing.annotation.NestedObject;

@Getter
@DataSection(name = "object")
public class TestMockProcessClass {

    @Key(name = "string")
    private String string;

    @Key(name = "int")
    private int integer;

    @NestedObject
    @Key(name = "object")
    private TestInternalMock internal;

    public static final class TestInternalMock {

        @Key(name = "string")
        private String string;

        @Key(name = "int")
        private int integer;

    }
}
