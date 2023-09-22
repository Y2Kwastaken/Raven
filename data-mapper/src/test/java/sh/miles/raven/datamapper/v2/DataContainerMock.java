package sh.miles.raven.datamapper.v2;

import org.jetbrains.annotations.NotNull;
import sh.miles.raven.datamapper.v2.data.DataField;
import sh.miles.raven.datamapper.v2.data.DataTuple;

public final class DataContainerMock implements DataContainer {

    private final DataField<String> string = new DataField<String>("string", "default");
    private final DataTuple<Integer> integer = new DataTuple<Integer>("int", -1);

    public String getString() {
        return string.getValue();
    }

    public int getInteger() {
        return integer.getValue();
    }

    public void setString(@NotNull final String str) {
        string.setValue(str);
    }


}
