package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Transportation {


    private static int idcounter=0;

   private int id;
    private Date date;
    private LocalTime leaving_time;
    private Trucks truck;
    private List<Supplier> suppliers;
    private List<Store> stores;
    private double weight_truck=-1;
    private Drivers driver;
    private List<ItemsFile> itemsFiles;

    public Transportation(Date date, LocalTime leaving_time, Drivers drivers, Trucks truck, List<Supplier> suppliers, List<Store> stores)
    {
        this.id=idcounter++;
        this.date=date;
        this.leaving_time=leaving_time;
        this.driver=drivers;
        this.truck=truck;
        this.suppliers=suppliers;
        this.stores=stores;
        this.itemsFiles=new LinkedList<>();
    }

  public Date getDate(){
      return date;
  }

  public List<Supplier> getSuppliers(){
      return suppliers;
  }

    public LocalTime getLeaving_time() {
        return leaving_time;
    }

    public Trucks getTruck() {
        return truck;
    }

    public void setTruck(Trucks truck) {
        this.truck = truck;
    }

    public List<Store> getStores() {
        return stores;
    }

    public double getWeight_truck() {
        return weight_truck;
    }

    public Drivers getDriver() {
        return driver;
    }

    public List<ItemsFile> getItemsFiles() {
        return itemsFiles;
    }

    public int getId() {
        return id;
    }

    public void addItemFile(ItemsFile file){
        itemsFiles.add(file);
    }

    public int getDriveId(){
        return driver.getId();
    }

    public int getTrucklicense(){
        return truck.getlicense_number();
    }
}
