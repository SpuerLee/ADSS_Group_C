package Business_Layer;

import Presentation_Layer.Controler;

import com.google.gson.stream.JsonReader;
import com.google.gson.*;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    private HashMap<Integer,Drivers> HashDrivers= new HashMap<>();
    private HashMap<Integer,Sites> HashSites= new HashMap<>();
    private HashMap<Integer,Trucks> HashTrucks= new HashMap<>();
    public HashMap<Integer,ItemsFile> HashItemsFile= new HashMap<>();
    public HashMap<Integer,Transportation> HashTransportation= new HashMap<>();
    public HashMap<Integer,MissingItems> HashMissingItems= new HashMap<>();
    public List<String> license_list = new LinkedList<>();
    public List<String> area_list = new LinkedList<>();



    public void uploadData()
    {
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonReader reader = new JsonReader(new FileReader("../DataLayer/Data.json"));
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            final JsonArray drivers = jsonObject.get("Drivers").getAsJsonArray();
            for (int i = 0; i < drivers.size(); i++) {
                final JsonObject driver = drivers.get(i).getAsJsonObject();
                final JsonArray license = driver.getAsJsonArray("licenses");
                List<String> licenses=new LinkedList<>();
                for(int j=0;j<license.size();j++){
                    String type = license.get(i).getAsString();
                    licenses.add(type);
                }
                HashDrivers.put(driver.get("id").getAsInt(),new Drivers(driver.get("id").getAsInt(),driver.get("name").getAsString(),licenses));

            }
            final JsonArray sites = jsonObject.get("Sites").getAsJsonArray();
            for (int i = 0; i < sites.size(); i++) {
                final JsonObject site = sites.get(i).getAsJsonObject();
                HashSites.put(site.get("id").getAsInt(),new Sites(site.get("id").getAsInt(),site.get("type").getAsString(),site.get("name").getAsString(),site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt(),site.get("name_of_contact").getAsString(),site.get("phone").getAsString(),site.get("area").getAsString()));
            }
            final JsonArray trucks = jsonObject.get("Trucks").getAsJsonArray();
            for (int i = 0; i < trucks.size(); i++) {
                final JsonObject truck = trucks.get(i).getAsJsonObject();
                final JsonArray license = truck.getAsJsonArray("licenses");
                List<String> licenses=new LinkedList<>();
                for(int j=0;j<license.size();j++){
                    String type = license.get(i).getAsString();
                    licenses.add(type);
                }
                HashTrucks.put(truck.get("license_number").getAsInt(),new Trucks(truck.get("license_number").getAsInt(),licenses,truck.get("model").getAsString(),truck.get("weight").getAsDouble(),truck.get("weight").getAsDouble()));
            }
            final JsonArray missing_items = jsonObject.get("Missing_Items").getAsJsonArray();
            for (int i = 0; i < missing_items.size(); i++) {
                final JsonObject missing = missing_items.get(i).getAsJsonObject();
                final JsonArray items = missing.getAsJsonArray("items_list");
                HashMap<String,Integer> map=new HashMap<>();
                for(int j=0;j<items.size();j++){
                    String [] items_to_add=items.get(i).getAsString().split(":");
                    map.put(items_to_add[1],Integer.parseInt(items_to_add[0]));
                }
                MissingItems items1=new MissingItems(missing.get("source_site_id").getAsInt(),missing.get("destination_site_id").getAsInt(),map);
                HashMissingItems.put(items1.getID(),items1);
            }

        }
        catch (Exception e)
        {

        }
    }

    //missing items
    public boolean createTransportation(LocalDate date, LocalTime leaving_time, int driver_id,
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
                ItemsFile itemFile = new ItemsFile(transportation.getID(),missingItems.getSupplierId(),
                        missingItems.getStoreId(),missingItems.getItems_list());
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
    public boolean createRegularTransportation(LocalDate date, LocalTime leaving_time, int driver_id,
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
    public void add_to_items_file(LocalDate date, LocalTime leaving_time, int driver_id,
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
            if(storeId==missingItems.getStoreId()&&area==supplierArea)
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
            String output = area+ ": [ ";
            for (Sites sites : HashSites.values()) {
                if ((sites.getType().equals("supplier")) & (sites.getArea().equals(area))) {
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
                output = output + site.getName()+"\n";
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
            if(sites.getName().equals(sites)) {
                output = Integer.toString(sites.getId());
            }
        }
        return output;
    }

    public String getFreeDrivers(LocalDate date)
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

    public String getTrucksToDriver(String id, LocalDate date)
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



}


