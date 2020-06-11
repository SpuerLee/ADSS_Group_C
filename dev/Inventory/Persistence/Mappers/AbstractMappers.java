package Inventory.Persistence.Mappers;

import DataAccess.SupInvDBConn;

import java.sql.Connection;

public abstract class AbstractMappers<T> {

    private Connection myDB;

    public AbstractMappers() {
        this.myDB = SupInvDBConn.getInstance();
    }

    //region getters&setters
    public Connection getMyDB() {
        return myDB;
    }

    public void setMyDB(Connection myDB) {
        this.myDB = myDB;
    }
    //endregion

   // public abstract HashMap<Integer, T> load() throws SQLException;
    public abstract void insert();
    public abstract void update();
    public abstract void delete();

}