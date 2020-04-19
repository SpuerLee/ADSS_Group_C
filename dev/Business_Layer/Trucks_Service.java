package Business_Layer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    public String showtrucks(){
        String result="";
        for(Trucks trucks: service.getHashTrucks().values()){
            result=result+"License Number: "+trucks.getlicense_number()+" , Model: "+trucks.getModel()+"\n";
        }
        return result;
    }


    public boolean addTruck(String license_number, List<String> licenses_types, String model, String weight, String max_weight){
        List<License> licenses=new LinkedList<>();
        for(String license:licenses_types){
            licenses.add(new License(license));
        }
        Trucks trucks=new Trucks(Integer.parseInt(license_number),licenses,model,Double.parseDouble(weight),Double.parseDouble(max_weight));
        service.getHashTrucks().put(Integer.parseInt(license_number),trucks);
        return true;
    }



    public boolean removeTruck(String license){
        boolean result=false;
        Integer number=Integer.parseInt(license);
        for(Trucks trucks:service.getHashTrucks().values()){
            if ((trucks.getlicense_number().equals(number))){
                result=true;
                for(Transportation transportation:service.getHashTransportation().values()) {
                    if (transportation.getTruck().getlicense_number()==number)
                        result = false;
                }
                if(result)
                    service.getHashTrucks().remove(trucks.getlicense_number());
            }
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



}
