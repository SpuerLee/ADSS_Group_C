package Business_Layer;

import java.util.Date;
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

    private Service service = Service.getInstance();

    public boolean addDriver(int license_number, String name, List<String> license_list) {
        List<License> licenses = new LinkedList<>();
        for (String license : license_list) {
            licenses.add(new License(license));
        }
        Drivers drivers = new Drivers(license_number, name, licenses);
        service.getDrivers().put(license_number, drivers);
        return true;
    }


    public boolean removeDriver(int id) {
        boolean result = true;
        for (Drivers drivers : service.getDrivers().values()) {
            if ((drivers.getId() == id)) {
                // result=true;
                for (Transportation transportation : service.getHashTransportation().values()) {
                    if (transportation.getDriver().equals(drivers)) {
                        result = false;
                    }
                }
                if (result)
                    service.getDrivers().remove(id);
            }
        }
        return result;
    }

    public String showDrivers() {
        String result = "";
        for (Drivers drivers : service.getDrivers().values()) {
            result = result + "Name:" + drivers.getName() + "Id " + drivers.getId() + "\n";
        }
        return result;
    }

    public String getFreeDrivers(Date date) {
        String output = "";
        for (Drivers driver : service.getDrivers().values()) {
            if (driver.checkIfFree(date)) {
                output = output + driver.getId() + ". " + driver.getName() + "\n";
            }
        }
        return output;
    }
}





