package Business_Layer.Services;

import Business_Layer.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    public void addDriver(String name, List<String> license_list) {
        List<License> licenses = new LinkedList<License>();
        for (String license : license_list) {
            licenses.add(new License(license));
        }
        Drivers drivers = new Drivers(name, licenses);
        service.getDrivers().put(drivers.getId(), drivers);
    }

    public void removeDriver(int id) throws Buisness_Exception{
        if(!service.getDrivers().containsKey(id))
            throw new Buisness_Exception("The drives's id doesn't exist "+"\n");
        else {
            service.getDrivers().remove(id);
        }
    }

    public String showDrivers() throws Buisness_Exception {
        if(service.getDrivers().size()==0)
            throw new Buisness_Exception("There are no drivers in the system"+ "\n");
        String result = "";
        for (Drivers drivers : service.getDrivers().values()) {
            result = result + "Name:" + drivers.getName() + ", Id " + drivers.getId() + "\n";
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

    public String getDriverToTrucks(String id, Date date)
    {
        int truckId = Integer.parseInt(id);
        List<License> license_list = service.getHashTrucks().get(truckId).getLicenses();
        String output = "";
        for (Drivers driver : service.getDrivers().values())
        {
            if(driver.checkIfFree(date) && driver.checkLicense(license_list))
            {
                output = output + driver.getId()+". "+driver.getName()+"\n";
            }
        }
        return output;
    }
}





