package sh.miles.raven.datamapper.api.database.mock;

import sh.miles.raven.datamapper.api.DataContainer;
import sh.miles.raven.datamapper.api.fields.DataField;

public class DataContainerMock0 implements DataContainer {

    public static final String FIELD_0_DEFAULT = "value0-default";
    public static final String FIELD_1_DEFAULT = "value1-default";

    private final DataField<String> field0 = new DataField<>("field0", FIELD_0_DEFAULT);
    private final DataField<String> field1 = new DataField<>("field1", FIELD_1_DEFAULT);

    public String getField0() {
        return field0.getValue();
    }

    public void setField0(String value) {
        field0.setValue(value);
    }

    public String getField1() {
        return field1.getValue();
    }

    public void setField1(String value) {
        field1.setValue(value);
    }
}
