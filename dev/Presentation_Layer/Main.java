package Presentation_Layer;
import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Controler controler=new Controler();
        System.out.println("Hello, please choose your option:");
        String[] A = new String[]{"Drivers", "Transportations", "Sites", "Trucks", "Quit"};
        String choice = null;
        for (int i = 0; i < A.length; i++) {
            System.out.println(i+1 + ". " + A[i]);
        }
        Scanner scan = new Scanner(System.in);
        do {
            choice = scan.nextLine();
          switch (choice) {
                case "1":{
                    String[] drivers = new String[]{"Add new driver", "Show drivers list", "Remove driver"};
                    String driver_option = null;
                    for (int i = 0; i < drivers.length; i++) {
                        System.out.println(i+1 + ". " + drivers[i]);
                    }
                    driver_option=scan.nextLine();
                    switch (driver_option){
                        case "1":{
                           controler.Add_driver();
                        }
                        case "2":{
                            controler.Show_drivers_List();
                        }
                        case "3":{
                            controler.Remove_driver();
                        }
                    }
                }
              case "2": {
                  String[] transports = new String[]{"Add new transport", "Show transports List", "Remove transport"};
                  String option = null;
                  for (int i = 0; i < transports.length; i++) {
                      System.out.println(i+1 + ". " + transports[i]);
                  }
                  option = scan.nextLine();
                  switch (option) {
                      case "1": {
                          controler.Add_transport();
                      }
                      case "2": {
                          controler.Show_transports();
                      }
                      case "3": {
                          controler.Remove_transport();
                      }
                  }
              }
              case "3": {
                  String[] sites = new String[]{"Add new site", "Show sites List", "Remove site"};
                  String option = null;
                  for (int i = 0; i < sites.length; i++) {
                      System.out.println(i+1 + ". " + sites[i]);
                  }
                  option = scan.nextLine();
                  switch (option) {
                      case "1": {
                          controler.Add_site();
                      }
                      case "2": {
                          controler.Show_sites();
                      }
                      case "3": {
                          controler.Remove_site();
                      }
                  }
              }
              case "4": {
                  String[] suppliers = new String[]{"Add new Truck", "Show trucks List", "Remove truck"};
                  String option = null;
                  for (int i = 0; i < suppliers.length; i++) {
                      System.out.println(i+1 + ". " + suppliers[i]);
                  }
                  option = scan.nextLine();
                  switch (option) {
                      case "1": {
                          controler.Add_truck();
                      }
                      case "2": {
                          controler.Show_trucks();
                      }
                      case "3": {
                          controler.Remove_truck();
                      }
                  }
              }
            }
        } while (!choice.equals("5"));
    }
}
