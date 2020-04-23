package Business_Layer.Services;

import Business_Layer.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Trucks_Service {

    private static class Singelton_Trucks {
        private static Trucks_Service instance = new Trucks_Service();
    }
    private Trucks_Service() {
        // initialization code..
    }
    public static Trucks_Service getInstance() {
        return Singelton_Trucks.instance;
    }

    private Service service=Service.getInstance();

    public String showtrucks() throws Buisness_Exception{
        if(service.getHashTrucks().size()==0){
            throw new Buisness_Exception("There are no trucks in the system"+ "\n");
        }
        String result="";
        for(Truck trucks: service.getHashTrucks().values()){
            result=result+"Id: " + trucks.getId()+", License Number: "+
                    trucks.getlicense_number()+", Model: "+trucks.getModel()+ ", Licenses: ";
            for(License license :trucks.getLicenses())
            {
                result += license.getType()+", ";
            }
            result+="\n";
        }
        return result;
    }


    public boolean addTruck(String license_number, List<String> licenses_types, String model, String weight, String max_weight) throws Buisness_Exception{
        List<License> licenses=new LinkedList<>();
        Integer number=Integer.parseInt(license_number);
        boolean result=true;
        if(service.getHashTrucks().containsKey(number)){
            result=false;
            throw new Buisness_Exception("The truck driving license is already exist"+ "\n");
        }
        else {
            for (String license : licenses_types) {
                licenses.add(new License(license));
            }
            Truck trucks = new Truck(Integer.parseInt(license_number), licenses, model, Double.parseDouble(weight), Double.parseDouble(max_weight));
            service.getHashTrucks().put(trucks.getId(), trucks);
            return result;
        }
    }



    public boolean removeTruck(int id) throws Buisness_Exception{
        boolean result=false;
       if(!service.getHashTrucks().containsKey(id))
           throw new Buisness_Exception("The truck's license number does'nt exist "+"\n");
       else {
           service.getHashTrucks().remove(id);
           result=true;
       }
       return result;
    }

    public String getTrucksToDriver(String id, Date date)
    {
        int driverId = Integer.parseInt(id);
        List<License> license_list = service.getDrivers().get(driverId).getLicenses();
        String output = "";
        for (Truck truck : service.getHashTrucks().values())
        {
            if(truck.checkIfFree(date) && truck.checkLicense(license_list))
            {
                output = output + truck.getlicense_number()+". "+truck.getModel()+"\n";
            }
        }
        return output;
    }

    public String getFreeTrucks(Date date) {
        String output = "";
        for (Truck truck : service.getHashTrucks().values()) {
            if (truck.checkIfFree(date)) {
                output = output + truck.getId()+". "+"license number: "+truck.getlicense_number()+
                        ", Model: "+truck.getModel()+"\n";
            }
        }
        return output;
    }



}
