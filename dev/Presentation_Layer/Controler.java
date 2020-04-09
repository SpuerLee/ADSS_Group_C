package Presentation_Layer;

import Business_Layer.Drivers;
import Business_Layer.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Controler {

    Service service=new Service();

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


        return service.add_driver(id,name,list);
    }

    public void Show_drivers_List(){

    }

    public void Remove_driver(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the driver id that you would like to delete");
        String id= scan.nextLine();
        service.delete_Driver(id);
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
        service.delete_Driver(id);
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
        service.delete_site(id);
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
        service.delete_truck(id);
    }

}
