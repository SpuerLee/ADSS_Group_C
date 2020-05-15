package Business_Layer.Transportations.Controllers;
import Business_Layer.Workers.Modules.Worker.Driver;

import Business_Layer.Service;
import Business_Layer.Transportations.Utils.Buisness_Exception;
import Business_Layer.Modules.License;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Drivers_Controller {

    private static class Singelton_Driver {
        private static Drivers_Controller instance = new Drivers_Controller();
    }

    private Drivers_Controller() {
        // initialization code..
    }

    public static Drivers_Controller getInstance() {
        return Singelton_Driver.instance;
    }



    public void addDriver(String name, List<String> license_list) {
        Service service = Service.getInstance();
        List<License> licenses = new LinkedList<License>();
        for (String license : license_list) {
            licenses.add(new License(license));
        }
        Driver drivers = new Driver(name, licenses);
        service.getDrivers().put(drivers.getId(), drivers);
    }

    public void removeDriver(int id) throws Buisness_Exception{
        Service service = Service.getInstance();
        if(!service.getDrivers().containsKey(id))
            throw new Buisness_Exception("The drives's id doesn't exist "+"\n");
        else {
            service.getDrivers().remove(id);
        }
    }

    public String showDrivers() throws Buisness_Exception {
        Service service = Service.getInstance();
        if(service.getDrivers().size()==0)
            throw new Buisness_Exception("There are no drivers in the system"+ "\n");
        String result = "";
        for (Driver drivers : service.getDrivers().values()) {
            result = result +"Id: " + drivers.getId()+ ", Name: " + drivers.getName() + ", Licenses: ";
            for(License license :drivers.getLicenses())
            {
                result += license.getType()+", ";
            }
            result+="\n";
        }
        return result;
    }

    public String getFreeDrivers(Date date) {
        Service service = Service.getInstance();
        String output = "";
        for (Driver driver : service.getDrivers().values()) {
            if (driver.checkIfFree(date)) {
                output = output + driver.getId() + ". " + driver.getName() + "\n";
            }
        }
        return output;
    }

    public List<String> getDriverToTrucks(int truckId, Date date)
    {
        Service service = Service.getInstance();
        List<License> license_list = service.getHashTrucks().get(truckId).getLicenses();
        List<String> output = new LinkedList<String>();
        for (Driver driver : service.getDrivers().values())
        {
            if(driver.checkIfFree(date) && driver.checkLicense(license_list))
            {
                String line = driver.getId()+". "+driver.getName()+".";
                output.add(line);
            }
        }
        return output;
    }
}





