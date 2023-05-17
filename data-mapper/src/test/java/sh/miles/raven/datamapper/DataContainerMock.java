package sh.miles.raven.datamapper;

import sh.miles.raven.datamapper.api.DataContainer;
import sh.miles.raven.datamapper.api.fields.DataField;
import sh.miles.raven.datamapper.api.fields.DataTuple;

public class DataContainerMock implements DataContainer {

    private final DataField<String> string = new DataField<String>("string", "default");
    private final DataTuple<Integer> integer = new DataTuple<Integer>("int", -1);

    public String getString() {
        return string.getValue();
    }

    public int getInteger() {
        return integer.getValue();
    }

}
