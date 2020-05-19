package Data_Layer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Business_Layer.Modules.Address;
import Business_Layer.Modules.License;
import Business_Layer.Modules.Store;
import Business_Layer.Service;
import Business_Layer.Workers.Modules.Shift;
import Business_Layer.Workers.Modules.Worker.Driver;
import Business_Layer.Workers.Modules.Worker.Worker;
import Business_Layer.Workers.Utils.enums;
import Data_Layer.DAOs.*;
import Data_Layer.Dummy_objects.*;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import javafx.util.Pair;


public class Mapper {
    private static Mapper instance;
    private worker_DAO worker_Mapper;
    private address_DAO address_Mapper;
    private area_DAO area_Mapper;
    private item_file_DAO itemFile_Mapper;
    private missing_items_DAO missingItems_Mapper;
    private shift_DAO shift_Mapper;
    private store_DAO store_Mapper;
    private supplier_DAO supplier_Mapper;
    private transportation_DAO transportation_Mapper;
    private truck_DAO truck_Mapper;

    public static Mapper getInstance() {
        if(instance==null){
            instance = new Mapper();
        }
        return instance;
    }

    public Mapper() {
        worker_Mapper = new worker_DAO();
        address_Mapper = new address_DAO();
        area_Mapper = new area_DAO();
        itemFile_Mapper = new item_file_DAO();
        missingItems_Mapper = new missing_items_DAO();
        shift_Mapper = new shift_DAO();
        store_Mapper = new store_DAO();
        supplier_Mapper = new supplier_DAO();
        transportation_Mapper = new transportation_DAO();
        truck_Mapper = new truck_DAO();
    }

    public void insertWorker(int id, String name, String phoneNumber, int bankAccount, int salary, Date date, String jobTitle, int Sn, int storeSN){
        dummy_Worker toAdd = new dummy_Worker(Sn,id,name,phoneNumber,bankAccount,salary,storeSN,date,jobTitle);
        worker_Mapper.insert(toAdd);
    }

    public void insertDriver(int id, String name, String phoneNumber, int bankAccount, int salary, Date date, String jobTitle, int Sn,int storeSN){
        dummy_Worker toAdd = new dummy_Worker(Sn,id,name,phoneNumber,bankAccount,salary,storeSN,date,jobTitle);
        worker_Mapper.insert(toAdd);
/*        for(int x : licenses){
            worker_Mapper.insertLicense(toAdd.getSN(),x);
        }*/
    }
    public void deleteConstraints(int workerSN){
        worker_Mapper.deleteConstraints(workerSN);
    }
    public void updateSalary(int sn,int salary){
        worker_Mapper.updateSalary(sn,salary);
    }

    public void addConstraints(int workerSn,int selectedDay,int sType){
        worker_Mapper.addConstraints(workerSn, selectedDay,sType);
    }

    public void insertLicense(int driverSN,int license){
        worker_Mapper.insertLicense(driverSN,license);
    }
    public void insert_Shift_Workers(int workerSn,int shiftSN){
        shift_Mapper.insert_Shift_Workers(workerSn,shiftSN);

    }



    public void insertShift(Date date, int shiftType, int manager,int SN, int Branch){
        dummy_Shift toAdd = new dummy_Shift(date,shiftType,manager,SN,Branch);
        shift_Mapper.insert(toAdd);
    }

    public void insertMissingItem(int id, int store_id, int supplier_id,List<Pair<String,Integer>> missing){
        dummy_Missing_items toAdd = new dummy_Missing_items(id,store_id,supplier_id,missing);
        missingItems_Mapper.insert(toAdd);
    }

    public void insertItemfile(int Sn, int supplier_id,int store_id, List<Pair<String,Integer>> items){
        dummy_Items_File toAdd = new dummy_Items_File(Sn,supplier_id,store_id, items);
        itemFile_Mapper.insert(toAdd);
    }

    public void insertAddress(String city, String street, int number,int SN){
        dummy_Address toAdd = new dummy_Address(city, street, number,SN);
       // address_Mapper.insert(toAdd);
    }

    public void insertTransportation(Date date, String leaving_time, double truck_weight, int trucksn, List<Integer> itemsFile, List<Integer> suppliers, List<Integer> stores, int Driver){
        enums leaving=enums.NIGHT;
        if(leaving_time.toUpperCase().equals("MORNING"))
            leaving=enums.MORNING;
        dummy_Transportation toAdd = new dummy_Transportation( date, leaving, truck_weight,trucksn,itemsFile,suppliers, stores,Driver);
        transportation_Mapper.insert(toAdd);
    }

    public void insertTruck(int Id,int license_number, String model, double weight, double max_weight, String license_type){
        dummy_Truck toAdd = new dummy_Truck(Id,license_number, model, weight, max_weight, license_type);
        truck_Mapper.insert(toAdd);
    }

    public void insertStore(String phone, String contact_name, String name, int id, String city, String street, int number, int Adrress_Sn, int AreaSn){
        dummy_store toAdd = new dummy_store(phone,contact_name, name, id,city,street,number,Adrress_Sn,AreaSn);
        store_Mapper.insert(toAdd);
    }

    public void insertSupplier(String phone, String contact_name, String name, int id, String city, String street, int number, int Adrress_Sn, int AreaSn){
        dummy_supplier toAdd = new dummy_supplier(phone,contact_name,name, id,city, street, number, Adrress_Sn, AreaSn);
       // supplier_Mapper.insert(toAdd);
    }

    public Address selectAddressBySN(int Address){
        dummy_Address address = address_Mapper.select(Address);
        return new Address(address.getCity(),address.getStreet(),address.getNumber(),address.getSN());
    }

    public void getAllStores(){
       List<dummy_store> dummy_Stores = store_Mapper.select();
       for(dummy_store store: dummy_Stores){
           Address address = selectAddressBySN(store.getAddress_Sn());
           Store toADD = new Store(store.getName(),store.getPhone(),store.getContact_name(),address, Service.getInstance().getArea_list().get(store.getAreaSn()),store.getId());
           Service.getInstance().getHashStoresMap().putIfAbsent(toADD.getId(),toADD);
       }
    }

    private enums shiftTypeToEnum(int shiftType) {
        String DBshiftType="";
        switch (shiftType) {
            case 1:
                DBshiftType = "MORNING";
                break;

            case 2:
                DBshiftType = "NIGHT";
                break;
        }
        return enums.valueOf(DBshiftType);
    }

    private enums  dayToEnum(Integer day) {

        String DBday="";
        switch (day){
            case 1:
                DBday ="SUNDAY";
                break;

            case 2:
                DBday ="MONDAY";
                break;

            case 3:
                DBday ="TUESDAY";
                break;

            case 4:
                DBday ="WEDNESDAY";
                break;

            case 5:
                DBday ="THURSDAY";
                break;

            case 6:
                DBday ="FRIDAY";
                break;

            case 7:
                DBday ="SATURDAY";
                break;

        }

        return enums.valueOf(DBday);
    }

/*
    public String findJobTitle(int worker_type){
        switch (worker_type){
            case 1:
                return cash
        }
    }
*/

    public Worker getWorker(int StoreSN, int workerSN){
        Worker worker = null;
        if(Service.getInstance().getWorkerList(StoreSN).containsKey(workerSN)){ //worker already exists
            return Service.getInstance().getWorkerList(StoreSN).get(workerSN);
        } //
        dummy_Worker toADD = worker_Mapper.selectWorkerBySN(workerSN); //get the worker from db
        worker = new Worker(toADD.getId(),toADD.getName(),toADD.getPhone(),toADD.getBankAccount(),toADD.getSalary(),
                toADD.getStart_Date(),toADD.getJob_title(),workerSN,StoreSN);
        if( toADD.getJob_title().toUpperCase().equals("DRIVER")){ //if driver add licenses
            String newLicense = "";
            List<Integer> licenses = worker_Mapper.selectDriverLicenseByWorkerSN(workerSN);
            if(licenses.contains(1)){
                newLicense = "C";
                if(licenses.contains(2)){
                    newLicense = "C,C1";
                }
            }else if(licenses.contains(2)){
                newLicense="C1";
            }
            worker = new Driver(toADD.getId(),toADD.getName(),toADD.getPhone(),toADD.getBankAccount(),toADD.getSalary(),
                    toADD.getStart_Date(),toADD.getJob_title(),workerSN,StoreSN,newLicense);
        } //add constraints
        List<Pair<Integer,Integer>> constraints = worker_Mapper.selectConstrainsByWorkerSN(workerSN);
        for(Pair x: constraints){
            worker.addConstrainsToWorker(dayToEnum((int)x.getKey()),shiftTypeToEnum((int)x.getValue()));
        }
        Service.getInstance().getWorkerList().putIfAbsent(workerSN,worker);
        return worker;
    }

    public List<Worker> getShiftsWorker(int StoreSN,List<Integer> workers){
        List<Worker> workers1 = new LinkedList<>();
        for(int worker: workers){
            workers1.add(getWorker(StoreSN,worker));
        }
        return workers1;
    }


    public void getAllShifts(int storeSn){
        List<dummy_Shift> dummy_Shifts = shift_Mapper.selectShiftByStoreSN(storeSn);
        for(dummy_Shift shift: dummy_Shifts){
//            Date shiftDate = shift.getDate();
//            enums shiftType = shiftTypeToEnum(shift.getShift_type());
//            Worker manager = getWorker(storeSn,shift.getManager());
//            List<Worker> listOfWorkers = getShiftsWorker(storeSn,shift.getShift_workers());
//            int shiftSN = shift.getSn();
//            int storeSN = storeSn;

            Shift shift1 = new Shift(shift.getDate(),shiftTypeToEnum(shift.getShift_type()),getWorker(storeSn,shift.getManager()),
                    getShiftsWorker(storeSn,shift.getShift_workers()),shift.getSn(),storeSn);
            Service.getInstance().getShiftHistory().putIfAbsent(shift1.getShiftSn(),shift1);
        }
    }

    public void deleteWorker( int workerSn){
        worker_Mapper.deleteEmployee(workerSn);
    }
    public void deleteManager( int workerSn){
        worker_Mapper.deleteManager(workerSn);
    }
    // area license shifttype
    public void init(){
        area_Mapper.insert(1,"A");
        area_Mapper.insert(2,"B");
        area_Mapper.insert(3,"C");
        area_Mapper.insert(4,"D");
        worker_Mapper.initLicense();
        worker_Mapper.initShiftType();
    }

    public void getAllWorkersByStore(int StoreSN) {
        List<dummy_Worker> workers = worker_Mapper.selectWorkersByStoreSN(StoreSN);
        for(dummy_Worker worker: workers){
            Worker worker1 = getWorker(StoreSN,worker.getSN());
            Service.getInstance().getWorkerList().putIfAbsent(worker1.getWorkerSn(),worker1);
        }
    }
    //in area license shift_type

    public void clearDB() {
        //DELETE EVERYTHING
        shift_Mapper.deleteAllShift_Worker();
        worker_Mapper.deleteAllDriverLicense();
        worker_Mapper.deleteAllConstraints();
        worker_Mapper.deleteAllWorkers();
        shift_Mapper.deleteAllShifts();
        store_Mapper.deleteAllStores();
        address_Mapper.deleteAll();
        area_Mapper.deleteAll();
        worker_Mapper.deleteLicenseType();
        worker_Mapper.deleteShiftType();
    }
}
