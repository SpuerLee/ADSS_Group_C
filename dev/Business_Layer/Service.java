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

    private static class SingletonService {
        private static Service instance = new Service();
    }
    private Service() {
        // initialization code..
    }
    public static Service getInstance() {
        return SingletonService.instance;
    }

    private ConcurrentHashMap<Integer,Drivers> HashDrivers= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Sites> HashSites= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Trucks> HashTrucks= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,ItemsFile> HashItemsFile= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Transportation> HashTransportation= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,MissingItems> HashMissingItems= new ConcurrentHashMap<>();
    public List<String> license_list = new LinkedList<>();
    private List<String> area_list = new LinkedList<>();



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
                List<String> licenses=new LinkedList<>();
                for(int j=0;j<license.size();j++){
                    String type = license.get(j).getAsString();
                    licenses.add(type);
                }
                Drivers add=new Drivers(driver.get("name").getAsString(),licenses);
                HashDrivers.put(add.getId(),add);

            }
            final JsonArray sites = jsonObject.get("Sites").getAsJsonArray();
            for (int i = 0; i < sites.size(); i++) {
                final JsonObject site = sites.get(i).getAsJsonObject();
                HashSites.put(site.get("id").getAsInt(),new Sites(site.get("id").getAsInt(),site.get("site_type").getAsString(),site.get("name").getAsString(),site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt(),site.get("name_of_contact").getAsString(),site.get("phone").getAsString(),site.get("area").getAsString()));
                if(!area_list.contains(site.get("area").getAsString())){
                    area_list.add(site.get("area").getAsString());
                }
            }
            final JsonArray trucks = jsonObject.get("Trucks").getAsJsonArray();
            for (int i = 0; i < trucks.size(); i++) {
                final JsonObject truck = trucks.get(i).getAsJsonObject();
                final JsonArray license = truck.getAsJsonArray("licenses");
                List<String> licenses=new LinkedList<>();
                for(int j=0;j<license.size();j++){
                    String type = license.get(j).getAsString();
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
                HashMissingItems.put(items1.getID(),items1);
            }

        }
        catch (Exception e)
        {

        }
    }

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

    public String getMissingItemsStores()
    {
        List<Integer> id_stores_list = new LinkedList<>();
        String output = "";
        for (MissingItems missingItems: HashMissingItems.values())
        {
            Integer storeId=missingItems.getStoreId();
            if(!id_stores_list.contains(storeId))
            {
                id_stores_list.add(storeId);
                String store = HashSites.get(storeId).getName();
                output = output +storeId+". "+ store+"\n";
            }

        }
        return output;
    }

    public String getSupplierAreaByStore(String id)
    {
        int storeId = Integer.parseInt(id);
        List<String> area_list = new LinkedList<>();
        String output = "[ ";
        for (MissingItems missingItems: HashMissingItems.values())
        {
            if(storeId==missingItems.getStoreId())
            {
                int supplierId = missingItems.getSupplierId();
                String area = HashSites.get(supplierId).getArea();
                if(!area_list.contains(area))
                {
                    area_list.add(area);
                    output = output +area+", ";
                }
            }
        }
        output = output +"]";
        return output;
    }

    public String getSupplierByStoreArea(String id,String area)
    {
        int storeId = Integer.parseInt(id);
        String output = "";
        for (MissingItems missingItems: HashMissingItems.values())
        {
            int supplierId = missingItems.getSupplierId();
            String supplierArea = HashSites.get(supplierId).getArea();
            if(storeId==missingItems.getStoreId()&&area.equals(supplierArea))
            {
                output = output +supplierId+". "+ HashSites.get(supplierId).getName()+"\n";
            }
        }
        return output;
    }

    //print the area and the stores that are in the area
    public String getStoresByarea()
    {
        String result="";
        for(String area: area_list) {
            String output = "Area "+area+ ": [ ";
            for (Sites sites : HashSites.values()) {
                if ((sites.getType().equals("store")) & (sites.getArea().equals(area))) {
                    output = output + sites.getName() + " ,";
                }
            }
            output = output +"]\n";
            result=result+output;
        }
        return result;
    }

    public String getMissingItems()
    {
        Gson gson = new Gson();
        String output = "";
        for (MissingItems missingItems: HashMissingItems.values())
        {
            output = output + gson.toJson(missingItems)+"\n";
        }
        return output;
    }
    public String getAeras()
    {
        String output = "[ ";
        for (String area: area_list)
        {
            output = output + area+", ";
        }
        output = output + "]";
        return output;
    }

    //print list of all the suppliers

    public String getSuppliers()
    {
        List <Sites> sites=new LinkedList<>();
        String output = "";
        for (Sites site: HashSites.values())
        {
            if((site.getType().equals("supplier"))& (!sites.contains(site)))
            {
                output = output +"name: "+site.getName()+", id : "+ site.getId()+"\n";
                sites.add(site);
            }
        }
        return output;
    }


    //print store and id
    public String get_Stores_By_specific_area(String area){
        String output = "";
        for (Sites sites: HashSites.values())
        {
            if(sites.getArea().equals(area) && sites.getType().equals("store"))
            {
                output = output + sites.getName()+": "+ sites.getId()+"\n";
            }
        }
        return output;
    }

    public String get_Store_id(String site){
        String output = "";
        for (Sites sites: HashSites.values())
        {
            if(sites.getName().equals(site)) {
                output = Integer.toString(sites.getId());
            }
        }
        return output;
    }

    public String getFreeDrivers(Date date)
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
        List<String> license_list =HashDrivers.get(driverId).getLicenses();
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
    }

    public boolean addDriver(String name,List<String> license_list){
        Drivers drivers=new Drivers(name,license_list);
        HashDrivers.put(drivers.getId(),drivers);
        return true;
    }

    public boolean addTruck(String license_number, List<String> licenses_types, String model, String weight, String max_weight){
        Trucks trucks=new Trucks(Integer.parseInt(license_number),licenses_types,model,Double.parseDouble(weight),Double.parseDouble(max_weight));
        HashTrucks.put(Integer.parseInt(license_number),trucks);
        return true;
    }

    public boolean addsite(String site_type, String name, String city, String street, String number, String name_of_contact, String phone, String site_area){
        boolean result=true;
        for(Sites sites:HashSites.values()){
            if(sites.getName().equals(name)){
                result=false;
            }
        }
        if(result){
            Sites sites=new Sites(site_type,name,city,street,Integer.parseInt(number),name_of_contact,phone,site_area);
            HashSites.put(sites.getId(),sites);
        }
        return result;
    }

    public boolean removeDriver(String name){
        boolean result=false;
        for(Drivers drivers:HashDrivers.values()){
            if ((drivers.getName().equals(name))){
                result=true;
                int id=drivers.getId();
                if(drivers.getDo_transportation_days().size()>0)
                    result=false;
                if(result)
                    HashDrivers.remove(drivers.getId());
            }
        }
        return result;
    }

    public boolean removeTruck(String id){
        boolean result=false;
        Integer number=Integer.parseInt(id);
        for(Trucks trucks:HashTrucks.values()){
            if ((trucks.getlicense_number().equals(number))){
                result=true;
                if(trucks.getDo_transportation_days().size()>0)
                    result=false;
                if(result)
                    HashTrucks.remove(trucks.getlicense_number());
            }
        }
        return result;
    }

    public boolean removeSite(String name){
        boolean result=false;
        for(Sites sites:HashSites.values()){
            if ((sites.getName().equals(name))){
                result=true;
                for(Transportation transportation:HashTransportation.values()){
                    if ((transportation.getStores().contains((sites.getId()))||(transportation.getSuppliers().contains(sites.getId())))){
                        result=false;
                    }
                }
                if(result){
                    HashSites.remove(sites.getId());
                }
            }
        }
        return result;
    }
    public String showDrivers(){
        String result="";
        for(Drivers drivers: HashDrivers.values()){
            result=result+"id :"+drivers.getId()+" , Name:"+drivers.getName()+"\n";
        }
        return result;
    }

    public String showsite(){
        String result="";
        for(Sites sites: HashSites.values()){
            result=result+"id :"+sites.getId()+" , Name :"+sites.getName()+" ,Type :"+sites.getType()+"\n";
        }
        return result;
    }

    public String showtrucks(){
        String result="";
        for(Trucks trucks: HashTrucks.values()){
            result=result+"License Number: "+trucks.getlicense_number()+" , Model: "+trucks.getModel()+"\n";
        }
        return result;
    }
}


