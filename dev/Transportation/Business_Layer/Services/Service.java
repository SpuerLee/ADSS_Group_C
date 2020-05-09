package Transportation.Business_Layer.Services;

import Transportation.Business_Layer.*;

import com.google.gson.*;


import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Service {

    public ConcurrentHashMap<Integer, Truck> getHashTrucks() {
        return HashTrucks;
    }

    private static class SingletonService {
        private static Service instance = new Service();
    }
    private Service() {
        // initialization code..
    }
    public static Service getInstance() {
        return SingletonService.instance;
    }

    private ConcurrentHashMap<Integer, Driver> Drivers= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Supplier> HashSuppliers= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Store> HashStore= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Truck> HashTrucks= new ConcurrentHashMap<>();
    private List<Transportation.Business_Layer.ItemsFile> ItemsFile= new LinkedList<>();
    private ConcurrentHashMap<Integer,Transportation> HashTransportation= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,MissingItems> MissingItems= new ConcurrentHashMap<>();
    public List<License> license_list = new LinkedList<License>();
    private List<Area> area_list = new LinkedList<Area>();



    public void uploadData()
    {
        JsonParser parser = new JsonParser();
        try (InputStream is = this.getClass().getResourceAsStream("/Transportation/DataLayer/Data.json");
             Reader rd = new InputStreamReader(is, "UTF-8"); ) {
            Object obj = parser.parse(rd);
            Gson gson = new Gson();

            JsonObject jsonObject = (JsonObject) obj;
          //  JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            final JsonArray drivers = jsonObject.get("Drivers").getAsJsonArray();
            for (int i = 0; i < drivers.size(); i++) {
                final JsonObject driver = drivers.get(i).getAsJsonObject();
                final JsonArray license = driver.getAsJsonArray("licenses");
                List<License> licenses=new LinkedList<License>();
                for(int j=0;j<license.size();j++){
                    License type = new License(license.get(j).getAsString());
                    licenses.add(type);
                }
                Driver add=new Driver(driver.get("name").getAsString(),licenses);
                Drivers.put(add.getId(),add);

            }
            final JsonArray sites = jsonObject.get("Sites").getAsJsonArray();
            for (int i = 0; i < sites.size(); i++) {
                final JsonObject site = sites.get(i).getAsJsonObject();
                String type=site.get("site_type").getAsString();
                Area area=new Area(site.get("area").getAsString());
                boolean add=true;
                for(Area area1:area_list){
                    if (area1.toString().equals(area.toString())) {
                        add=false;
                        break;
                    }
                }
                if(add)
                    area_list.add(area);
                if(type.equals("store")){
                    Address address=new Address(site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt());
                    Store store=new Store(site.get("name").getAsString(),site.get("phone").getAsString(),site.get("name_of_contact").getAsString(),address,new Area(site.get("area").getAsString()));
                    HashStore.put(store.getId(),store);
                }
                else if(type.equals("supplier")){
                    Address address=new Address(site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt());
                    Supplier supplier=new Supplier(site.get("name").getAsString(),site.get("phone").getAsString(),site.get("name_of_contact").getAsString(),address,new Area(site.get("area").getAsString()));
                    HashSuppliers.put(supplier.getId(),supplier);
                   }
                }

            final JsonArray trucks = jsonObject.get("Trucks").getAsJsonArray();
            for (int i = 0; i < trucks.size(); i++) {
                final JsonObject truck = trucks.get(i).getAsJsonObject();
                final JsonArray license = truck.getAsJsonArray("licenses");
                List<License> licenses=new LinkedList<License>();
                for(int j=0;j<license.size();j++){
                    License type = new License(license.get(j).getAsString());
                    licenses.add(type);
                }
                Truck truck1 = new Truck(truck.get("license_number").getAsInt(),licenses,truck.get("model").getAsString(),truck.get("weight").getAsDouble(),truck.get("max weight").getAsDouble());
                HashTrucks.put(truck1.getId(),truck1);
            }
            final JsonArray missing_items = jsonObject.get("Missing_Items").getAsJsonArray();
            for (int i = 0; i < missing_items.size(); i++) {
                final JsonObject missing = missing_items.get(i).getAsJsonObject();
                final JsonArray items = missing.getAsJsonArray("items_list");
                HashMap<String,Integer> map=new HashMap<String,Integer>();
                for(int j=0;j<items.size();j++){
                    String [] items_to_add=items.get(j).getAsString().split(":");
                    map.put(items_to_add[1],Integer.parseInt(items_to_add[0]));
                }
                MissingItems items1=new MissingItems(missing.get("store_id").getAsInt(),missing.get("supplier_id").getAsInt(),map);
                MissingItems.put(items1.getID(),items1);
            }

        }
        catch (Exception e)
        {

        }
    }

    /*
    //missing items

    //create Regular Transportation
    public boolean createRegularTransportation(Date date, LocalTime leaving_time, int driver_id,
                                        int truck_license_number, int supplierid, List<Integer> stores)
    {
        List <Integer> suppliers=new LinkedList<>();
        suppliers.add(supplierid);
        Transportation transportation =
                new Transportation(date,leaving_time,driver_id,truck_license_number,suppliers,stores);
        HashTransportation.put(transportation.getID(),transportation);
        HashDrivers.get(driver_id).addDate(date);
        HashTrucks.get(truck_license_number).addDate(date);
        return true;
    }

    //add each store to the itemFile
    public void add_to_items_file(Date date, LocalTime leaving_time, int driver_id,
                                     int truck_license_number, int supplierid, int store, HashMap<String,Integer> items){
        for(Transportation transportation: HashTransportation.values()){
            if(transportation.getDriveId().equals(driver_id)& transportation.getLeaving_time().equals(leaving_time)& (transportation.getTrucklicense()==truck_license_number)){
                int transportId=transportation.getID();
                HashItemsFile.put(transportId, new ItemsFile(transportId,store,supplierid,items));
            }
        }
    }
  */

    public String getMissingItems()
    {
        Gson gson = new Gson();
        String output = "";
        for (MissingItems missingItems: MissingItems.values())
        {
            output = output + gson.toJson(missingItems)+"\n";
        }
        return output;
    }

    public String getAreas()
    {
        String output = "[ ";
        for (Area area: area_list)
        {
            output = output + area.toString() +", ";
        }
        output = output + "]";
        return output;
    }

    public List<ItemsFile> getItemsFile(){
        return ItemsFile;
    }

    //print list of all the suppliers

    public ConcurrentHashMap<Integer,MissingItems> getMissing(){
        return MissingItems;
    }

    public ConcurrentHashMap<Integer,Supplier> getSuppliersMap()
    {
        return HashSuppliers;
    }

    public ConcurrentHashMap<Integer,Store> getHashStoresMap()
    {
        return HashStore;
    }

    public ConcurrentHashMap<Integer,Transportation> getHashTransportation(){
        return HashTransportation;
    }

    public ConcurrentHashMap<Integer, Driver> getDrivers(){
        return Drivers;
    }

    public List<Area> getArea_list(){
        return area_list;
    }

}


