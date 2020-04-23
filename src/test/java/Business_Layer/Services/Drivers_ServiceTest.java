package Business_Layer.Services;

import Business_Layer.Buisness_Exception;
import Business_Layer.Driver;
import Business_Layer.License;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Drivers_ServiceTest {

    Service service;
    Drivers_Service drivers_service;
    Driver driver1;
    Driver driver2;
    Driver driver3;

    @BeforeEach
    void setUp() {
        service=Service.getInstance();
        drivers_service= Drivers_Service.getInstance();
        List<License> licenses= new LinkedList<>();
        licenses.add(new License("C"));
        driver1=new Driver("amit",licenses);
        List<License> licenses1= new LinkedList<>();
        licenses.add(new License("C1"));
        service.getDrivers().put(driver1.getId(),driver1);
        driver2=new Driver("reut",licenses);
        service.getDrivers().put(driver2.getId(),driver2);
        List<License> licenses2= new LinkedList<>();
        licenses.add(new License("C"));
        driver3=new Driver("reut1",licenses);
        service.getDrivers().put(driver3.getId(),driver3);
    }

    @Test
    void addDriver() {
        int size=service.getDrivers().size();
        List<String> licenses= new LinkedList<>();
        licenses.add("C1");
        drivers_service.addDriver("reut",licenses);
        assertEquals(size+1,service.getDrivers().size());

    }

    @Test
    void removeDriver() {
        Service service=Service.getInstance();
        Drivers_Service drivers_service= Drivers_Service.getInstance();
        int size=service.getDrivers().size();
        try
        { drivers_service.removeDriver(driver1.getId());
        }
        catch (Buisness_Exception e){
            e.printStackTrace();
        }
        assertEquals(size-1,service.getDrivers().size());

    }

    @Test
    void showDrivers() {
        String result="";
        try {
            result=drivers_service.showDrivers();
        }
        catch (Buisness_Exception e){
            e.printStackTrace();
        }
        Assert.assertThat(result,containsString("reut"));
    }
}