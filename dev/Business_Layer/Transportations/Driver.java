package Business_Layer.Transportations;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

    public class Driver {

     private static int idcounter=1;
     private int id;
     private String name;
     private List<License> licenses;
     private List<Transportation> transportations = new LinkedList<>();


    public Driver(String name, List<License> licenses)
    {
        this.id=idcounter++;
        this.name=name;
        this.licenses=licenses;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }
    public List<License> getLicenses()
    {
        return this.licenses;
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
        for(Transportation transportation1:transportations){
            if (transportationID==transportation1.getId())
                transportations.remove(transportation1);
        }
    }

    public List<Transportation> getTransportations(){
        return transportations;
    }

        public boolean checkLicense(List<License> license_list) {
            boolean output = false;
            for (License license : this.licenses) {
                for(License license1:license_list){
                    if(license1.getType()==license.getType()) {
                        output = true;
                        break;
                    }
                }
            }
            return output;
        }

}
