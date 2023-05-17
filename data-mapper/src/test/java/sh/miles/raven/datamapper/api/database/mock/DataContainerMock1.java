package sh.miles.raven.datamapper.api.database.mock;

import sh.miles.raven.datamapper.api.DataContainer;
import sh.miles.raven.datamapper.api.fields.DataField;

public class DataContainerMock1 implements DataContainer {

    public static final int FIELD_0_DEFAULT = 0;
    public static final int FIELD_1_DEFAULT = 1;

    private final DataField<Integer> field0 = new DataField<>("field0", FIELD_0_DEFAULT);
    private final DataField<Integer> field1 = new DataField<>("field1", FIELD_1_DEFAULT);

    public int getField0() {
        return field0.getValue();
    }

    public void setField0(int value) {
        field0.setValue(value);
    }

    public int getField1() {
        return field1.getValue();
    }

    public void setField1(int value) {
        field1.setValue(value);
    }

}
