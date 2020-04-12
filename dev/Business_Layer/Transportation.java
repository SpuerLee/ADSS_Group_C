package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Transportation {


    private static int idcounter=0;

    private int id;
    private Date date;
    private LocalTime leaving_time;
    private int driver_id;
    private int truck_license_number;
    private List<Integer> suppliers;
    private List<Integer> stores;
    private double weight_truck=-1;
    private boolean status;

    public Transportation(Date date, LocalTime leaving_time, int driver_id, int truck_license_number, List<Integer> suppliers, List<Integer> stores)
    {
        this.id=idcounter++;
        this.date=date;
        this.leaving_time=leaving_time;
        this.driver_id=driver_id;
        this.truck_license_number=truck_license_number;
        this.suppliers=suppliers;
        this.stores=stores;
        this.status = false;
    }

    public Integer getID()
    {
        return this.id;
    }
    public Integer getDriveId(){
        return driver_id;
    }
    public Integer getTrucklicense(){
        return truck_license_number;
    }

    public LocalTime getLeaving_time(){
        return leaving_time;
    }

    public List<Integer> getStores(){
        return stores;
    }
  /*
    private void addTransportTransportation(int id, LocalDate date, LocalTime leaving_time, int license, int driver_id, List<HashMap> suppliers, List<HashMap> stores)
    {
        HashTransportation.put(id,new Transportation(id,date,leaving_time,license,driver_id,suppliers,stores));
    }

    private void removeTransport(int id)
    {
        HashTransportation.remove(id);
    }

    public void setWeight_truck(double weight)
    {
        this.weight_truck=weight;
    }
 */
  public Date getDate(){
      return date;
  }

  public List<Integer> getSuppliers(){
      return suppliers;
  }

}
