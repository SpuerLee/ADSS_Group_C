package Business_Layer.Services;

import Business_Layer.*;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

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
        for(Trucks trucks: service.getHashTrucks().values()){
            result=result+"License Number: "+trucks.getlicense_number()+" , Model: "+trucks.getModel()+"\n";
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
            Trucks trucks = new Trucks(Integer.parseInt(license_number), licenses, model, Double.parseDouble(weight), Double.parseDouble(max_weight));
            service.getHashTrucks().put(Integer.parseInt(license_number), trucks);
            return result;
        }
    }



    public boolean removeTruck(String license) throws Buisness_Exception{
        boolean result=false;
        Integer number=Integer.parseInt(license);
       if(!service.getHashTrucks().containsKey(number))
           throw new Buisness_Exception("The truck's license number does'nt exist "+"\n");
       else {
           service.getHashTrucks().remove(number);
           result=true;
       }
       return result;
    }

    public String getTrucksToDriver(String id, Date date)
    {
        int driverId = Integer.parseInt(id);
        List<License> license_list = service.getDrivers().get(driverId).getLicenses();
        String output = "";
        for (Trucks truck : service.getHashTrucks().values())
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
        for (Trucks truck : service.getHashTrucks().values()) {
            if (truck.checkIfFree(date)) {
                output = output + truck.getlicense_number()+". "+truck.getModel()+"\n";
            }
        }
        return output;
    }



}
