package DataLayer;

import Business_Layer.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Database {

    private List<Integer> drives=new LinkedList<>();
    private List<Integer> Items=new LinkedList<>();
    private List<Integer> sites=new LinkedList<>();
    private List<Integer> transportation= new LinkedList<>();
    private List<Integer> trucks= new LinkedList<>();
    Service service=new Service();

    private static class Holder {
        private static Database dataBase = new Database();
    }

    public static Database getInstance() {
        return Holder.dataBase;
    }

    public List<Integer> getDrives(){
        return drives;
    }

    public List<Integer> getItems() {
        return Items;
    }

    public List<Integer> getSites() {
        return sites;
    }

    public List<Integer> getTransportation() {
        return transportation;
    }

    public List<Integer> getTrucks() {
        return trucks;
    }

    public void AddDriver(int id){
        if(drives.contains(id)){
            Transfer_Error("The id of the driver is already exist");
        }
        else {
            drives.add(id);
        }
    }

    public void RemoveDriver(int id){
        if(!drives.contains(id)){
            Transfer_Error("The id of the driver doesn't exist");
        }
        drives.remove(id);
    }

    public void Additems(int id){
        Items.add(id);
    }

    public void Removeitems(int id){
        Items.remove(id);
    }

    public void AddSite(int id){
        sites.add(id);
    }

    public void RemoveSite(int id){
        sites.remove(id);
    }

    public void AddTransport(int id){
        transportation.add(id);
    }

    public void RemoveTransport(int id){
        transportation.remove(id);
    }

    public void AddTruck(int id){
        trucks.add(id);
    }

    public void RemoveTruck(int id){
        trucks.remove(id);
    }

    public void Transfer_Error(String error){
        service.Transfer_Error(error);
    }


}
