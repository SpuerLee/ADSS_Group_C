package DataLayer;

public class ItemsHandler {

    private static Database database=Database.getInstance();

    public void AddItems(int id){
        database.Additems(id);
    }

    public void RemoveDriver(int id){
        database.Removeitems(id);
    }
}
