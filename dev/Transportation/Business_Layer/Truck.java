package Transportation.Business_Layer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Truck {

    private static int idcounter=1;
    private int id;
    private int license_number;
    private List<License> licenses_types;
    private String model;
    private double weight;
    private double max_weight;
    private List<Transportation> transportations = new LinkedList<>();

    public Truck(int license_number, List<License> licenses_types, String model, double weight, double max_weight) {
        this.id=idcounter++;
        this.license_number = license_number;
        this.licenses_types = licenses_types;
        this.model = model;
        this.weight = weight;
        this.max_weight = max_weight;
    }

    public double getMax_weight()
    {
        return this.weight+this.max_weight;
    }
    public int getId()
    {
        return this.id;
    }

    public List<License> getLicenses()
    {
        return this.licenses_types;
    }

    public Integer getlicense_number() {
        return this.license_number;
    }

    public String getModel() {
        return this.model;
    }

    public void addDate(Transportation transportation)
    {
        this.transportations.add(transportation);
    }


    public boolean checkIfFree(Date date)
    {
        for(Transportation transportation:transportations){
            if (date.equals(transportation.getDate()))
                return false;
        }
        return true;
    }

    public void Remove_date(Integer transportationID){
        try
        {
            for(Transportation transportation1:transportations){
                if (transportationID==transportation1.getId())
                    transportations.remove(transportation1);
            }
        }
        catch (Exception e)
        {

        }

    }

    public List<Transportation> getTransportations(){
        return transportations;
    }




    public boolean checkLicense(List<License> license_list) {
        boolean output = false;
        for (License license : this.licenses_types) {
            for(License license1:license_list){
                if(license1.getType()==(license.getType())) {
                    output = true;
                    break;
                }
            }
        }
        return output;
    }
}