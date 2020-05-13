package Business_Layer.Transportations.Services;

import Business_Layer.Modules.*;
import Business_Layer.Transportations.Modules.*;
import Business_Layer.Workers.Modules.Worker.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Transportation_ServiceTest {

    Service service;
    Transportation_Service transportation_service;
    String pattern = "MM-dd-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Driver driver1;
    Truck trucks;
    Store site2;
    Supplier site;
    MissingItems missingItems;

    @BeforeEach
    void setUp() {
        transportation_service= Transportation_Service.getInstance();
        service=Service.getInstance();
        service=Service.getInstance();
        List<License> licenses= new LinkedList<>();
        licenses.add(new License("C"));
        driver1=new Driver("amit",licenses);
        service.getDrivers().put(driver1.getId(),driver1);
        licenses.add(new License("C"));
        trucks=new Truck(12345678,licenses,"skoda",180,300);
        service.getHashTrucks().put(trucks.getlicense_number(),trucks);
        site2=new Store("store2","0546343167","reut1",new Address("Haifa","Kadesh",18), new Area("B"));
        service.getHashStoresMap().put(site2.getId(),site2);
        site=new Supplier("Osem","0546323167","amit",new Address("Tel-Aviv","Yaacov-Cohen",3), new Area("C"));
        service.getSuppliersMap().put(site.getId(),site);
        HashMap<String,Integer> items=new HashMap<>();
        items.put("milk",5);
        items.put("yogurt",100);
        missingItems=new MissingItems(site2.getId(),site.getId(),items);
        service.getMissing().put(missingItems.getID(),missingItems);

    }
    @Test
    void createTransportation() {
        int size=service.getHashTransportation().size();
        Date date=new Date();
        List<Integer> suppliers=new LinkedList<>();
        suppliers.add(site.getId());
        List<Integer> stores=new LinkedList<>();
        stores.add(site2.getId());
        try {
            date = simpleDateFormat.parse("23-04-2020");
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        transportation_service.createTransportation(date, LocalTime.parse("12:00:00"),driver1.getId(),12345678,suppliers,stores);
        assertEquals(size+1,service.getHashTransportation().size());
    }

    @Test
    void createRegularTransportation() {
        int size=service.getHashTransportation().size();
        Date date=new Date();
        List<Integer> suppliers=new LinkedList<>();
        suppliers.add(site.getId());
        List<Integer> stores=new LinkedList<>();
        stores.add(site2.getId());
        transportation_service.createRegularTransportation(date,LocalTime.parse("16:05:00"),driver1.getId(),12345678,suppliers,stores);
        assertEquals(size+1,service.getHashTransportation().size());

    }

}