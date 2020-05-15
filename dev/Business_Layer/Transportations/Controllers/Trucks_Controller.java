package Business_Layer.Transportations.Controllers;

import Business_Layer.Service;
import Business_Layer.Transportations.Utils.Buisness_Exception;
import Business_Layer.Modules.License;
import Business_Layer.Transportations.Modules.Truck;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Trucks_Controller {

    private static class Singelton_Trucks {
        private static Trucks_Controller instance = new Trucks_Controller();
    }
    private Trucks_Controller() {
        // initialization code..
    }
    public static Trucks_Controller getInstance() {
        return Singelton_Trucks.instance;
    }



    public List<String> showtrucks() throws Buisness_Exception{
        Service service=Service.getInstance();
        if(service.getHashTrucks().size()==0){
            throw new Buisness_Exception("There are no trucks in the system"+ "\n");
        }
        List<String> result = new LinkedList<>();
        for(Truck trucks: service.getHashTrucks().values()){
            String line = trucks.getId()+". License Number: "+
                    trucks.getlicense_number()+", Model: "+trucks.getModel()+ ".\n Licenses: ";
//            line += trucks.getLicenses().toString()+".";
            for(License license :trucks.getLicenses())
            {
                line += license.getLicenseType()+", ";
            }
            line+=".";
            result.add(line);
        }
        return result;
    }


    public boolean addTruck(int license_number, List<String> licenses_types,
                            String model, double weight, double max_weight) throws Buisness_Exception {
        Service service=Service.getInstance();
        List<License> licenses=new LinkedList<>();
        boolean result=true;
        if(service.getHashTrucks().containsKey(license_number)){
            result=false;
            throw new Buisness_Exception("The truck driving license is already exist"+ "\n");
        }
        else {
            for (String license : licenses_types) {
                licenses.add(new License(license, licenseSN, licenseType));
            }
            Truck trucks = new Truck(license_number, licenses, model, weight,max_weight);
            service.getHashTrucks().put(trucks.getId(), trucks);
            return result;
        }
    }



    public boolean removeTruck(int id) throws Buisness_Exception{
        Service service=Service.getInstance();
        boolean result=false;
       if(!service.getHashTrucks().containsKey(id))
           throw new Buisness_Exception("The truck's license number does'nt exist "+"\n");
       else {
           service.getHashTrucks().remove(id);
           result=true;
       }
       return result;
    }

    public List<String> getFreeTrucks(Date date) throws Buisness_Exception{
        Service service=Service.getInstance();
        List<String> output = new LinkedList<String>();
        for (Truck truck : service.getHashTrucks().values()) {
            if (truck.checkIfFree(date)) {
                String line = truck.getId()+". "+"license number: "+truck.getlicense_number()+
                        ", Model: "+truck.getModel()+".";
                output.add(line);
            }
            if(output.isEmpty())
                throw new Buisness_Exception("there are on free tracks\n");

        }
        return output;
    }



}
