package Business_Layer;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Service {

    private static HashMap<Integer,Drivers> HashDrivers= new HashMap<>();
    private static HashMap<Integer,Sites> HashSites= new HashMap<>();
    private static HashMap<Integer,Trucks> HashTrucks= new HashMap<>();

    public void add_driver(String id, String name, List<String> licenses)
    {
        int driver_id = Integer.parseInt(id);
        List<Drivers.type> licenses1=new LinkedList<>();
        for (String license: licenses){
            if(license.equals("1"))
                licenses1.add(Drivers.type.one);
            else
                licenses1.add(Drivers.type.two);
        }
        HashDrivers.put(driver_id,new Drivers(driver_id,name,licenses1));
    }

    public void delete_Driver(String id)
    {
        int driver_id = Integer.parseInt(id);
        HashDrivers.remove(driver_id);
    }


  /* private void create_Site(int id, Sites.type site_type, String name, String city, String street, int number, String name_of_contact, String phone, Sites.area site_area)
    {
        HashSites.put(id,new Sites(id,site_type,name,city,street,number,name_of_contact,phone,site_area));
    } */

    public void delete_site(String id)
    {
        int site_id = Integer.parseInt(id);
        HashSites.remove(site_id);
    }

    public void delete_truck(String licennse_number)
    {
        int site_id = Integer.parseInt(licennse_number);
        HashSites.remove(site_id);
    }
}


