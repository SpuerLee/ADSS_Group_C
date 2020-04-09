package DataLayer;

import Business_Layer.License;

import java.util.List;

public class DriversHandler {

    private static Database database=Database.getInstance();

    public void AddDriver(int id){
        database.AddDriver(id);
    }

    public void RemoveDriver(int id){
        database.AddDriver(id);
    }
}
