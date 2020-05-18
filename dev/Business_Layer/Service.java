package Business_Layer;

import Business_Layer.Controllers.Site_Controller;
import Business_Layer.Modules.*;
import Business_Layer.Transportations.Controllers.Drivers_Controller;
import Business_Layer.Transportations.Controllers.Missing_items_Controller;
import Business_Layer.Transportations.Controllers.Transportation_Controller;
import Business_Layer.Transportations.Controllers.Trucks_Controller;
import Business_Layer.Transportations.Modules.*;
import Business_Layer.Transportations.Utils.Buisness_Exception;
import Business_Layer.Workers.Controllers.ShiftController;
import Business_Layer.Workers.Controllers.WorkerController;
import Business_Layer.Workers.Modules.Shift;
import Business_Layer.Workers.Modules.Worker.Driver;
import Business_Layer.Workers.Modules.Worker.Worker;
import Business_Layer.Workers.Utils.ShiftType;
import Data_Layer.Mapper;
import Interface_Layer.Workers.SystemInterfaceWorkers;
import javafx.util.Pair;
import com.google.gson.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Service {

    public ConcurrentHashMap<Integer, Truck> getHashTrucks() {
        return HashTrucks;
    }

    public WorkerController getWorkerController() {
        return this.workerController;
    }

    public ShiftController getShiftController() {
        return this.shiftController;
    }

    private static class SingletonService {

        private static Service instance = new Service();
    }

    private Service() {
        // initialization code..
        license_list.put(1, new License(1,"C"));
        license_list.put(2,new License(2,"C1"));
        area_list.put(1,new Area(1,"A"));
        area_list.put(2,new Area(2,"B"));
        area_list.put(3,new Area(3,"C"));
        area_list.put(4,new Area(4,"D"));
        shiftTypeList.put(1, new ShiftType(1,"MORNING"));
        shiftTypeList.put(2, new ShiftType(2,"NIGHT"));

    }


    public Area getAreaByName(String str) throws Buisness_Exception
    {
        for (Map.Entry<Integer, Area> area : area_list.entrySet()) {
            if(str.equals(area.getValue().getAreaName()))
                 return area.getValue();
        }
        throw new Buisness_Exception("-Area dont exist-");
    }

    public License getLicenseByName(String str) throws Buisness_Exception
    {
        for (Map.Entry<Integer, License> license : license_list.entrySet()) {
            if(str.equals(license.getValue().getLicenseType()))
                 return license.getValue();
        }
        throw new Buisness_Exception("-License dont exist-");
    }


    public static Service getInstance() {
        return SingletonService.instance;
    }

    public Trucks_Controller trucks_controller = Trucks_Controller.getInstance();

    public Site_Controller getSite_controller() {
        return site_controller;
    }

    public Site_Controller site_controller = Site_Controller.getInstance();
    public Transportation_Controller transportation_controller = Transportation_Controller.getInstance();
    public Missing_items_Controller missing_items_controller = Missing_items_Controller.getInstance();
    public Drivers_Controller drivers_controller = Drivers_Controller.getInstance();
    private ShiftController shiftController = new ShiftController();
    private WorkerController workerController = new WorkerController();

    private ConcurrentHashMap<Integer, Driver> Drivers= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Supplier> HashSuppliers= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Store> HashStore= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Truck> HashTrucks= new ConcurrentHashMap<>();
    private List<Business_Layer.Transportations.Modules.ItemsFile> ItemsFile= new LinkedList<>();
    private ConcurrentHashMap<Integer,Transportation> HashTransportation= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,MissingItems> MissingItems= new ConcurrentHashMap<>();
    private HashMap<Integer, Shift> shiftHistory = new HashMap<>();
    private HashMap<Integer, Worker> workerList= new HashMap<>();
    public HashMap<Integer, License> license_list = new HashMap<>();
    private HashMap<Integer, Area> area_list = new HashMap<>();
    private HashMap<Integer, ShiftType> shiftTypeList = new HashMap<>();

    public HashMap<Integer, ShiftType> getshiftTypeList() {
        return shiftTypeList;
    }

    public HashMap<Integer, Area> getArea_list() {
        return area_list;
    }

    public HashMap<Integer, License> getLicense_list() {
        return license_list;
    }

    public HashMap<Integer, Worker> getWorkerList() {
        return workerList;
    }

    public HashMap<Integer, Shift> getShiftHistory() {
        return shiftHistory;
    }

    public HashMap<Integer, Shift> getShiftHistory(int currentStoreSN) {
        HashMap<Integer,Shift> CurrentStoreShifts = new HashMap<>();
        for(Shift shift : this.shiftHistory.values()){
            if(shift.getStoreSN() == currentStoreSN){
                CurrentStoreShifts.put(shift.getShiftSn(),shift);
            }
        }
        return CurrentStoreShifts;
    }

    public HashMap<Integer, Worker> getWorkerList(int currentStoreSN) {
        HashMap<Integer,Worker> CurrentStoreWorkers = new HashMap<>();
        for(Worker worker : this.workerList.values()){
            if(worker.getStoreSN() == currentStoreSN){
                CurrentStoreWorkers.put(worker.getWorkerSn(),worker);
            }
        }
        return CurrentStoreWorkers;
    }

    public void uploadData()
    {
/*
        JsonParser parser = new JsonParser();
        try (InputStream is = this.getClass().getResourceAsStream("Data.json");
            Reader rd = new InputStreamReader(is, "UTF-8"); ) {
            Object obj = parser.parse(rd);
            Gson gson = new Gson();

            JsonObject jsonObject = (JsonObject) obj;
          //  JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            final JsonArray drivers = jsonObject.get("Drivers").getAsJsonArray();
            for (int i = 0; i < drivers.size(); i++) {
                final JsonObject driver = drivers.get(i).getAsJsonObject();
                Date date = new Date();                //
                Driver add = new Driver(123,driver.get("name").getAsString(),"321312",132,465,date,"Driver",100,1,driver.get("licenses").getAsString());
                Drivers.put(add.getWorkerSn(),add);

            }
            final JsonArray sites = jsonObject.get("Sites").getAsJsonArray();
            for (int i = 0; i < sites.size(); i++) {
                final JsonObject site = sites.get(i).getAsJsonObject();
                String type=site.get("site_type").getAsString();
                Area area= getAreaByName(site.get("area").getAsString());
                if(type.equals("store")){
                    Address address=new Address(site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt(),);
                    Store store=new Store(site.get("name").getAsString(),site.get("phone").getAsString(),site.get("name_of_contact").getAsString(),address,area);
                    HashStore.put(store.getId(),store);
                }
                else if(type.equals("supplier")){
                    Address address=new Address(site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt());
                    Supplier supplier=new Supplier(site.get("name").getAsString(),site.get("phone").getAsString(),site.get("name_of_contact").getAsString(),address,area);
                    HashSuppliers.put(supplier.getId(),supplier);
                   }
                }

            final JsonArray trucks = jsonObject.get("Trucks").getAsJsonArray();
            for (int i = 0; i < trucks.size(); i++) {
                final JsonObject truck = trucks.get(i).getAsJsonObject();
                final JsonArray license = truck.getAsJsonArray("licenses");
                List<License> licenses=new LinkedList<License>();
                for(int j=0;j<license.size();j++){
                    License type = getLicenseByName(license.get(j).getAsString());
                    licenses.add(type);
                }
                Truck truck1 = new Truck(truck.get("license_number").getAsInt(),licenses,truck.get("model").getAsString(),truck.get("weight").getAsDouble(),truck.get("max weight").getAsDouble());
                HashTrucks.put(truck1.getId(),truck1);
            }
            final JsonArray missing_items = jsonObject.get("Missing_Items").getAsJsonArray();
            for (int i = 0; i < missing_items.size(); i++) {
                final JsonObject missing = missing_items.get(i).getAsJsonObject();
                final JsonArray items = missing.getAsJsonArray("items_list");
                List<Pair<String,Integer>> items_list = new LinkedList<Pair<String,Integer>>();
//                HashMap<String,Integer> map=new HashMap<String,Integer>();
                for(int j=0;j<items.size();j++){
                    String [] items_to_add=items.get(j).getAsString().split(":");
                    Pair<String,Integer> pair = new Pair<>(items_to_add[1],Integer.parseInt(items_to_add[0]));
//                    map.put(items_to_add[1],Integer.parseInt(items_to_add[0]));
                }
                MissingItems items1=new MissingItems(missing.get("store_id").getAsInt(),missing.get("supplier_id").getAsInt(),items_list);
                MissingItems.put(items1.getID(),items1);
            }

        }
        catch (Exception e)
        {
            System.out.println(e);

        }*/
    }


    public List<ItemsFile> getItemsFile(){
        return ItemsFile;
    }

    //print list of all the suppliers

    public ConcurrentHashMap<Integer, Business_Layer.Transportations.Modules.MissingItems> getMissing(){
        return MissingItems;
    }

    public ConcurrentHashMap<Integer,Supplier> getSuppliersMap()
    {
        return HashSuppliers;
    }

    public ConcurrentHashMap<Integer, Store> getHashStoresMap()
    {
        return HashStore;
    }

    public ConcurrentHashMap<Integer,Transportation> getHashTransportation(){
        return HashTransportation;
    }

    public ConcurrentHashMap<Integer, Driver> getDrivers(){
        return Drivers;
    }

}


