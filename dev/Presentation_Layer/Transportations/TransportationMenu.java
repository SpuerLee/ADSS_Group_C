package Presentation_Layer.Transportations;

import Business_Layer.Modules.License;
import Business_Layer.Transportations.Utils.Buisness_Exception;
import Interface_Layer.Transportations.SystemInterfaceTransportations;
import javafx.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class TransportationMenu {

    private static Scanner scan = new Scanner(System.in);
    private static SystemInterfaceTransportations systemInterfaceTransportations = SystemInterfaceTransportations.getInstance();

    public static void Menu() {

//        if (args.length > 0 && args[0].equals("upload"))
        systemInterfaceTransportations.uploadData();
        String choice = "0";
        do {
            System.out.println("Please choose your option:");
            String[] A = new String[]{"Transportations", "Suppliers", "Trucks","Missing_items","Quit"};
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
                    if (option2.equals("1"))Complete_Stock_Missing();
                    else if (option2.equals("2"))Regular_stock_transport();
                }
                else if (option.equals("2"))
                    Show_transports();
                else if (option.equals("3"))
                    Remove_transport();
                else if (option.equals("4")) {
                    Pair<Boolean,Integer> check= Truck_weight_in_supplier();
                    if(check!=null)
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
                            Change_truck_and_driver(check.getValue());
                        else if (option2.equals("2"))
                            Change_remove_store_or_supplier(check.getValue());
                    }
                }
            }

            else if (choice.equals("2")) {
                String[] sites = new String[]{"Add new Suppliers", "Show Suppliers List", "Cancel"};
                String option = null;
                for (int i = 0; i < sites.length; i++) {
                    System.out.println(i + 1 + ". " + sites[i]);
                }
                option = scan.nextLine();
                if (option.equals("1"))
                    Add_supplier();
                else if (option.equals("2"))
                    Show_supplier();
            }
            else if (choice.equals("3")) {
                String[] suppliers = new String[]{"Add new Truck", "Show trucks List", "Remove truck","Cancel"};
                String option = null;
                for (int i = 0; i < suppliers.length; i++) {
                    System.out.println(i + 1 + ". " + suppliers[i]);
                }
                option = scan.nextLine();
                if (option.equals("1"))
                    Add_truck();
                else if (option.equals("2"))
                    Show_trucks();
                else if (option.equals("3"))
                    Remove_truck();
            }

            else if (choice.equals("4")) {
                String[] suppliers = new String[]{"Add new Truck", "Show trucks List", "Remove truck","Cancel"};
                String option = null;
                for (int i = 0; i < suppliers.length; i++) {
                    System.out.println(i + 1 + ". " + suppliers[i]);
                }
                option = scan.nextLine();
//                if (option.equals("1"))
//                    systemInterfaceTransportations.Add_truck();
//                else if (option.equals("2"))
//                    systemInterfaceTransportations.Show_trucks();
//                else if (option.equals("3"))
//                    systemInterfaceTransportations.Remove_truck();
            }
        }

        while (!choice.equals("5"));
    }

    static String Print_error(Exception e) {
        Throwable current = e;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        return current.getMessage();
    }

    public static void Complete_Stock_Missing() {
        try {
            List<String> MissingItemsStores = systemInterfaceTransportations.getMissingItemsStores();
            MissingItemsStores.forEach(System.out::println);
            System.out.println("Please choose store to transportation by id");
            Integer storeId = parseToNumber(scan.nextLine());
            checkIdInList(MissingItemsStores,storeId.toString());
            List<Integer> stores = new LinkedList<Integer>();
            stores.add(storeId);

            System.out.println("Please choose area to transportation from the following choices");
            List<String> SupplierAreaByStore = systemInterfaceTransportations.getSupplierAreaByStore(storeId);
            System.out.println(SupplierAreaByStore);
            String area = scan.nextLine();
            checkIdInList(SupplierAreaByStore,area);

            System.out.println("Please choose suppliers to transportation from the following, if you want to choose more than one, please separate them by space");
            List<String> SupplierByStoreArea = systemInterfaceTransportations.getSupplierByStoreArea(storeId, area);
            SupplierByStoreArea.forEach(System.out::println);
            Integer [] suppliers = parseArrayToNumber(scan.nextLine().split(" "));
            for (Integer id : suppliers ) checkIdInList(SupplierByStoreArea, id.toString());
            List<Integer> supplier_list = Arrays.asList(suppliers);

            boolean find_truck_driver = false;
            Date date = new Date();
            Integer driverId = 0;
            Integer truckId = 0;
            Integer shiftType = 0;
            while (!find_truck_driver) {
                System.out.println("Please choose date to transportation by the pattern dd-MM-yyyy");
                date = parseToDate(scan.nextLine());

                System.out.println("Please choose shiftType by id:");
                List<String> ShiftTypes = systemInterfaceTransportations.Show_shiftTypeList();
                ShiftTypes.forEach(System.out::println);
                shiftType = parseToNumber(scan.nextLine());
                checkIdInList(ShiftTypes,shiftType.toString());
                String shift = systemInterfaceTransportations.getShiftNameByID(shiftType);
                boolean hasKeeper = true;
                for (Integer storeId1: stores)
                {

                    if (!systemInterfaceTransportations.isStoreKeeperAvailable(date,shift,storeId1))
                    {
                        System.out.println("There are no Store Keeper available for store "+storeId1.toString()+
                                "this date and shift");
                        hasKeeper = false;
                    }
                }
                if(!hasKeeper)
                    continue;

                List<String> freeTrucks = systemInterfaceTransportations.getFreeTrucks(date, shiftType);
                if (!freeTrucks.isEmpty()) {
                    System.out.println("The trucks available for the date are:");
                    freeTrucks.forEach(System.out::println);
                    System.out.println("Please choose truck to transportation by it's id");
                    truckId = parseToNumber(scan.nextLine());
                    checkIdInList(freeTrucks,truckId.toString());
                    List<String> licenses = systemInterfaceTransportations.getTruckLicenseList(truckId);

//                    List<String> freeDrivers = systemInterfaceTransportations.getDriverToTrucks(truckId, date);
                    List<String> freeDrivers = systemInterfaceTransportations.getAllDrivers(date,shift,licenses);
                    if (!freeDrivers.isEmpty()) {
                        System.out.println("The Drivers available for the date are and truck:");
                        freeDrivers.forEach(System.out::println);
                        System.out.println("Please choose Driver to transportation by it's id");
                        driverId = parseToNumber(scan.nextLine());
                        checkIdInList(freeDrivers,driverId.toString());
                        find_truck_driver = true;


                    } else {
                        System.out.println("There are no Drivers available for the truck at this date");
                    }
                } else {
                    System.out.println("No trucks available on date");
                }
            }

            systemInterfaceTransportations.createTransportation(date, shiftType, driverId,
                    truckId, supplier_list, stores);
            System.out.println("The transport was registered successfully" + "\n");
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public static void Regular_stock_transport() {
        try {
            List<String> area_for_suppliers = systemInterfaceTransportations.get_area_for_suppliers();
            System.out.println(area_for_suppliers);
            System.out.println("Please choose area for the suppliers");
            String area = scan.nextLine();
            checkIdInList(area_for_suppliers,area);

            List<String> Suppliersbyarea = systemInterfaceTransportations.getSuppliersbyarea(area);
            Suppliersbyarea.forEach(System.out::println);
            System.out.println("Please choose suppliers to transportation by id , if there are many please separate by space"); //choose supplier
            Integer [] supplier = parseArrayToNumber(scan.nextLine().split(" "));
            for (Integer id : supplier ) checkIdInList(Suppliersbyarea, id.toString());

            List<String> area_for_stores = systemInterfaceTransportations.get_area_for_stores();
            System.out.println(area_for_stores);
            System.out.println("Please choose area for stors to transportation");
            String area1 = scan.nextLine();
            checkIdInList(area_for_stores,area1);

            List<String> Stores_By_specific_area = systemInterfaceTransportations.get_Stores_By_specific_area(area1);
            Stores_By_specific_area.forEach(System.out::println);
            System.out.println("Please choose stores to transportation by id , if there are many please separate by space"); //choose supplier
            Integer [] stores = parseArrayToNumber(scan.nextLine().split(" "));
            for (Integer id : stores ) checkIdInList(Stores_By_specific_area, id.toString());


            List<Integer> store_list = Arrays.asList(stores);
            List<Integer> supplier_list = Arrays.asList(supplier);

//            boolean find_truck_driver = false;
//            Date date = new Date();
//            Integer driverId = 0;
//            Integer truckId = 0;
//            while (!find_truck_driver) {
//                System.out.println("Please choose date to transportation by the pattern dd-MM-yyyy");
//                date = parseToDate(scan.nextLine());
//
//                List<String> freeTrucks = systemInterfaceTransportations.getFreeTrucks(date);
//                if (!freeTrucks.isEmpty()) {
//                    System.out.println("The trucks available for the date are:");
//                    freeTrucks.forEach(System.out::println);
//                    System.out.println("Please choose truck to transportation by it's id");
//                    truckId = parseToNumber(scan.nextLine());
//                    checkIdInList(freeTrucks,truckId.toString());
//
//                    List<String> freeDrivers = systemInterfaceTransportations.getDriverToTrucks(truckId, date);
//                    if (!freeDrivers.isEmpty()) {
//                        System.out.println("The Drivers available for the date are and truck:");
//                        freeDrivers.forEach(System.out::println);
//                        System.out.println("Please choose Driver to transportation by it's id");
//                        driverId = parseToNumber(scan.nextLine());
//                        checkIdInList(freeDrivers,driverId.toString());
//                        find_truck_driver = true;
//
//
//                    } else {
//                        System.out.println("There are no Drivers available for the truck at this date");
//                    }
//                } else {
//                    System.out.println("No trucks available on date");
//                }
//            }

            boolean find_truck_driver = false;
            Date date = new Date();
            Integer driverId = 0;
            Integer truckId = 0;
            Integer shiftType = 0;
            while (!find_truck_driver) {
                System.out.println("Please choose date to transportation by the pattern dd-MM-yyyy");
                date = parseToDate(scan.nextLine());

                System.out.println("Please choose shiftType by id:");
                List<String> ShiftTypes = systemInterfaceTransportations.Show_shiftTypeList();
                ShiftTypes.forEach(System.out::println);
                shiftType = parseToNumber(scan.nextLine());
                checkIdInList(ShiftTypes,shiftType.toString());
                String shift = systemInterfaceTransportations.getShiftNameByID(shiftType);
                boolean hasKeeper = true;
                for (Integer storeId1: stores)
                {

                    if (!systemInterfaceTransportations.isStoreKeeperAvailable(date,shift,storeId1))
                    {
                        System.out.println("There are no Store Keeper available for store "+storeId1.toString()+
                                " in this date and shift");
                        hasKeeper = false;
                    }
                }
                if(!hasKeeper)
                    continue;

                List<String> freeTrucks = systemInterfaceTransportations.getFreeTrucks(date, shiftType);
                if (!freeTrucks.isEmpty()) {
                    System.out.println("The trucks available for the date are:");
                    freeTrucks.forEach(System.out::println);
                    System.out.println("Please choose truck to transportation by it's id");
                    truckId = parseToNumber(scan.nextLine());
                    checkIdInList(freeTrucks,truckId.toString());
                    List<String> licenses = systemInterfaceTransportations.getTruckLicenseList(truckId);

//                    List<String> freeDrivers = systemInterfaceTransportations.getDriverToTrucks(truckId, date);
                    List<String> freeDrivers = systemInterfaceTransportations.getAllDrivers(date,shift,licenses);
                    if (!freeDrivers.isEmpty()) {
                        System.out.println("The Drivers available for the date are and truck:");
                        freeDrivers.forEach(System.out::println);
                        System.out.println("Please choose Driver to transportation by it's id");
                        driverId = parseToNumber(scan.nextLine());
                        checkIdInList(freeDrivers,driverId.toString());
                        find_truck_driver = true;


                    } else {
                        System.out.println("There are no Drivers available for the truck at this date");
                    }
                } else {
                    System.out.println("No trucks available on date");
                }
            }

            for (Integer suppller : supplier_list) {
                System.out.println("For supplier " + suppller.toString() + " enter the next detalis");
                for (Integer store : store_list) {
                    System.out.println("Do you want that this supplier will supply products for stroe :" + store.toString() + " yes/no");
                    String answer = scan.nextLine();
                    if (checkYesNo(answer)) {
                        boolean exit = false;
                        List<Pair<String, Integer>> toAdd = new LinkedList<>();
//                        HashMap<String, Integer> add = new HashMap<>();
                        System.out.println("Please enter a product and the quantity required seperate by space");
                        System.out.println("Enter end to the next store");
                        while (!exit) {
                            String[] items = scan.nextLine().split(" ");
                            if (items.length<2) {
                                exit = true;
                            }
                            else{
                                toAdd.add(checkItem(items));
//                                add.put(items[0], Integer.parseInt(items[1]));
                            }
                        }
                        systemInterfaceTransportations.addItemFiletotransport(toAdd, store, suppller);
                        System.out.println("The transport was registered successfully\n");
                    }
                }
            }
            systemInterfaceTransportations.createRegularTransportation(date, shiftType, driverId,
                    truckId, supplier_list, store_list);
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public static void Show_transports() {
        try {
            List<String> result = systemInterfaceTransportations.Show_transports();
            result.forEach(System.out::println);
        }
        catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }

    }

    public static void Remove_transport() {
        try {
            List<String> transportList = systemInterfaceTransportations.getTransport_id();
            System.out.println("The transport that can be deleted (represented by their ID) are: ");
            System.out.println(transportList);
            System.out.println("Please enter the transport id that you would like to delete");
            Integer id = parseToNumber(scan.nextLine());
            checkIdInList(transportList, id.toString());
            systemInterfaceTransportations.delete_Transport(id);
            System.out.println("The transport was deleted successfully" + "\n");
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public static void Change_truck_and_driver(int transportationID) {
        try {
            Pair<Date,Integer> transportationTime= systemInterfaceTransportations.Free_truck_and_driver(transportationID);
            Date date = transportationTime.getKey();
            Integer DepartureTime = transportationTime.getValue();
            boolean find_truck_driver= false;
            Integer driverId = 0;
            Integer truckId = 0;
            while (!find_truck_driver) {
                List<String> freeTrucks = systemInterfaceTransportations.getFreeTrucks(date, DepartureTime);
                if (!freeTrucks.isEmpty()) {
                    System.out.println("The trucks available for the date are:");
                    freeTrucks.forEach(System.out::println);
                    System.out.println("Please choose truck to transportation by it's id");
                    truckId = parseToNumber(scan.nextLine());
                    checkIdInList(freeTrucks,truckId.toString());

                    List<String> freeDrivers = systemInterfaceTransportations.getDriverToTrucks(truckId, date);
                    if (!freeDrivers.isEmpty()) {
                        System.out.println("The Drivers available for the date are and truck:");
                        freeDrivers.forEach(System.out::println);
                        System.out.println("Please choose Driver to transportation by it's id");
                        driverId = parseToNumber(scan.nextLine());
                        checkIdInList(freeDrivers,driverId.toString());
                        find_truck_driver = true;


                    } else {
                        System.out.println("There are no Drivers available for the truck at this date");
                    }
                } else {
                    System.out.println("No trucks available on date");
                }
            }
            boolean Succeeded = systemInterfaceTransportations.Change_truck_and_driver(transportationID,driverId,truckId);
            if (Succeeded)
                System.out.println("The truck and driver change successfully\n");
            else
                System.out.println("could not change truck and driver\n");
        } catch (Exception e) {
            systemInterfaceTransportations.Change_Back_truck_and_driver(transportationID);
            System.out.println("could not change truck and driver\n");
        }
    }

    public static Pair<Boolean,Integer> Truck_weight_in_supplier() {
        try {
            List<String> result = systemInterfaceTransportations.Show_transports();
            result.forEach(System.out::println);
            System.out.println("Please choose transportation id to enter truck weight");
            int transportationId = parseToNumber(scan.nextLine());
            try {

                System.out.println("Enter weight of truck");
                double truckWeight = parseToDouble(scan.nextLine());
                systemInterfaceTransportations.SetTruckWeight(transportationId,truckWeight);
                System.out.println("The truck weight change successfully\n");
                return new Pair<Boolean,Integer>(true, transportationId);

            }catch (Buisness_Exception e) {
                System.out.println(Print_error(e));
                return new Pair<Boolean,Integer>(false, transportationId);
            }

        }
        catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
            return null;
        }
    }

    public static void Change_remove_store_or_supplier(int transportationID) {
        try {
            boolean OkToRemove= false, toBreak=false;
            while (!OkToRemove)
            {
                List<String> store_list = systemInterfaceTransportations.store_list_by_transportationID(transportationID);
                store_list.forEach(System.out::println);
                System.out.println("Please choose store id to remove from the transportation, if you want to choose more than one,\n" +
                        "please separate them by space, if you dont want to remove store press enter\n" +
                        "write Cancel fo exit");
                Integer [] stores = parseArrayToNumber(scan.nextLine().split(" "));
                for (Integer id : stores ) checkIdInList(store_list, id.toString());

                List<String> supplier_list = systemInterfaceTransportations.supplier_list_by_transportationID(transportationID);
                supplier_list.forEach(System.out::println);
                System.out.println("Please choose supplier id to remove from the transportation, if you want to choose more than one,\n" +
                        "please separate them by space, if you dont want to remove supplier press enter\n" +
                        "write Cancel fo exit");
                Integer [] suppliers = parseArrayToNumber(scan.nextLine().split(" "));
                for (Integer id : suppliers ) checkIdInList(supplier_list, id.toString());

                String out= systemInterfaceTransportations.RemoveSites(transportationID,stores,suppliers);
                if (out=="Ok")
                    OkToRemove=true;
                else
                    System.out.println(out);
            }
            if (OkToRemove)
                System.out.println("The sites remove successfully\n");
        }
        catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }  catch (Exception e) {
            System.out.println("The could not remove the sites\n");
        }
    }

    public static void Add_supplier() {

        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Please choose supplier's area from:");
            List<String> area_list = systemInterfaceTransportations.Show_AreaList();
            System.out.println(area_list);
            String area = scan.nextLine();
            checkIdInList(area_list,area);
            System.out.println("Please choose the name of the supplier"); //chose area
            String name = scan.nextLine();
            System.out.println("Please choose city of the supplier");
            String city = scan.nextLine();
            System.out.println("Please choose street of the supplier");
            String street = scan.nextLine();
            System.out.println("Please choose street's number of the supplier");
            Integer number = parseToNumber(scan.nextLine());
            System.out.println("Please enter name of contact for the supplier");
            String name_of_contact = scan.nextLine();
            System.out.println("Please enter contact's person phone");
            String phone = scan.nextLine();
            boolean result = systemInterfaceTransportations.addsupplier(name, city, street, number, name_of_contact, phone, area);
            if (result) {
                System.out.println("The supplier was added successfully\n");
            } else {
                System.out.println("Input error\n");
            }
        }
        catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }

    }

    public static void Show_supplier() {
        try {
            List<String> result = systemInterfaceTransportations.Show_supplier();
            result.forEach(System.out::println);
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public static void Add_truck() {
        try {
            System.out.println("Please enter truck's license_number");
            int number = parseToNumber(scan.nextLine());
            System.out.println("Please enter licenses_types separated by a space from:");
            List<String> license_list = systemInterfaceTransportations.Show_LicenseList();
            System.out.println(license_list);
            String type = scan.nextLine();
            String[] licenses = type.split(" ");
            List<String> list = Arrays.asList(licenses);
            for (String license : list ) checkIdInList(license_list, license);
            System.out.println("Please enter truck's model");
            String model = scan.nextLine();
            System.out.println("Please enter truck's weight");
            double weight = parseToDouble(scan.nextLine());
            System.out.println("Please enter truck's max weight");
            double max_weight = parseToDouble(scan.nextLine());
            boolean result = systemInterfaceTransportations.addTruck(number, list, model, weight, max_weight);
            System.out.println("Truck was added successfully");
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public static void Show_trucks() {
        try {
            List<String> result = systemInterfaceTransportations.showtrucks();
            result.forEach(System.out::println);
        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }
    }

    public static void Remove_truck() {
        try {
            List<String> trucks = systemInterfaceTransportations.showtrucks();
            trucks.forEach(System.out::println);
            System.out.println("Please enter the truck's id that you would like to delete");
            Integer id = parseToNumber(scan.nextLine());
            checkIdInList(trucks, id.toString());
            systemInterfaceTransportations.removeTruck(id);
            System.out.println("The truck has removed successfully" + "\n");

        } catch (Buisness_Exception e) {
            System.out.println(Print_error(e));
        }

    }


    //Valid input check

    public static Integer parseToNumber(String str) throws Buisness_Exception{
        try{
            if(str.equals("Cancel"))
                throw new Buisness_Exception("");
            return Integer.parseInt(str);
        }catch (Exception e){
            throw new Buisness_Exception("-input not a number-\n");
        }
    }

    public static Double parseToDouble(String str) throws Buisness_Exception{
        try{
            if(str.equals("Cancel"))
                throw new Buisness_Exception("");
            return Double.parseDouble(str);
        }catch (Exception e){
            throw new Buisness_Exception("-input not a number-\n");
        }
    }

    public static Date parseToDate(String str) throws Buisness_Exception{
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try{
            if(str.equals("Cancel"))
                throw new Buisness_Exception("");
            return simpleDateFormat.parse(str);
        }catch (Exception e){
            throw new Buisness_Exception("-invaled input date-\n");
        }
    }

    public static Integer [] parseArrayToNumber(String [] arr) throws Buisness_Exception{
        Integer [] out = new Integer[arr.length];
        int i =0;
        for(String str : arr)
        {
            out[i]= parseToNumber(str);
            i++;
        }
        return out;
    }

    public static void checkIdInList(List<String> ls, String str) throws Buisness_Exception{
        if(ls.isEmpty())
            throw new Buisness_Exception("-empty list-\n");
        boolean ifExist = false;
        for(String s : ls)
        {
            if(s.indexOf('.')!=-1)
                s = s.substring(0,s.indexOf('.'));
            if(str.equals(s))
                ifExist = true;
        }
        if (!ifExist)
            throw new Buisness_Exception("-incurrect value-\n");

    }

    public static Boolean checkYesNo(String str) throws Buisness_Exception{
            if(str.equals("Cancel"))
                throw new Buisness_Exception("");
            if(str.equals("yes") || str.equals("y") || str.equals("Yes"))
                return true;
            if(str.equals("no") || str.equals("n") || str.equals("No"))
                return false;
            else
                throw new Buisness_Exception("-Not yes/no input-");
    }

    public static Pair<String, Integer> checkItem(String [] arr) throws Buisness_Exception{
        if(arr[0].equals("Cancel"))
            throw new Buisness_Exception("");
        if(arr.length>2)
            throw new Buisness_Exception("-Invalid input-\n");
        int quantity = parseToNumber(arr[1]);
        return  new Pair<>(arr[0],quantity);

    }


}
