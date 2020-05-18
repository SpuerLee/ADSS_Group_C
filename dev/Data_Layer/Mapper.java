package Data_Layer;

import Data_Layer.DAOs.*;
import Data_Layer.Dummy_objects.*;
import javafx.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


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

    public void insertWorker(int id, String name, String phoneNumber, int bankAccount, int salary, Date date, String jobTitle, int Sn, int storeSN, HashMap<Pair<Integer, Integer>,Boolean> constraints){
        dummy_Worker toAdd = new dummy_Worker(Sn,id,name,phoneNumber,bankAccount,salary,storeSN,date,jobTitle,constraints);
        worker_Mapper.insert(toAdd);
    }

    public void insertDriver(int id, String name, String phoneNumber, int bankAccount, int salary, Date date, String jobTitle, int Sn,int storeSN, HashMap<Pair<Integer, Integer>,Boolean> constraints, List<Integer> licenses){
        dummy_Worker toAdd = new dummy_Worker(Sn,id,name,phoneNumber,bankAccount,salary,storeSN,date,jobTitle,constraints);
        worker_Mapper.insert(toAdd);
        for(int x : licenses){
            worker_Mapper.insertLicense(toAdd.getSN(),x);
        }
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


    public void insertShift(Date date, int shiftType, int manager, List<Integer> workers,int SN, int Branch){
        dummy_Shift toAdd = new dummy_Shift(date,shiftType,manager,workers,SN,Branch);
        shift_Mapper.insert(toAdd);
    }


    //Address
    public void insertAddress(int Sn,String city, String street, int number){
        dummy_Address toAdd = new dummy_Address(Sn,city, street, number);
        address_Mapper.insert(toAdd);
    }

    public void deleteAddress(int SN){
        address_Mapper.delete(SN);
    }

    public Integer getNextSNAddress(){
        return address_Mapper.getNextSN();
    }

    public List<dummy_Address> selectAllAddress(){
        return address_Mapper.selectAll();
    }

    public dummy_Address selectAddress(int SN){
        return address_Mapper.select(SN);
    }

    //Area
    public List<dummy_Area> selectAllArea(){
        return area_Mapper.selectAll();
    }

    //Supplier
    public void insertSupplier(int SN,String name, String Phone, String ContactName, int AddressSN, int AreaSN ,String city, String street, int number){
        insertAddress(AddressSN,city, street, number);
        dummy_supplier toAdd = new dummy_supplier(SN, name, Phone, ContactName, AddressSN, AreaSN, city, street, number);
         supplier_Mapper.insert(toAdd);
    }

    public List<dummy_supplier> selectAllSuppliers(){
        List<dummy_supplier> output = supplier_Mapper.selectAll();
        for (dummy_supplier s : output)
        {
            s.setDummy_address(selectAddress(s.getAddress_Sn()));

        }
        return output;
    }

    public dummy_supplier selectSupplier(int SN){
        dummy_supplier supplier = supplier_Mapper.select(SN);
        if(supplier!=null)
            supplier.setDummy_address(selectAddress(supplier.getAddress_Sn()));
        return supplier;
    }

    //Truck
    public void insertTruck(int SN,int license_number, String model, double weight, double max_weight, List<Integer> license_type){
        dummy_Truck toAdd = new dummy_Truck(SN,license_number, model, weight, max_weight, license_type);
        truck_Mapper.insert(toAdd);
    }

    public void deleteTruck(int SN){
        truck_Mapper.delete(SN);
    }

    //Transportation
    public void insertTransportation(int Sn, Date date, int leaving_time, double truck_weight,
                                     int trucksn, int Driver, List<Integer> suppliers,
                                     List<Integer> stores, List<Integer> itemsFile){
        dummy_Transportation toAdd = new dummy_Transportation( Sn,date, leaving_time, truck_weight,
                trucksn,Driver,suppliers, stores,itemsFile);
        transportation_Mapper.insert(toAdd);
    }


    //Itemfile
    public void insertItemfile(int Sn, int supplier_id,int store_id, List<Pair<String,Integer>> items){
        dummy_Items_File toAdd = new dummy_Items_File(Sn,supplier_id,store_id, items);
        itemFile_Mapper.insert(toAdd);
    }

    //MissingItem
    public void insertMissingItem(int id, int store_id, int supplier_id,List<Pair<String,Integer>> missing){
        dummy_Missing_items toAdd = new dummy_Missing_items(id,store_id,supplier_id,missing);
        missingItems_Mapper.insert(toAdd);
    }

    public List<dummy_Missing_items> selectAllMissing_items(){
        return missingItems_Mapper.selectAll();
    }

    //store
    public void insertStore(String phone, String contact_name, String name, int id, String city, String street, int number, int Adrress_Sn, int AreaSn){
        dummy_store toAdd = new dummy_store(phone,contact_name, name, id,city,street,number,Adrress_Sn,AreaSn);
        store_Mapper.insert(toAdd);
    }

    public dummy_store selectStore(int SN){
        dummy_store store = store_Mapper.select_by_storeId(SN);
        return store;
    }



}
