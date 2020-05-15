package Business_Layer.Transportations.Modules;
import Business_Layer.Workers.Modules.Worker.Driver;

import Business_Layer.Modules.Store;
import Business_Layer.Modules.Supplier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;


public class Transportation {


    private static int idcounter = 1;

    private int id;
    private Date date;
    private LocalTime leaving_time;
    private Truck truck;
    private List<Supplier> suppliers;
    private List<Store> stores;
    private double weight_truck = -1;
    private Driver driver;
    private List<ItemsFile> itemsFiles;

    public Transportation(Date date, LocalTime leaving_time, Driver drivers, Truck truck, List<Supplier> suppliers, List<Store> stores) {
        this.id = idcounter++;
        this.date = date;
        this.leaving_time = leaving_time;
        this.driver = drivers;
        this.truck = truck;
        this.suppliers = suppliers;
        this.stores = stores;
        this.itemsFiles = new LinkedList<>();
    }

    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String output = "";
        output += "id: " + this.id + "\n\tdate: " + dateFormat.format(this.date) + "\n\tleaving_time: " + this.leaving_time.toString()
                + "\n\tdriver: " + this.driver.getWorkerName() + "\n\ttruck- license_number:" + this.truck.getlicense_number()
                + ", Model:" + this.truck.getModel() + "\n";
        output += "\tstores: ";
        for (Store sites : stores) {

            output = output + sites.getName() + ", ";
        }
        output += "\n\tsuppliers: ";
        for (Supplier sites : suppliers) {
            output = output + sites.getName() + ", ";
        }
        output += "\n\titemsFiles: ";
        for (ItemsFile itemsFile: itemsFiles) {
            output = output +"\n\t"+ itemsFile.getSupplier().getName()+"->"+itemsFile.getStore().getName()+":";
            for (Map.Entry me : itemsFile.getItems_list().entrySet()) {
                output = output +"\n\t-"+ me.getKey()+"-"+me.getValue();
            }
        }
        if(weight_truck!=-1)
        {
            output+="\ntruck weight= "+weight_truck;
        }
        return output;

    }
    public void setDriver(Driver driver)
    {
        this.driver=driver;
    }
    public void setTruck(Truck truck)
    {
        this.truck=truck;
    }

    public void setWeight_truck(int weight_truck)
    {
        this.weight_truck=weight_truck;
    }

    public Date getDate() {
        return date;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public LocalTime getLeaving_time() {
        return leaving_time;
    }

    public Truck getTruck() {
        return truck;
    }


    public List<Store> getStores() {
        return stores;
    }

    public double getWeight_truck() {
        return weight_truck;
    }

    public Driver getDriver() {
        return driver;
    }

    public List<ItemsFile> getItemsFiles() {
        return itemsFiles;
    }

    public int getId() {
        return id;
    }

    public void addItemFile(ItemsFile file) {
        itemsFiles.add(file);
    }

    public int getDriveId() {
        return driver.getWorkerSn();
    }

    public int getTrucklicense() {
        return truck.getlicense_number();
    }
}
