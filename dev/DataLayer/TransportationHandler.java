package DataLayer;

public class TransportationHandler {

    private static Database database=Database.getInstance();

    public void AddTransport(int id){
        database.AddTransport(id);
    }

    public void RemoveSite(int id){
        database.RemoveTransport(id);
    }
}
