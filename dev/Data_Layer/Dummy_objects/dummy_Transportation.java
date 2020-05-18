package Data_Layer.Dummy_objects;

import java.util.Date;
import java.util.List;

public class dummy_Transportation {

    private int Id;
    private Date date;
    private int leaving_time;
    private double truck_weight;
    private int trucksn;
    private List<Integer> itemsFile;
    private List<Integer> suppliers;
    private List<Integer> stores;
    private int DriverSn;


    public dummy_Transportation(int Id,Date date, int leaving_time, double truck_weight,int trucksn, List<Integer> itemsFile, List<Integer> suppliers, List<Integer> stores, int Driver){
        this.Id=Id;
        this.date=date;
        this.leaving_time=leaving_time;
        this.truck_weight=truck_weight;
        this.trucksn=trucksn;
        this.itemsFile=itemsFile;
        this.suppliers=suppliers;
        this.stores=stores;
        this.DriverSn=Driver;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLeaving_time() {
        return leaving_time;
    }

    public void setLeaving_time(int leaving_time) {
        this.leaving_time = leaving_time;
    }

    public double getTruck_weight() {
        return truck_weight;
    }

    public void setTruck_weight(double truck_weight) {
        this.truck_weight = truck_weight;
    }

    public List<Integer> getItemsFile() {
        return itemsFile;
    }

    public void setItemsFile(List<Integer> itemsFile) {
        this.itemsFile = itemsFile;
    }

    public int getId(){
        return this.Id;
    }
    public void setId(int id) {
        Id = id;
    }

    public int getTrucksn() {
        return trucksn;
    }

    public void setTrucksn(int trucksn) {
        this.trucksn = trucksn;
    }

    public List<Integer> getStores() {
        return stores;
    }

    public void setStores(List<Integer> stores) {
        this.stores = stores;
    }

    public List<Integer> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Integer> suppliers) {
        this.suppliers = suppliers;
    }

    public int getDriverSn() {
        return DriverSn;
    }

    public void setDriverSn(int driverSn) {
        DriverSn = driverSn;
    }
}
