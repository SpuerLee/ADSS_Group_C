package Business_Layer;

import Presentation_Layer.Controler;

import com.google.gson.stream.JsonReader;
import com.google.gson.*;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Service {

    private static class SingletonService {
        private static Service instance = new Service();
    }
    private Service() {
        // initialization code..
    }
    public static Service getInstance() {
        return SingletonService.instance;
    }

    private HashMap<Integer,Drivers> HashDrivers= new HashMap<>();
    private HashMap<Integer,Sites> HashSites= new HashMap<>();
    private HashMap<Integer,Trucks> HashTrucks= new HashMap<>();
    public HashMap<Integer,Items> HashItems= new HashMap<>();
    public HashMap<Integer,Transportation> HashTransportation= new HashMap<>();
    public HashMap<Integer,Transportation> HashMissingItems= new HashMap<>();

    Controler control=new Controler();

    public void uploadData()
    {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonReader reader = new JsonReader(new FileReader("Data.json"));
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
    }

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

    private void create_transport(int id, int transport_id,  int site_id, List<HashMap<String,Integer>> items_list)
    {
        HashItems.put(id,new Items(id,transportation_id,site_id,items_list));
    }

    private void delete_transport(int id)
    {
        HashItems.remove(id);
    }
}


