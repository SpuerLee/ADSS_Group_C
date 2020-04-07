package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class Trucks {

    public static HashMap<Integer,Trucks> HashTrucks= new HashMap<>();

    private int license_number;
    private enum type{
        one,
        two
    };
    private type truck_type;
    private String model;
    private double weight;
    private double max_weight;

    public Trucks(int license_number, type type, String model, double weight, double max_weight)
    {
        this.license_number=license_number;
        this.truck_type=type;
        this.model=model;
        this.weight=weight;
        this.max_weight=max_weight;
    }

    private void create_Site (int license_number, type type, String model, double weight, double max_weight)
    {
       HashTrucks.put(license_number, new Trucks(license_number,type,model,weight,max_weight));
    }

    private void delete_Truck(int id)
    {
        HashTrucks.remove(id);
    }

}
