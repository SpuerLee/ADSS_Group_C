package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;


public class Transportation {

    public static HashMap<Integer,Transportation> HashTransportation= new HashMap<>();

    private int id;
    private LocalDate date;
    private LocalTime leaving_time;
    private int license;
    private int driver_id;
    private List<HashMap> suppliers;
    private List<HashMap> stores;

    public Transportation(int id, LocalDate date, LocalTime leaving_time, int license, int driver_id, List<HashMap> suppliers, List<HashMap> stores)
    {
        this.id=id;
        this.date=date;
        this.leaving_time=leaving_time;
        this.license=license;
        this.driver_id=driver_id;
        this.suppliers=suppliers;
        this.stores=stores;
    }

    private void addTransportTransportation(int id, LocalDate date, LocalTime leaving_time, int license, int driver_id, List<HashMap> suppliers, List<HashMap> stores)
    {
        HashTransportation.put(id,new Transportation(id,date,leaving_time,license,driver_id,suppliers,stores));
    }

    private void removeTransport(int id)
    {
        HashTransportation.remove(id);
    }

}
