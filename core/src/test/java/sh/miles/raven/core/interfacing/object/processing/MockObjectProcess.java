package sh.miles.raven.core.interfacing.object.processing;

import sh.miles.raven.api.interfacing.object.DataField;
import sh.miles.raven.api.interfacing.object.DataObject;

public class MockObjectProcess implements DataObject {

    private final DataField<String> string;
    private final DataField<Integer> integer;
    private final DataField<InternalMockObjectProcess> internalObject;

    public MockObjectProcess() {
        string = new DataField<>("string", "default");
        integer = new DataField<>("int", -999);
        internalObject = new DataField<>("object", new InternalMockObjectProcess());
    }

    public DataField<String> getStringField() {
        return string;
    }

    public DataField<Integer> getIntegerField() {
        return integer;
    }

    public DataField<InternalMockObjectProcess> getInternalObjectField() {
        return internalObject;
    }

    @Override
    public String getDatabaseSectionName() {
        return "object";
    }

    public static final class InternalMockObjectProcess implements DataObject {

        private final DataField<String> string;
        private final DataField<Integer> integer;

        public InternalMockObjectProcess() {
            string = new DataField<String>("string", "default");
            integer = new DataField<Integer>("int", -999);
        }

        public DataField<String> getStringField() {
            return string;
        }

        public DataField<Integer> getIntegerField() {
            return integer;
        }

        @Override
        public String getDatabaseSectionName() {
            return "object";
        }
    }

}
