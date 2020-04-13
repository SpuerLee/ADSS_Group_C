package Business_Layer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Drivers_Service {

    private static class Singelton_Driver {
        private static Drivers_Service instance = new Drivers_Service();
    }
    private Drivers_Service() {
        // initialization code..
    }
    public static Drivers_Service getInstance() {
        return Singelton_Driver.instance;
    }

    Service service=Service.getInstance();

    public boolean addDriver(String name, List<String> license_list){
        List <License> licenses=new LinkedList<>();
        for(String license: license_list){
            licenses.add(new License(license));
        }
        Drivers drivers=new Drivers(name,licenses);
        service.getDrivers().add(drivers);
        return true;
    }


    public boolean removeDriver(String name){
        boolean result=true;
        for(Drivers drivers:service.getDrivers()){
            if ((drivers.getName().equals(name))){
                // result=true;
               for(Transportation transportation: service.getHashTransportation().values()){
                   if(transportation.getDriver().equals(drivers)){
                       result=false;
                   }
               }
               if(result)
                service.getDrivers().remove(drivers);
            }
        }
        return result;
    }

    public String showDrivers(){
        String result="";
        for(Drivers drivers: service.getDrivers()){
            result=result+"Name:"+drivers.getName()+"\n";
        }
        return result;
    }



}
