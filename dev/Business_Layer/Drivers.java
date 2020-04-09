package Business_Layer;

import DataLayer.DriversHandler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Drivers {


    private int id;
    private String name;
    private List<type> licenses;
    public enum type{
        one,
        two
    };

    public Drivers(int id, String name, List<type> licenses)
    {
        this.id=id;
        this.name=name;
        this.licenses=licenses;
    }

}
