package Business_Layer;

import java.util.HashMap;
import java.util.List;

public class Trucks {

    public static HashMap<Integer,Trucks> HashTrucks= new HashMap<>();

    private int license_number;
    private enum type{
        one,
        two
    };
    private String model;
    private double weight;
    private double max_weight;

}
