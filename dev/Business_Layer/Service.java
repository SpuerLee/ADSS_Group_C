package Business_Layer;

import DataLayer.DriversHandler;
import Presentation_Layer.Controler;
import javafx.scene.control.TableColumn;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Service {

    private static HashMap<Integer,Drivers> HashDrivers= new HashMap<>();
    private static HashMap<Integer,Sites> HashSites= new HashMap<>();
    private static HashMap<Integer,Trucks> HashTrucks= new HashMap<>();
    Controler control=new Controler();
    DriversHandler driversHandler=new DriversHandler();

    public boolean add_driver(String id, String name, List<String> licenses)
    {
        int driver_id = Integer.parseInt(id);
        List<Drivers.type> licenses1=new LinkedList<>();
        for (String license: licenses){
            if(license.equals("1"))
                licenses1.add(Drivers.type.one);
            else if(license.equals("2"))
                licenses1.add(Drivers.type.two);
        }
        HashDrivers.put(driver_id,new Drivers(driver_id,name,licenses1));
        driversHandler.AddDriver(driver_id);
        return true;
    }

    public void delete_Driver(String id)
    {
        int driver_id = Integer.parseInt(id);
        HashDrivers.remove(driver_id);
    }

    public void Transfer_Error(String error){
        control.Show_Error(error);
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
        HashTrucks.remove(site_id);
    }

    public void Add_truck(String number,String type,String model, String weight, String max){
        int license_number = Integer.parseInt(number);
        Trucks.type type1=null;
        switch (type){
            case "1":
               type1=Trucks.type.one;
            case "2":
               type1=Trucks.type.two;

        }
        int weight1 = Integer.parseInt(weight);
        int maxweight1 = Integer.parseInt(max);
        HashTrucks.put(license_number,new Trucks(license_number,type1,model,weight1,maxweight1));
    }
}


