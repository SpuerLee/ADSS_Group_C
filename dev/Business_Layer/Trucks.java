package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Trucks {

    private int license_number;
    private List<String> licenses_types;
    private String model;
    private double weight;
    private double max_weight;
    private List<LocalDate> do_transportation_days = new LinkedList<>();

    public Trucks(int license_number, List<String> licenses_types, String model, double weight, double max_weight)
    {
        this.license_number=license_number;
        this.licenses_types=licenses_types;
        this.model=model;
        this.weight=weight;
        this.max_weight=max_weight;
    }

    public Integer getlicense_number()
    {
        return this.license_number;
    }
    public String getModel()
    {
        return this.model;
    }

    public void addDate(LocalDate date)
    {
        this.do_transportation_days.add(date);
    }

    public boolean checkLicense(List<String> license_list)
    {
        boolean output = false;
        for (String license : license_list)
        {
            if(this.licenses_types.contains(license))
            {
                output=true;
                break;
            }
        }
        return output;
    }

    public boolean checkIfFree(LocalDate date)
    {
        if(do_transportation_days.contains(date))
        {
            return false;
        }
        else
        {
            //do_transportation_days.add(date);
            return true;
        }
    }

}
