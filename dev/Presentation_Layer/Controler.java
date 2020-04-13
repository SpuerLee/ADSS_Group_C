package Presentation_Layer;

import Business_Layer.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

public class Controler {


    private Service service = Service.getInstance();
    private Trucks_Service trucks_service=Trucks_Service.getInstance();
    private Site_Service site_service=Site_Service.getInstance();
    private Transportation_Service transportation_service=Transportation_Service.getInstance();
    private Missing_items_Service missing_items_service=Missing_items_Service.getInstance();
    private Drivers_Service drivers_service=Drivers_Service.getInstance();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private static class SingletonControler {
        private static Controler instance = new Controler();
    }
    private Controler() {
        // initialization code..
    }
    public static Controler getInstance() {
        return Controler.SingletonControler.instance;
    }

    public void uploadData()
    {
        service.uploadData();
    }


    /*
    public boolean Complete_stock_missing()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println(missing_items_service.getMissingItemsStores());
        System.out.println("Please choose store to transportation");
        String storeId= scan.nextLine();
        System.out.println(site_service.getSupplierAreaByStore(storeId));
        System.out.println("Please choose area to transportation");
        String area= scan.nextLine();
        System.out.println(site_service.getSupplierByStoreArea(storeId,area));
        System.out.println("Please choose suppliers to transportation");
        String[] suppliers = scan.nextLine().split(" ");
        boolean find_truck_driver=false;
        Date date = new Date();
        String driverId="";
        String truckId="";
        while (!find_truck_driver)
        {
            System.out.println("Please choose date to transportation by the pattern MM-dd-yyyy");
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                date = simpleDateFormat.parse(scan.nextLine());
            }
            catch (ParseException e){
                e.getErrorOffset();
            }
            String freeDrivers=service.getFreeDrivers(date);
            if(freeDrivers!="")
            {
                System.out.println("The drivers available for the date are:");
                System.out.println(freeDrivers);
                System.out.println("Please choose driver to transportation");
                driverId= scan.nextLine();
                String freeTrucks=service.getTrucksToDriver(driverId,date);
                if(freeTrucks!="")
                {
                    System.out.println("The trucks available for the date are and driver:");
                    System.out.println(freeTrucks);
                    System.out.println("Please choose driver to transportation");
                    truckId= scan.nextLine();
                    find_truck_driver=true;
                }
                else
                {
                    System.out.println("There are no trucks available for the driver at this date");
                }
            }
            else
            {
                System.out.println("No drivers available on date");
            }
        }

        List<Integer> stores=new LinkedList<>();
        stores.add(Integer.parseInt(storeId));
        List<Integer> supplier_list=new LinkedList<>();
        for (String s:suppliers)
        {
            supplier_list.add(Integer.parseInt(s));
        }
        if(service.createTransportation(date, LocalTime.parse("12:00:00"),Integer.parseInt(driverId),
            Integer.parseInt(truckId),supplier_list,stores))
        {
            System.out.println("The transport was registered successfully");
        }
        return true;
    }

    public boolean Regular_stock_transport()
    {
        Scanner scan = new Scanner(System.in);;
        System.out.println(site_service.getSuppliers()); //print all the suppliers
        System.out.println("Please choose supplier to transportation by id"); //choose supplier
        String supplier = scan.nextLine();
        System.out.println(site_service.getStoresByarea()); //show all the area with all the stores in it
        System.out.println("Please choose area to transportation"); //chose area
        String area= scan.nextLine();
        System.out.println(site_service.get_Stores_By_specific_area(area)); //show all the stores in the area
        System.out.println("Please choose stores (by id) to make transportation");
        String[] stores = scan.nextLine().split(" ");
        List<Integer> stores1=new LinkedList<>();
        for(String store:stores) {
            stores1.add(Integer.parseInt(store));
        }
        String driverId="";
        String truckId="";
        boolean find_truck_driver=false;
        Date date=new Date();
        while (!find_truck_driver) {
            System.out.println("Please choose date to transportation by the pattern MM-dd-yyyy");
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                date = simpleDateFormat.parse(scan.nextLine());
            }
            catch (ParseException e){
                e.getErrorOffset();
            }
            String freeDrivers = service.getFreeDrivers(date);
            if (freeDrivers != "") {
                System.out.println("The drivers available for the date are:");
                System.out.println(freeDrivers);
                System.out.println("Please choose driver to transportation");
                driverId = scan.nextLine();
                String freeTrucks = service.getTrucksToDriver(driverId, date);
                if (freeTrucks != "") {
                    System.out.println("The trucks available for the date are and driver:");
                    System.out.println(freeTrucks);
                    System.out.println("Please choose truck to transportation");
                    truckId = scan.nextLine();
                    find_truck_driver = true;
                } else {
                    System.out.println("There are no trucks available for the driver at this date");
                }
            } else {
                System.out.println("No drivers available on date");
            }
        }
        if(service.createRegularTransportation(date, LocalTime.parse("12:00:00"),Integer.parseInt(driverId),
                Integer.parseInt(truckId),Integer.parseInt(supplier),stores1))
        for(String store:stores) {
                System.out.println("Please enter the next detalis for store :" + store);
                boolean exit = false;
                HashMap<String, Integer> add = new HashMap<>();
                System.out.println("Please enter a product and the quantity required seperate by space");
                System.out.println("Enter end to the next store");
                while (!exit) {
                    String[] items = scan.nextLine().split(" ");
                    if (items[0].equals("end")) {
                        exit = true;
                    } else {
                        add.put(items[0], Integer.parseInt(items[1]));
                }
            }
            service.add_to_items_file(date, LocalTime.parse("12:00:00"),Integer.parseInt(driverId),
                    Integer.parseInt(truckId),Integer.parseInt(supplier),Integer.parseInt(store),add);
          }
        System.out.println("The transport was registered successfully");
        return true;
        } */

    //Drivers

    public boolean Add_driver(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter driver name");
        String name= scan.nextLine();
        String licenese=null;
        List<String> list=new LinkedList<>();
        System.out.println("Please enter driver's license (A/B) "+"\n" + "If there many than enter license and than press enter to the next one.");
        System.out.println("If you dine enter the license please press q");
        do{
            licenese=scan.nextLine();
            list.add(licenese);
        }
        while (!licenese.equals("q"));

        System.out.println("The driver was added successfully");
        return drivers_service.addDriver(name,list);
    }

    public void Show_drivers_List(){
        String result=drivers_service.showDrivers();
        if(result.equals("")){
            System.out.println("there are no drivers to show");
        }
        else {
            System.out.println(result);
        }
    }

    public void Remove_driver(){
        System.out.println(drivers_service.showDrivers());
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the driver's name that you would like to delete");
        String name= scan.nextLine();
        boolean result=drivers_service.removeDriver(name);
        if(result){
            System.out.println("The driver has removed successfully");
        }
        else {
            System.out.println("The deletion could not be performed");
        }
    }



    // Transportations

    public void Show_transports(){

    }

   /* public void Remove_transport(){
        String transport=Transportation_Service.getTransport_id();
        if(transport.equals("")){
            System.out.println("There are no transportations to delete");
        }
        else {
            Scanner scan = new Scanner(System.in);
            System.out.println("The transport that can be deleted (represented by their ID) are: ");
            System.out.println(transport);
            System.out.println("Please enter the transport id that you would like to delete");
            String id = scan.nextLine();
            service.delete_Transport(id);
        }
    }
 */
    //Sites

    public void Add_site(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose site's type store/supplier"); //choose supplier
        String type = scan.nextLine();
        System.out.println("Please choose the name of the site"); //chose area
        String name= scan.nextLine();
        System.out.println("Please choose city of the site");
        String city= scan.nextLine();
        System.out.println("Please choose street of the site");
        String street= scan.nextLine();
        System.out.println("Please choose street's number of the site");
        String number= scan.nextLine();
        System.out.println("Please choose name of contact of the site");
        String name_of_contact= scan.nextLine();
        System.out.println("Please choose name of contact's phone");
        String phone= scan.nextLine();
        System.out.println("Please choose site's area (A/B/C/D)");
        String area= scan.nextLine();
        site_service.addsite(type,name,city,street,number,name_of_contact,phone,area);
        System.out.println("The site was added successfully");

    }

    public void Show_sites(){
        String result=site_service.showsite();
        if(result.equals("")){
            System.out.println("there are no sites to show");
        }
        else {
            System.out.println(result);
        }
    }

    public void Remove_site(){
        System.out.println(site_service.showsite());
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the site name that you would like to delete");
        String name= scan.nextLine();
        boolean result=site_service.removeSite(name);
        if(result){
            System.out.println("The site has removed successfully");
        }
        else {
            System.out.println("The deletion could not be performed");
        }
    }

    //Trucks

    public void Add_truck(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter truck's license_number");
        String number= scan.nextLine();
        System.out.println("Please enter licenses_types separated by a space");
        String type= scan.nextLine();
        String [] license=type.split(" ");
        List<String> list=new LinkedList<>();
        for(String L1:license){
            list.add(L1);
        }
        System.out.println("Please enter truck's model");
        String model= scan.nextLine();
        System.out.println("Please enter truck's weight");
        String weight= scan.nextLine();
        System.out.println("Please enter truck's max weight");
        String max_weight= scan.nextLine();
        boolean result=trucks_service.addTruck(number,list,model,weight,max_weight);
        System.out.println("Truck was added successfully");
    }

    public void Show_trucks(){
        String result=trucks_service.showtrucks();
        if(result.equals("")){
            System.out.println("there are no trucks to show");
        }
        else {
            System.out.println(result);
        }
    }

    public void Remove_truck(){
        System.out.println(trucks_service.showtrucks());
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the truck's license number that you would like to delete");
        String id= scan.nextLine();
        boolean result=trucks_service.removeTruck(id);
        if(result){
            System.out.println("The truck has removed successfully");
        }
        else {
            System.out.println("The deletion could not be performed");
        }
    }

    public void Show_Error(String error){
        System.out.println(error);
    }

}
