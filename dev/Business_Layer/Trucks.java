package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Trucks {

    private int license_number;
    private List<License> licenses_types;
    private String model;
    private double weight;
    private double max_weight;
    private List<Date> do_transportation_days = new LinkedList<>();

    public Trucks(int license_number, List<License> licenses_types, String model, double weight, double max_weight) {
        this.license_number = license_number;
        this.licenses_types = licenses_types;
        this.model = model;
        this.weight = weight;
        this.max_weight = max_weight;
    }

    public Integer getlicense_number() {
        return this.license_number;
    }

    public String getModel() {
        return this.model;
    }

    public void addDate(Date date)
    {
        this.do_transportation_days.add(date);
    }


    public boolean checkIfFree(Date date) {
        for (Date transportation : do_transportation_days) {
            if (transportation.equals(date))
                return false;
        }
        return true;
    }

    public void Remove_date(Date date) {
        for (Date transportation : do_transportation_days) {
            if (transportation.equals(date))
                do_transportation_days.remove(transportation);
        }
    }

    public List<Date> getDo_transportation_days() {
        return do_transportation_days;
    }


    public boolean checkLicense(List<License> license_list) {
        boolean output = false;
        for (License license : license_list) {
            if (this.licenses_types.contains(license)) {
                output = true;
                break;
            }
        }
        return output;
    }
}