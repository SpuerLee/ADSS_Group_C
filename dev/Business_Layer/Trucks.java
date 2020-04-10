package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class Trucks {

    private int license_number;
    public enum type{
        one,
        two
    };
    private type license_type;
    private String model;
    private double weight;
    private double max_weight;

    public Trucks(int license_number, type type, String model, double weight, double max_weight)
    {
        this.license_number=license_number;
        this.license_type=type;
        this.model=model;
        this.weight=weight;
        this.max_weight=max_weight;
    }

}
