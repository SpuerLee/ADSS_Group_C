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

}
