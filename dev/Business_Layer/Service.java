package Business_Layer;

import Presentation_Layer.Controler;

import com.google.gson.stream.JsonReader;
import com.google.gson.*;
import jdk.nashorn.internal.parser.JSONParser;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Service {

    public ConcurrentHashMap<Integer, Trucks> getHashTrucks() {
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

    private List<Drivers> Drivers= new LinkedList<>();
    private ConcurrentHashMap<String,Site> HashSites= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Trucks> HashTrucks= new ConcurrentHashMap<>();
    private List<ItemsFile> ItemsFile= new LinkedList<>();
    private ConcurrentHashMap<Integer,Transportation> HashTransportation= new ConcurrentHashMap<>();
    private List<MissingItems> MissingItems= new LinkedList<>();
    public List<License> license_list = new LinkedList<>();
    private List<Area> area_list = new LinkedList<>();



    public void uploadData()
    {
        JsonParser parser = new JsonParser();
        try (InputStream is = this.getClass().getResourceAsStream("/DataLayer/Data.json");
             Reader rd = new InputStreamReader(is, "UTF-8"); ) {
            Object obj = parser.parse(rd);
            Gson gson = new Gson();

            JsonObject jsonObject = (JsonObject) obj;
          //  JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            final JsonArray drivers = jsonObject.get("Drivers").getAsJsonArray();
            for (int i = 0; i < drivers.size(); i++) {
                final JsonObject driver = drivers.get(i).getAsJsonObject();
                final JsonArray license = driver.getAsJsonArray("licenses");
                List<License> licenses=new LinkedList<>();
                for(int j=0;j<license.size();j++){
                    License type = new License(license.get(j).getAsString());
                    licenses.add(type);
                }
                Drivers add=new Drivers(driver.get("name").getAsString(),licenses);
                Drivers.add(add);

            }
            final JsonArray sites = jsonObject.get("Sites").getAsJsonArray();
            for (int i = 0; i < sites.size(); i++) {
                final JsonObject site = sites.get(i).getAsJsonObject();
                String type=site.get("type").getAsString();
                if(type.equals("store")){
                    Address address=new Address(site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt());
                    HashSites.put(site.get("name").getAsString(),new Store(site.get("name").getAsString(),site.get("phone").getAsString(),site.get("name_of_contact").getAsString(),address,new Area(site.get("area").getAsString())));
                }
                else if(type.equals("supplier")){
                    Address address=new Address(site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt());
                    HashSites.put(site.get("name").getAsString(),new Supplier(site.get("name").getAsString(),site.get("phone").getAsString(),site.get("name_of_contact").getAsString(),address,new Area(site.get("area").getAsString())));
                   }
                }
              /*  if(!area_list.contains(site.get("area").getAsString())){
                    area_list.add(site.get("area").getAsString());
                } */
            final JsonArray trucks = jsonObject.get("Trucks").getAsJsonArray();
            for (int i = 0; i < trucks.size(); i++) {
                final JsonObject truck = trucks.get(i).getAsJsonObject();
                final JsonArray license = truck.getAsJsonArray("licenses");
                List<License> licenses=new LinkedList<>();
                for(int j=0;j<license.size();j++){
                    License type = new License(license.get(j).getAsString());
                    licenses.add(type);
                }
                HashTrucks.put(truck.get("license_number").getAsInt(),new Trucks(truck.get("license_number").getAsInt(),licenses,truck.get("model").getAsString(),truck.get("weight").getAsDouble(),truck.get("max weight").getAsDouble()));
            }
            final JsonArray missing_items = jsonObject.get("Missing_Items").getAsJsonArray();
            for (int i = 0; i < missing_items.size(); i++) {
                final JsonObject missing = missing_items.get(i).getAsJsonObject();
                final JsonArray items = missing.getAsJsonArray("items_list");
                HashMap<String,Integer> map=new HashMap<>();
                for(int j=0;j<items.size();j++){
                    String [] items_to_add=items.get(j).getAsString().split(":");
                    map.put(items_to_add[1],Integer.parseInt(items_to_add[0]));
                }
                MissingItems items1=new MissingItems(missing.get("store_id").getAsInt(),missing.get("supplier_id").getAsInt(),map);
                MissingItems.add(items1);
            }

        }
        catch (Exception e)
        {

        }
    }

    /*
    //missing items
    public boolean createTransportation(Date date, LocalTime leaving_time, int driver_id,
                                        int truck_license_number, List<Integer> suppliers, List<Integer> stores)
    {
        Transportation transportation =
                new Transportation(date,leaving_time,driver_id,truck_license_number,suppliers,stores);
        HashTransportation.put(transportation.getID(),transportation);
        List<Integer> id_to_delete = new LinkedList<>();
        for (MissingItems missingItems: HashMissingItems.values())
        {
            if(stores.contains(missingItems.getStoreId())&&suppliers.contains(missingItems.getSupplierId()))
            {
                ItemsFile itemFile = new ItemsFile(transportation.getID(),missingItems.getStoreId(),
                        missingItems.getSupplierId(),missingItems.getItems_list());
                HashItemsFile.put(itemFile.getID(),itemFile);
                id_to_delete.add(missingItems.getID());
            }
        }
        HashDrivers.get(driver_id).addDate(date);
        HashTrucks.get(truck_license_number).addDate(date);
        for (Integer id: id_to_delete)
        {
            HashMissingItems.remove(id);
        }
        return true;
    }

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
        for (MissingItems missingItems: MissingItems)
        {
            output = output + gson.toJson(missingItems)+"\n";
        }
        return output;
    }

    public String getAeras()
    {
        String output = "[ ";
        for (Area area: area_list)
        {
            output = output + area.toString() +", ";
        }
        output = output + "]";
        return output;
    }

    //print list of all the suppliers






  /*  public String get_Store_id(String site){
        String output = "";
        for (Site sites: HashSites.values())
        {
            if(sites.getName().equals(site)) {
                output = Integer.toString(sites.getId());
            }
        }
        return output;
    } */

 /*   public String getFreeDrivers(Date date)
    {
        String output = "";
        for (Drivers driver: HashDrivers.values())
        {
            if(driver.checkIfFree(date))
            {
                output = output + driver.getId()+". "+driver.getName()+"\n";
            }
        }
        return output;
    }

    public String getTrucksToDriver(String id, Date date)
    {
        int driverId = Integer.parseInt(id);
        List<License> license_list = HashDrivers.get(driverId).getLicenses();
        String output = "";
        for (Trucks truck : HashTrucks.values())
        {
            if(truck.checkIfFree(date) && truck.checkLicense(license_list))
            {
                output = output + truck.getlicense_number()+". "+truck.getModel()+"\n";
            }
        }
        return output;
    }

    public boolean delete_Transport(String id)
    {
        int transport_id = Integer.parseInt(id);
        for(Map.Entry<Integer, Transportation> transport:HashTransportation.entrySet()){
            if (transport.getValue().getID()==transport_id){
                int driver=transport.getValue().getDriveId();
                int truck=transport.getValue().getTrucklicense();
                HashDrivers.get(driver).Remove_date(transport.getValue().getDate());
                HashTrucks.get(truck).Remove_date(transport.getValue().getDate());
                HashTransportation.remove(transport.getKey());
            }
        }
        return true;
    }

    public String getTransport_id(){
        String result="";
        for(Transportation transportation: HashTransportation.values()){
            result=result+transportation.getID()+" ,";
        }
        return result;
    } */



    public List<MissingItems> getMissing(){
        return MissingItems;
    }

    public ConcurrentHashMap<String,Site> getSitesMap()
    {
        return HashSites;
    }

    public ConcurrentHashMap<Integer,Transportation> getHashTransportation(){
        return HashTransportation;
    }

    public List<Drivers> getDrivers(){
        return Drivers;
    }

    public List<Area> getArea_list(){
        return area_list;
    }

}


