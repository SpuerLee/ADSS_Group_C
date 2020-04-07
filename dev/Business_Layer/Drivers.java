package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class Drivers {

    public static HashMap<Integer,Drivers> HashDrivers= new HashMap<>();

    private int id;
    private String name;
    private List<License> licenses;

    public Drivers(int id, String name, List<License> licenses )
    {
        this.id=id;
        this.name=name;
        this.licenses=licenses;
    }

    private void create_Driver(int id, String name, List<License> licenses)
    {
        HashDrivers.put(id,new Drivers(id,name,licenses));
    }

    private void delete_Driver(int id, String name, List<License> licenses)
    {
        HashDrivers.remove(id);
    }


}
