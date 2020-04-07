package DataLayer;

public class TrucksHandler {

    private static Database database=Database.getInstance();

    public void AddTruck(int id){
        database.AddTruck(id);
    }

    public void RemoveTruck(int id){
        database.RemoveTruck(id);
    }
}
