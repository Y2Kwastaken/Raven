package sh.miles.raven.datamapper;

import sh.miles.raven.api.database.Database;
import sh.miles.raven.datamapper.api.DataBlock;
import sh.miles.raven.datamapper.parse.DataBlockParser;

public class DataBlockMock extends DataBlock<String> {

    private final String id;
    private final Database database;

    public DataBlockMock(String id, Database database) {
        super();
        this.id = id;
        this.database = database;
        init();
    }

    public void init() {
        addContainer("object", DataContainerMock.class);
        DataBlockParser.parseDataBlock(this, database.getSection("test-collection", id));
    }

}
