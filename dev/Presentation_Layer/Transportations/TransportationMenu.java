package Presentation_Layer.Transportations;

import Interface_Layer.Transportations.SystemInterfaceTransportations;

import java.util.HashMap;
import java.util.Scanner;

public class TransportationMenu {
    public static void Menu() {
        SystemInterfaceTransportations systemInterfaceTransportations = SystemInterfaceTransportations.getInstance();
//        if (args.length > 0 && args[0].equals("upload"))
        systemInterfaceTransportations.uploadData();
        String choice = "0";
        do {
            System.out.println("Please choose your option:");
            String[] A = new String[]{"Transportations", "Drivers", "Sites", "Trucks", "Quit"};
            //  String choice = null;
            for (int i = 0; i < A.length; i++) {
                System.out.println(i + 1 + ". " + A[i]);
            }
            Scanner scan = new Scanner(System.in);

            choice = scan.nextLine();
            if (choice.equals("1")) {
                String[] transports = new String[]{"Add new transport", "Show transport list", "Remove transport",
                        "Enter truck weight at supplier","Cancel"};
                String option = "";
                for (int i = 0; i < transports.length; i++) {
                    System.out.println(i + 1 + ". " + transports[i]);
                }
                option = scan.nextLine();
                if (option.equals("1")) { //Add new transport
                    String option2 = "";
                    String[] transports_type = new String[]{"Complete stock missing", "Routine transport","Cancel"};
                    for (int i = 0; i < transports_type.length; i++) {
                        System.out.println(i + 1 + ". " + transports_type[i]);
                    }
                    option2 = scan.nextLine();
                    if (option2.equals("1"))
                        systemInterfaceTransportations.Complete_stock_missing();
                    else if (option2.equals("2"))
                        systemInterfaceTransportations.Regular_stock_transport();
                }else if (option.equals("2")) {
                    systemInterfaceTransportations.Show_transports();
                }
                else if (option.equals("3")) {
                    systemInterfaceTransportations.Remove_transport();
                }
                else if (option.equals("4")) {
                    HashMap<Boolean,Integer> check= systemInterfaceTransportations.Truck_weight_in_supplier();
                    if(check.containsKey(false))
                    {
                        System.out.println("Choose how to fix the transport");
                        String option2 = "";
                        String[] transports_change = new String[]{"Change Truck and driver",
                                "Remove Store/supplier and there items ", "Cancel"};
                        for (int i = 0; i < transports_change.length; i++) {
                            System.out.println(i + 1 + ". " + transports_change[i]);
                        }
                        option2 = scan.nextLine();
                        if (option2.equals("1"))
                            systemInterfaceTransportations.Change_truck_and_driver(check.get(false));
                        else if (option2.equals("2"))
                            systemInterfaceTransportations.Change_remove_store_or_supplier(check.get(false));
                    }
                }
            } else if (choice.equals("2")) {
                String[] drivers = new String[]{"Add new driver", "Show drivers list", "Remove driver","Cancel"};
                String driver_option = null;
                for (int i = 0; i < drivers.length; i++) {
                    System.out.println(i + 1 + ". " + drivers[i]);
                }
                driver_option = scan.nextLine();
                if (driver_option.equals("1")) {
                    systemInterfaceTransportations.Add_driver();
                } else if (driver_option.equals("2"))
                    systemInterfaceTransportations.Show_drivers_List();
                else if (driver_option.equals("3"))
                    systemInterfaceTransportations.Remove_driver();
            } else if (choice.equals("3")) {
                String[] sites = new String[]{"Add new site", "Show sites List","Cancel"};
                String option = null;
                for (int i = 0; i < sites.length; i++) {
                    System.out.println(i + 1 + ". " + sites[i]);
                }
                option = scan.nextLine();
                if (option.equals("1"))
                    systemInterfaceTransportations.Add_site();
                else if (option.equals("2"))
                    systemInterfaceTransportations.Show_sites();
            } else if (choice.equals("4")) {
                String[] suppliers = new String[]{"Add new Truck", "Show trucks List", "Remove truck","Cancel"};
                String option = null;
                for (int i = 0; i < suppliers.length; i++) {
                    System.out.println(i + 1 + ". " + suppliers[i]);
                }
                option = scan.nextLine();
                if (option.equals("1"))
                    systemInterfaceTransportations.Add_truck();
                else if (option.equals("2"))
                    systemInterfaceTransportations.Show_trucks();
                else if (option.equals("3"))
                    systemInterfaceTransportations.Remove_truck();
            }
        }
        while (!choice.equals("5"));

    }
}
