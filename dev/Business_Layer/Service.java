package Business_Layer;

import Business_Layer.Controllers.Site_Controller;
import Business_Layer.Modules.*;
import Business_Layer.Transportations.Controllers.Drivers_Controller;
import Business_Layer.Transportations.Controllers.Missing_items_Controller;
import Business_Layer.Transportations.Controllers.Transportation_Controller;
import Business_Layer.Transportations.Controllers.Trucks_Controller;
import Business_Layer.Transportations.Modules.ItemsFile;
import Business_Layer.Transportations.Modules.MissingItems;
import Business_Layer.Transportations.Modules.Transportation;
import Business_Layer.Transportations.Modules.Truck;
import Business_Layer.Transportations.Utils.Buisness_Exception;
import Business_Layer.Workers.Controllers.ShiftController;
import Business_Layer.Workers.Controllers.WorkerController;
import Business_Layer.Workers.Modules.Shift;
import Business_Layer.Workers.Modules.Worker.Driver;
import Business_Layer.Workers.Modules.Worker.Worker;
import Business_Layer.Workers.Utils.ShiftType;
import Business_Layer.Workers.Utils.enums;
import Data_Layer.DAOs.shift_DAO;
import Data_Layer.DAOs.truck_DAO;
import Data_Layer.Dummy_objects.*;
import Data_Layer.Mapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Service {

    private static class SingletonService {
        private static Service instance = new Service();
    }

    truck_DAO truck_dao=new truck_DAO();

    private Service() {
        // initialization code..
      /*  license_list.put(1, new License(1,"C"));
        license_list.put(2,new License(2,"C1"));
        area_list.put(1,new Area(1,"A"));
        area_list.put(2,new Area(2,"B"));
        area_list.put(3,new Area(3,"C"));
        area_list.put(4,new Area(4,"D"));
        shiftTypeList.put(1, new ShiftType(1,"MORNING"));
        shiftTypeList.put(2, new ShiftType(2,"NIGHT")); */


    }


    public static Service getInstance() {
        return SingletonService.instance;
    }

    public Trucks_Controller trucks_controller = Trucks_Controller.getInstance();
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
    private ConcurrentHashMap<Integer,ItemsFile> HashItemsFile = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Transportation> HashTransportation= new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,MissingItems> MissingItems= new ConcurrentHashMap<>();
    private HashMap<Integer, Shift> shiftHistory = new HashMap<>();
    private HashMap<Integer, Worker> workerList= new HashMap<>();
    public HashMap<Integer, License> license_list = new HashMap<>();
    private HashMap<Integer, Area> area_list = new HashMap<>();
    private HashMap<Integer, ShiftType> shiftTypeList = new HashMap<>();

    private Mapper mapper = Mapper.getInstance();

    public void remove_MissingItem(int SN){
        if(MissingItems.containsKey(SN))
        {
            MissingItems.remove(SN);
            mapper.deleteMissing_items(SN);
        }
    }

    public void upload_MissingItems(){
        if(MissingItems.isEmpty())
        {
            List<dummy_Missing_items> list = mapper.selectAllMissing_items();
            for (dummy_Missing_items dummy_missing_item : list)
            {
                MissingItems missingItem = new MissingItems(dummy_missing_item.getSN(),dummy_missing_item.getStore_id(),
                        dummy_missing_item.getSupplier_id(), dummy_missing_item.getItems());

                upload_Supplier(missingItem.getSupplierId());
                upload_Store(dummy_missing_item.getStore_id());

                MissingItems.put(missingItem.getID(),missingItem);
            }
        }
    }

    public void upload_ItemFile(int SN){

        dummy_Items_File dummy_items_file = mapper.selectItemfile(SN);
        if (dummy_items_file!=null)
        {
            upload_Supplier(dummy_items_file.getSupplier_id());
            upload_Store(dummy_items_file.getStore_id());
            Store store = HashStore.get(dummy_items_file.getStore_id());
            Supplier supplier = HashSuppliers.get(dummy_items_file.getSupplier_id());
            ItemsFile itemsFile = new ItemsFile(dummy_items_file.getSn(),dummy_items_file.getItems(),
                    store,supplier);
            HashItemsFile.put(itemsFile.getId(),itemsFile);
        }
    }

    public void upload_shift_type(){
        shift_DAO shiftDAO=new shift_DAO();
        shiftDAO.insert_types();
        shiftTypeList.put(1, new ShiftType(1,"Morning"));

    }

    public void add_ItemFile(ItemsFile itemsFile){
        if(!HashItemsFile.containsKey(itemsFile.getId()))
        {
            HashItemsFile.put(itemsFile.getId(),itemsFile);
            mapper.insertItemfile(itemsFile.getId(),itemsFile.getSupplier().getId(),itemsFile.getStore().getId(),
                    itemsFile.getItems_list());
        }
    }

    public void set_ItemFile_idCouter(){
        ItemsFile.setIdCounter(mapper.getNextSNItemfile());
    }

    public void upload_Trucks() throws Buisness_Exception{
        List<dummy_Truck> list = mapper.selectAllTrucks();
        upload_license();
        for (dummy_Truck dummy_truck : list)
        {
            if(!HashTrucks.containsKey(dummy_truck.getSN()))
            {
                List<License> license_list = new LinkedList<>();
                for (Integer id :dummy_truck.getLicense_type())
                {
                    license_list.add(getLicenseByName(id.toString()));
                }
                Truck truck = new Truck(dummy_truck.getSN(),license_list,dummy_truck.getModel(),dummy_truck.getWeight(),
                        dummy_truck.getMax_weight());
                HashTrucks.put(truck.getId(),truck);


                for(Integer id : mapper.selectTransportationTrucks(dummy_truck.getSN()))
                {
                    upload_Transportation(id);
                    HashTrucks.get(truck.getId()).getTransportations().add(HashTransportation.get(id));
                }
            }
        }
    }

    public void upload_Store(Integer SN){
        if (!HashStore.containsKey(SN))
        {
            dummy_store dummy_store = mapper.selectStore(SN);
            if(dummy_store!=null)
            {
                HashStore.put(dummy_store.getId(),new Store(dummy_store.getId(),dummy_store.getName(),
                        dummy_store.getPhone(),dummy_store.getContact_name(),
                        new Address(dummy_store.getCity(),dummy_store.getStreet(),dummy_store.getNumber()),
                        area_list.get(dummy_store.getAreaSn())));
            }
        }

    }

    public void upload_Supplier(Integer SN){
        if (!HashSuppliers.containsKey(SN))
        {
            dummy_supplier supplier = mapper.selectSupplier(SN);
            if (supplier!=null)
            {
                Address address = new Address(supplier.getDummy_address().getCity(),supplier.getDummy_address().getStreet(),
                        supplier.getDummy_address().getNumber());
                HashSuppliers.put(supplier.getSN(),
                        new Supplier(supplier.getSN(),supplier.getName(),supplier.getPhone(),
                                supplier.getContactName(),address,area_list.get(supplier.getAreaSn())));
            }
        }
    }

    public void add_new_supplier(Supplier supplier){
        if(!HashSuppliers.containsKey(supplier.getId())) {
            HashSuppliers.put(supplier.getId(), supplier);
            mapper.insertSupplier(supplier.getId(),supplier.getName(),supplier.getPhone(),supplier.getContact_name(),supplier.getAddress().getId(),supplier.getArea().getAreaSN(),supplier.getAddress().getCity(),supplier.getAddress().getStreet(),supplier.getAddress().getNumber());
        }
    }

    public void set_supplier_idCouter(){
        Supplier.setIdCounter(mapper.getNextSNTransportation());
    }

    public void upload_All_Supplier(){
        List<dummy_supplier> dummy_suppliers=mapper.selectAllSuppliers();
        for(dummy_supplier supplier:dummy_suppliers) {
            if (!HashSuppliers.containsKey(supplier.getSN())) {
                    Address address = new Address(supplier.getDummy_address().getCity(), supplier.getDummy_address().getStreet(),
                            supplier.getDummy_address().getNumber());
                    HashSuppliers.put(supplier.getSN(),
                            new Supplier(supplier.getSN(), supplier.getName(), supplier.getPhone(),
                                    supplier.getContactName(), address, area_list.get(supplier.getAreaSn())));
            }
        }
    }

    public void upload_Driver(Integer SN){
        // TODO: upload Driver

    }
/*
    public void upload_all_Drivers(){ //TODO implement this
        List<dummy_Worker> dummy_workers=mapper.get_drivers();
    } */

    public void add_transport_driver(int Transport,int Driver){
        if((HashTransportation.containsKey(Transport)) && (Drivers.containsKey(Driver))){
            mapper.add_transport_driver(Transport,Driver);
        }
    }


    public void add_transport_Truck(int Transport,int truck){
        if((HashTransportation.containsKey(Transport)) && (HashTrucks.containsKey(truck))){
            mapper.add_transport_truck(Transport,truck);
        }
    }

    public void remove_driver_transportatin(int Transport,int driver){
        mapper.remove_driver_transportatin(Transport, driver);
    }

    public void remove_truck_transportatin(int Transport, int driver){
        mapper.remove_truck_transportatin(Transport, driver);
    }

    public void upload_Transportation(int SN) throws Buisness_Exception {
        if(!HashTransportation.containsKey(SN))
        {
            dummy_Transportation dummy_transportation = mapper.selectTransportation(SN);
            Driver driver = null;
            if(Drivers.containsKey(dummy_transportation.getDriverSn()))
                driver = Drivers.get(dummy_transportation.getDriverSn());

            Truck truck = null;
            if(HashTrucks.containsKey(dummy_transportation.getTrucksn()))
                truck = HashTrucks.get(dummy_transportation.getTrucksn());

            Transportation transportation = new Transportation(dummy_transportation.getId(),dummy_transportation.getDate(),
                    dummy_transportation.getLeaving_time(),driver,truck);

            for(Integer id : dummy_transportation.getStores())
            {
                upload_Store(id);
                transportation.getStores().add(HashStore.get(id));
            }
            for(Integer id : dummy_transportation.getSuppliers())
            {
                upload_Supplier(id);
                transportation.getSuppliers().add(HashSuppliers.get(id));
            }

            for(Integer id : dummy_transportation.getItemsFile())
            {
                upload_ItemFile(id);
                transportation.getItemsFiles().add(HashItemsFile.get(id));
            }


            // TODO: upload Stores
            // TODO: upload Supplier
            // TODO: upload ItemFile





        }



    }

    public void upload__all_Transportation() throws Buisness_Exception {

        List<dummy_Transportation> dummy_transportations = mapper.select_all_Transportation();
        for (dummy_Transportation dummy_transportation:dummy_transportations) {
            if (!HashTransportation.containsKey(dummy_transportation.getId())) {
                Driver driver = null;
                if (Drivers.containsKey(dummy_transportation.getDriverSn()))
                    driver = Drivers.get(dummy_transportation.getDriverSn());

                Truck truck = null;
                if (HashTrucks.containsKey(dummy_transportation.getTrucksn()))
                    truck = HashTrucks.get(dummy_transportation.getTrucksn());

                Transportation transportation = new Transportation(dummy_transportation.getId(), dummy_transportation.getDate(),
                        dummy_transportation.getLeaving_time(), driver, truck);

                for (Integer id : dummy_transportation.getStores()) {
                    upload_Store(id);
                    transportation.getStores().add(HashStore.get(id));
                }
                for (Integer id : dummy_transportation.getSuppliers()) {
                    upload_Supplier(id);
                    transportation.getSuppliers().add(HashSuppliers.get(id));
                }

                for (Integer id : dummy_transportation.getItemsFile()) {
                    upload_ItemFile(id);
                    transportation.getItemsFiles().add(HashItemsFile.get(id));
                }


                // TODO: upload Stores
                // TODO: upload Supplier
                // TODO: upload ItemFile
            }
        }
    }

    public void add_Transportation(Transportation transportation){
        if(!HashTransportation.containsKey(transportation.getId()))
        {
            HashTransportation.put(transportation.getId(),transportation);
            List<Integer> Suppliers = new LinkedList<>();
            for(Supplier s:transportation.getSuppliers())
            {
                Suppliers.add(s.getId());
            }
            List<Integer> Stores = new LinkedList<>();
            for(Store s:transportation.getStores())
            {
                Stores.add(s.getId());
            }
            List<Integer> ItemFiles = new LinkedList<>();
            for(ItemsFile i:transportation.getItemsFiles())
            {
                ItemFiles.add(i.getId());
            }
            mapper.insertTransportation(transportation.getId(),transportation.getDate(),transportation.getDepartureTime(),
                    transportation.getWeight_truck(),transportation.getTruck().getId(),transportation.getDriveId(),
                    Suppliers,Stores,ItemFiles);
        }

    }

    public void remove_transport(int sn){
        mapper.remove_transport(sn);
    }

    public void set_Transportation_idCouter(){
        Transportation.setIdCounter(mapper.getNextSNTransportation());
    }


    public void upload_Area(){
        if(area_list.isEmpty())
        {
            List<dummy_Area> list = mapper.selectAllArea();
            for (dummy_Area area : list)
            {
                area_list.put(area.getSN(),new Area(area.getSN(),area.getAreaName()));
            }
        }
    }

    public void getAllStores(){
        List<dummy_store> dummy_Stores = mapper.select_all_stores();
        for(dummy_store store: dummy_Stores){
            Address address = new Address(store.getCity(),store.getStreet(),
                    store.getNumber());
            Store toADD = new Store(store.getName(),store.getPhone(),store.getContact_name(),address, Service.getInstance().getArea_list().get(store.getAreaSn()));
            Service.getInstance().getHashStoresMap().putIfAbsent(toADD.getId(),toADD);
        }
    }


    //trucks

    public void upload_license(){
        if(license_list.isEmpty())
        {
            List<dummy_License> list = mapper.selectAllLicense();
            for (dummy_License l : list)
            {
                license_list.put(l.getSN(),new License(l.getSN(),l.getLicenseType()));
            }
        }
    }

    public void remove_truck(int sn) throws Buisness_Exception{
        if(!HashTrucks.containsKey(sn)){
            throw new Buisness_Exception("Truck doesn't exist");
        }
        else {
            mapper.deleteTruck(sn);
            for(Truck truck:HashTrucks.values()){
                if (truck.getId()==sn)
                    HashTrucks.remove(sn);
            }
        }
    }

    public void add_truck(int license_number, List<String> licenses_types,
                          String model, double weight, double max_weight){
        List<License> licenses=new LinkedList<>();
        for (String license : licenses_types) {
            try {
                licenses.add(getLicenseByName(license));
            } catch (Buisness_Exception e) {
                e.printStackTrace();
            }
        }
        List<Integer> to_add=new LinkedList<>();
        for (String license : licenses_types) {
            try {
                to_add.add(get_license_int(license));
            } catch (Buisness_Exception e) {
                e.printStackTrace();
            }
        }
        Truck trucks = new Truck(license_number, licenses, model, weight,max_weight);
        if(Truck.getIdCounter()==0)
        {
            set_truck_idCouter();
        }
        HashTrucks.put(trucks.getId(), trucks);
        mapper.insertTruck(trucks.getId(),license_number,model,weight,max_weight,to_add);
    }

    public void set_truck_idCouter(){
        Truck.setIdCounter(mapper.getNextSNTransportation());
    }

    public void set_address_idCounter(){
        Address.setIdCounter(mapper.getNextSNAddress());
    }


    public Area getAreaByName(String str) throws Buisness_Exception{
        for (Map.Entry<Integer, Area> area : area_list.entrySet()) {
            if(str.equals(area.getValue().getAreaName()))
                return area.getValue();
        }
        throw new Buisness_Exception("-Area dont exist-");
    }

    public License getLicenseByName(String str) throws Buisness_Exception{
        for (Map.Entry<Integer, License> license : license_list.entrySet()) {
            if(str.equals(license.getValue().getLicenseType()))
                return license.getValue();
        }
        throw new Buisness_Exception("-License dont exist-");
    }

    public Integer get_license_int(String str) throws Buisness_Exception{
        for (Map.Entry<Integer, License> license : license_list.entrySet()) {
            if(str.equals(license.getValue().getLicenseType()))
                return license.getKey();
        }
        throw new Buisness_Exception("-License dont exist-");
    }

    public ConcurrentHashMap<Integer, Truck> getHashTrucks() {
        return HashTrucks;
    }

    public WorkerController getWorkerController() {
        return this.workerController;
    }

    public ShiftController getShiftController() {
        return this.shiftController;
    }

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
        HashMap<Integer, Worker> CurrentStoreWorkers = new HashMap<>();
        for (Worker worker : this.workerList.values()) {
            if (worker.getStoreSN() == currentStoreSN) {
                CurrentStoreWorkers.put(worker.getWorkerSn(), worker);
            }
        }
        return CurrentStoreWorkers;
    }
    /*
    public void uploadData()
    {

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
                    Address address=new Address(site.get("city").getAsString(),site.get("street").getAsString(),site.get("number").getAsInt());
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

        }
    }
*/

    public ConcurrentHashMap<Integer,ItemsFile> getHashItemsFile(){
        return HashItemsFile;
    }

    //print list of all the suppliers

    public ConcurrentHashMap<Integer, MissingItems> getMissing(){
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


