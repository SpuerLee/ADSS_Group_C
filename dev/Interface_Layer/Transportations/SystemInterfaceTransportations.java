package Interface_Layer.Transportations;

import Business_Layer.Service;
import Business_Layer.Controllers.Site_Controller;
import Business_Layer.Transportations.Buisness_Exception;
import Business_Layer.Transportations.Controllers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SystemInterfaceTransportations {


    private Service service = Service.getInstance();
    private Trucks_Controller trucks_controller = Trucks_Controller.getInstance();
    private Site_Controller site_controller = Site_Controller.getInstance();
    private Transportation_Controller transportation_controller = Transportation_Controller.getInstance();
    private Missing_items_Controller missing_items_controller = Missing_items_Controller.getInstance();
    private Drivers_Controller drivers_controller = Drivers_Controller.getInstance();
    private Scanner scan = new Scanner(System.in);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static class SingletonControler {
        private static SystemInterfaceTransportations instance = new SystemInterfaceTransportations();
    }

    private SystemInterfaceTransportations() {
        // initialization code..
    }

    public static SystemInterfaceTransportations getInstance() {
        return SystemInterfaceTransportations.SingletonControler.instance;
    }

    public void uploadData() {
        service.uploadData();
    }

    static String Print_error(Exception e) {
        Throwable current = e;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        return current.getMessage();
    }


    public void Change_remove_store_or_supplier(int transportationID) {
        try {
            boolean OkToRemove= false, toBreak=false;
            while (!OkToRemove)
            {
                System.out.println(transportation_controller.store_and_supplier_list(transportationID));
                System.out.println("Please choose store id to remove from the transportation, if you want to choose more than one,\n" +
                        "please separate them by space, if you dont want to remove store press enter\n" +
                        "write Cancel fo exit");
                String[] stores = scan.nextLine().split(" ");
                for (String s:stores)
                {
                    if(s.equals("Cancel"))
                        toBreak=true;
                }
                if (toBreak)
                {
                    break;
                }
                System.out.println("Please choose supplier id to remove from the transportation, if you want to choose more than one,\n" +
                        "please separate them by space, if you dont want to remove supplier press enter\n" +
                        "write Cancel fo exit");
                String[] suppliers = scan.nextLine().split(" ");
                for (String s:suppliers)
                {
                    if(s.equals("Cancel"))
                        toBreak=true;
                }
                if (toBreak)
                {
                    break;
                }
                String out= transportation_controller.RemoveSites(transportationID,stores,suppliers);
                if (out=="Ok")
                {
                    OkToRemove=true;
                }
                else
                {
                    System.out.println(out);
                }

            }
            if (OkToRemove)
            {
                System.out.println("The sites remove successfully\n");
            }



        } catch (Exception e) {
            System.out.println(e);
            System.out.println("The could not remove the sites\n");
        }
    }

    public void Change_truck_and_driver(int transportationID) {
        try {
            Date date = transportation_controller.Free_truck_and_driver(transportationID);
            boolean find_truck_driver= false;
            String driverId = "";
            String truckId = "";
            while (!find_truck_driver) {
                String freeTrucks = trucks_controller.getFreeTrucks(date);
                if (freeTrucks != "") {
                    System.out.println("The trucks available for the date are:");
                    System.out.println(freeTrucks);
                    System.out.println("Please choose truck to transportation by it's id");
                    truckId = scan.nextLine();
                    String freeDrivers = drivers_controller.getDriverToTrucks(truckId, date);
                    if (freeDrivers != "") {
                        System.out.println("The Drivers available for the date are and truck:");
                        System.out.println(freeDrivers);
                        System.out.println("Please choose Driver to transportation by it's id");
                        driverId = scan.nextLine();
                        find_truck_driver = true;
                    } else {
                        System.out.println("There are no Drivers available for the truck at this date");
                    }
                }
            }
            transportation_controller.Change_truck_and_driver(transportationID,
                    Integer.parseInt(driverId),Integer.parseInt(truckId));
            System.out.println("The truck and driver change successfully\n");
        } catch (Exception e) {
            transportation_controller.Change_Back_truck_and_driver(transportationID);
            System.out.println("could not change truck and driver\n");
        }
    }


    public HashMap<Boolean,Integer> Truck_weight_in_supplier() {
        HashMap<Boolean,Integer> output= new HashMap<>();
        String result = transportation_controller.Show_transports();
        if (result.equals("")) {
            System.out.println("There are no transports to show" + "\n");
        } else {
            System.out.println(result);
            System.out.println("Please choose transportation id to enter truck weight");
            String transportationId = scan.nextLine();
            System.out.println("Enter weight of truck");
            String truckWeightSTR = scan.nextLine();
            Boolean canSet= transportation_controller.SetTruckWeight(transportationId,truckWeightSTR);
            if(canSet==null)
            {
                System.out.println("Incorrect input");
                return output;
            }
            else if(canSet)
            {
                System.out.println("The truck weight change successfully\n");
                output.put(true,Integer.parseInt(transportationId));
                return output;
            }
            else
            {
                System.out.println("Truck weight exceeds the maximum allowed\n");
                output.put(false,Integer.parseInt(transportationId));

            }
        }
        return output;

    }


    public void Complete_stock_missing() {
        try {
            System.out.println(missing_items_controller.getMissingItemsStores());
            System.out.println("Please choose store to transportation by id");
            String storeId = scan.nextLine();
            System.out.println("Please choose area to transportation from the following choices");
            System.out.println(site_controller.getSupplierAreaByStore(storeId));
            String area = scan.nextLine();
            System.out.println("Please choose suppliers to transportation from the following, if you want to choose more than one, please separate them by space");
            System.out.print(site_controller.getSupplierByStoreArea(storeId, area));
            String[] suppliers = scan.nextLine().split(" ");
            boolean find_truck_driver = false;
            Date date = new Date();
            String driverId = "";
            String truckId = "";
            while (!find_truck_driver) {
                System.out.println("Please choose date to transportation by the pattern dd-MM-yyyy");
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                try {
                    date = simpleDateFormat.parse(scan.nextLine());
                } catch (ParseException e) {
                    e.getErrorOffset();
                }
                String freeTrucks = trucks_controller.getFreeTrucks(date);
                if (freeTrucks != "") {
                    System.out.println("The trucks available for the date are:");
                    System.out.println(freeTrucks);
                    System.out.println("Please choose truck to transportation by it's id");
                    truckId = scan.nextLine();
                    String freeDrivers = drivers_controller.getDriverToTrucks(truckId, date);
                    if (freeDrivers != "") {
                        System.out.println("The Drivers available for the date are and truck:");
                        System.out.println(freeDrivers);
                        System.out.println("Please choose Driver to transportation by it's id");
                        driverId = scan.nextLine();
                        find_truck_driver = true;
                    } else {
                        System.out.println("There are no Drivers available for the truck at this date");
                    }
                } else {
                    System.out.println("No trucks available on date");
                }
            }

            List<Integer> stores = new LinkedList<Integer>();
            stores.add(Integer.parseInt(storeId));
            List<Integer> supplier_list = new LinkedList<Integer>();
            for (String s : suppliers) {
                supplier_list.add(Integer.parseInt(s));
            }
            if (transportation_controller.createTransportation(date, LocalTime.parse("12:00:00"), Integer.parseInt(driverId),
                    Integer.parseInt(truckId), supplier_list, stores)) {
                System.out.println("The transport was registered successfully" + "\n");
            }
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public void Regular_stock_transport() {
        try {
            System.out.println(transportation_controller.get_area_for_suppliers()); //print all the suppliers
            System.out.println("Please choose area for the suppliers");
            String area = scan.nextLine();
            System.out.println(site_controller.getSuppliersbyarea(area));
            System.out.println("Please choose suppliers to transportation by id , if there are many please separate by space"); //choose supplier
            String[] supplier = scan.nextLine().split(" ");
            System.out.println(transportation_controller.get_area_for_stores()); //show all the area with all the stores in it
            System.out.println("Please choose area for stors to transportation"); //chose area
            String area1 = scan.nextLine();
            System.out.println(site_controller.get_Stores_By_specific_area(area1)); //show all the stores in the area
            System.out.println("Please choose stores (by id) to make transportation");
            String[] stores = scan.nextLine().split(" ");
            List<Integer> stores1 = new LinkedList<>();
            for (String store : stores) {
                stores1.add(Integer.parseInt(store));
            }
            List<Integer> suppliers = new LinkedList<>();
            for (String supplier1 : supplier) {
                suppliers.add(Integer.parseInt(supplier1));
            }
            boolean find_truck_driver = false;
            Date date = new Date();
            String driverId = "";
            String truckId = "";
            while (!find_truck_driver) {
                System.out.println("Please choose date to transportation by the pattern dd-MM-yyyy");
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                try {
                    date = simpleDateFormat.parse(scan.nextLine());
                } catch (ParseException e) {
                    System.out.println(Print_error(e));
                }
                String freeTrucks = trucks_controller.getFreeTrucks(date);
                if (freeTrucks != "") {
                    System.out.println("The trucks available for the date are:");
                    System.out.println(freeTrucks);
                    System.out.println("Please choose truck to transportation by it's id");
                    truckId = scan.nextLine();
                    String freeDrivers = drivers_controller.getDriverToTrucks(truckId, date);
                    if (freeDrivers != "") {
                        System.out.println("The Drivers available for the date are and truck:");
                        System.out.println(freeDrivers);
                        System.out.println("Please choose Driver to transportation by it's id");
                        driverId = scan.nextLine();
                        find_truck_driver = true;
                    } else {
                        System.out.println("There are no Drivers available for the truck at this date");
                    }
                } else {
                    System.out.println("No trucks available on date");
                }
            }

            for (String suppller : supplier) {
                System.out.println("For supplier " + suppller + " enter the next detalis");
                for (String store : stores) {
                    System.out.println("Do you want that this supplier will supply products for stroe :" + store + " yes/no");
                    String answer = scan.nextLine();
                    if (answer.equals("yes")) {
                        boolean exit = false;
                        HashMap<String, Integer> add = new HashMap<>();
                        System.out.println("Please enter a product and the quantity required seperate by space");
                        System.out.println("Enter end to the next store");
                        while (!exit) {
                            String[] items = scan.nextLine().split(" ");
                            if (items[0].equals("end")) {
                                exit = true;
                            } else {
                                try {
                                    add.put(items[0], Integer.parseInt(items[1]));
                                } catch (Exception e) {
                                    exit = true;
                                }

                            }
                        }
                        transportation_controller.addItemFiletotransport(add, Integer.parseInt(store), Integer.parseInt(suppller));
                    }
                }
            }
            transportation_controller.createRegularTransportation(date, LocalTime.parse("12:00:00"), Integer.parseInt(driverId),
                    Integer.parseInt(truckId), suppliers, stores1);
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
        System.out.println("The transport was registered successfully" + "\n");
    }

    //Drivers

    public void Add_driver() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter driver name");
        String name = scan.nextLine();
        List<String> list = new LinkedList<>();
        System.out.println("Please enter driver's license (C/C1) " + "\n" + "If there many than enter license and than separate them by space.");
        String[] licenses = scan.nextLine().split(" ");
        for (String license : licenses)
            list.add(license);
        drivers_controller.addDriver(name, list);
        System.out.println("The driver was added successfully" + "\n");

    }

    public void Show_drivers_List() {
        try {
            String result = drivers_controller.showDrivers();
            System.out.println(result);
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public void Remove_driver() {
        try {
            System.out.println(drivers_controller.showDrivers());
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter the driver's id that you would like to delete");
            String number = scan.nextLine();
            drivers_controller.removeDriver(new Integer(number));
            System.out.println("The driver has removed successfully+\n");
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }
    // Transportations

    public void Show_transports() {
        String result = transportation_controller.Show_transports();
        if (result.equals("")) {
            System.out.println("There are no transports to show" + "\n");
        } else {
            System.out.println(result);
        }
    }

    public void Remove_transport() {
        try {
            String transport = transportation_controller.getTransport_id();
            Scanner scan = new Scanner(System.in);
            System.out.println("The transport that can be deleted (represented by their ID) are: ");
            System.out.println(transport);
            System.out.println("Please enter the transport id that you would like to delete");
            String id = scan.nextLine();
            transportation_controller.delete_Transport(id);
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    //Sites

    public void Add_site() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose site's type store/supplier"); //choose supplier
        String type = scan.nextLine();
        System.out.println("Please choose the name of the site"); //chose area
        String name = scan.nextLine();
        System.out.println("Please choose city of the site");
        String city = scan.nextLine();
        System.out.println("Please choose street of the site");
        String street = scan.nextLine();
        System.out.println("Please choose street's number of the site");
        String number = scan.nextLine();
        System.out.println("Please enter name of contact for the site");
        String name_of_contact = scan.nextLine();
        System.out.println("Please enter contact's person phone");
        String phone = scan.nextLine();
        System.out.println("Please choose site's area (A/B/C/D)");
        String area = scan.nextLine();
        boolean result = site_controller.addsite(type, name, city, street, number, name_of_contact, phone, area);
        if (result) {
            System.out.println("The site was added successfully\n");
        } else {
            System.out.println("Input error\n");
        }


    }

    public void Show_sites() {
        try {
            String result = site_controller.showsite();
            System.out.println(result);
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }


    //Trucks

    public void Add_truck() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter truck's license_number");
            String number = scan.nextLine();
            System.out.println("Please enter licenses_types separated by a space (C/C1)");
            String type = scan.nextLine();
            String[] license = type.split(" ");
            List<String> list = new LinkedList<>();
            for (String L1 : license) {
                list.add(L1);
            }
            System.out.println("Please enter truck's model");
            String model = scan.nextLine();
            System.out.println("Please enter truck's weight");
            String weight = scan.nextLine();
            System.out.println("Please enter truck's max weight");
            String max_weight = scan.nextLine();
            boolean result = trucks_controller.addTruck(number, list, model, weight, max_weight);
            System.out.println("Truck was added successfully");
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public void Show_trucks() {
        try {
            String result = trucks_controller.showtrucks();
            System.out.println(result);
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public void Remove_truck() {
        try {
            System.out.println(trucks_controller.showtrucks());
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter the truck's id that you would like to delete");
            String id = scan.nextLine();
            trucks_controller.removeTruck(Integer.parseInt(id));
            System.out.println("The truck has removed successfully" + "\n");

        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }

    }
}
