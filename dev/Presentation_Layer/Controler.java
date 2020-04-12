package Presentation_Layer;

import Business_Layer.Drivers;
import Business_Layer.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Controler {


    private Service service = Service.getInstance();
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

    public boolean Complete_stock_missing()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println(service.getMissingItemsStores());
        System.out.println("Please choose store to transportation");
        String storeId= scan.nextLine();
        System.out.println(service.getSupplierAreaByStore(storeId));
        System.out.println("Please choose area to transportation");
        String area= scan.nextLine();
        System.out.println(service.getSupplierByStoreArea(storeId,area));
        System.out.println("Please choose suppliers to transportation");
        String[] suppliers = scan.nextLine().split(" ");
        boolean find_truck_driver=false;
        LocalDate date = LocalDate.now();
        String driverId="";
        String truckId="";
        while (!find_truck_driver)
        {
            System.out.println("Please choose date to transportation");
            date = LocalDate.parse(scan.nextLine(), formatter);
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

    //Drivers

    public boolean Add_driver(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter driver id");
        String id= scan.nextLine();
        System.out.println("Please enter driver name");
        String name= scan.nextLine();
        String licenese=null;
        List<String> list=new LinkedList<>();
        System.out.println("Please enter driver's license if there many than enter license and than press enter to the next one");
        System.out.println("If you dine enter the license please press q");
        do{
            licenese=scan.nextLine();
            list.add(licenese);
        }
        while (!licenese.equals("q"));

        //return service.add_driver(id,name,list);
        return true;
    }

    public void Show_drivers_List(){

    }

    public void Remove_driver(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the driver id that you would like to delete");
        String id= scan.nextLine();
        //service.delete_Driver(id);

    }

    // Transportations

    public void Add_transport(){

    }

    public void Show_transports(){

    }
    public void Remove_transport(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the transport id that you would like to delete");
        String id= scan.nextLine();
        //service.delete_Driver(id);
    }

    //Sites

    public void Add_site(){

    }
    public void Show_sites(){

    }
    public void Remove_site(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the site id that you would like to delete");
        String id= scan.nextLine();
        //service.delete_site(id);
    }

    //Trucks

    public void Add_truck(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter truck's license_number");
        String number= scan.nextLine();
        System.out.println("Please enter truck's type");
        String type= scan.nextLine();
        System.out.println("Please enter truck's model");
        String model= scan.nextLine();
        System.out.println("Please enter truck's weight");
        String weight= scan.nextLine();
        System.out.println("Please enter truck's max weight");
        String max_weight= scan.nextLine();
    }

    public void Show_trucks(){

    }
    public void Remove_truck(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the truck's license number that you would like to delete");
        String id= scan.nextLine();
        //service.delete_truck(id);
    }

    public void Show_Error(String error){
        System.out.println(error);
    }

}
