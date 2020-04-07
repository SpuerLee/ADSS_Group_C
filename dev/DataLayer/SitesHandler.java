package DataLayer;

public class SitesHandler {

    private static Database database=Database.getInstance();

    public void AddSite(int id){
        database.AddSite(id);
    }

    public void RemoveSite(int id){
        database.RemoveSite(id);
    }
}
