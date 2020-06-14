package Presentation;

import DataAccess.SupInvDBConn;
import Inventory.View.InvService;
import Suppliers.Presentation.MainMenu;
import Trans_HR.Business_Layer.Transportations.Utils.Buisness_Exception;
import Trans_HR.Data_Layer.Mapper;
import Trans_HR.Interface_Layer.Workers.SystemInterfaceWorkers;
import Trans_HR.Presentation_Layer.Transportations.TransportationMenu;
import Trans_HR.Presentation_Layer.Workers.HR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class WelcomeMenu {

    private static MainMenu supplierMenu;
    private static InvService inventoryMenu;
    private static Boolean terminate;
    private static Scanner sc = new Scanner(System.in);

    public WelcomeMenu() {
        supplierMenu = new MainMenu();
        inventoryMenu = InvService.getInstance();
    }

    public void newStart() throws Buisness_Exception {
        while (true) {
            chooseStore_start(sc);
        }
    }

    public static void chooseStore_start(Scanner sc) throws Buisness_Exception {
        try {
            Mapper.getInstance().init();
        } catch (Exception e) {

        }
        System.out.println("1. Choose store");
        System.out.println("2. Add store");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        SystemInterfaceWorkers.getInstance().getStores();
        int userChoose = sc.nextInt();
        if (userChoose == 1) {
            if (!(SystemInterfaceWorkers.getInstance().printAllStores())) {
                // Empty
                chooseStore_start(sc);
            } else {
                // Store.count > 0
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input, please try again");
                    sc.next();
                }
                userChoose = sc.nextInt();
                CurrStore.getInstance().setStore_id(userChoose);
                if (!(SystemInterfaceWorkers.getInstance().setCurrentStore(userChoose))) {
                    chooseStore_start(sc);
                } else {
                    chooseJob(sc);
                }
            }
        } else if (userChoose == 2) {

            Scanner scan = new Scanner(System.in);
            System.out.println("Please choose the name of the store"); //chose area
            String name = scan.nextLine();
            System.out.println("Please choose city of the store");
            String city = scan.nextLine();
            System.out.println("Please choose street of the store");
            String street = scan.nextLine();
            System.out.println("Please choose street's number of the store");
            String number = scan.nextLine();
            System.out.println("Please enter name of contact for the store");
            String name_of_contact = scan.nextLine();
            System.out.println("Please enter contact's person phone");
            String phone = scan.nextLine();
            System.out.println("Please choose store's area (A/B/C/D)");
            String area = scan.nextLine();
            boolean result = false;
            result = SystemInterfaceWorkers.getInstance().addNewStore(name, city, street, number, name_of_contact, phone, area);
            if (result) {
                System.out.println("The store was added successfully\n");
                chooseStore_start(sc);
            } else {
                System.out.println("Input error\n");
                chooseStore_start(sc);
            }
        } else if (userChoose == 0) {
            throw new Buisness_Exception("Going back");
        } else {
            System.out.println("Invalid input, please try again");
            chooseStore_start(sc);
        }
    }

    public static void chooseJob(Scanner sc) throws Buisness_Exception {

        while (true) {
            System.out.println("--------------\nPlease choose your job title:");
            System.out.println("[h] HR\n[s] Storekeeper\n[m] Manager\n[l] Logistic mg\n[b] back");
            System.out.print("Option: ");
            while (!sc.hasNext()) {
                System.out.println("Invalid input, please try again");
                sc.next();
            }
            String ansStr = sc.next();
            if(ansStr.equals("h") || ansStr.equals("H")) {
                HR.workingLoop(sc);
            }
            else if(ansStr.equals("s") || ansStr.equals("S")){
                start_invSup();
            }
            else if(ansStr.equals("m") || ansStr.equals("M")) {
                managerMenu();
            }
            else if(ansStr.equals("l") || ansStr.equals("L")) {
                TransportationMenu.Menu();
            }
            else if(ansStr.equals("b") || ansStr.equals("B")) {
                chooseStore_start(sc);
            }
            else {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    public static void start_invSup() throws Buisness_Exception {
        terminate = false;
        String option = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        inventoryMenu.loadDB();

        while (!terminate) {
            System.out.println("Please choose one of the following options:");
            System.out.println("[s] Supplier menu\n[i] Inventory menu\n[c] Close");
            System.out.print("Option: ");

            try {
                option = reader.readLine();
            } catch (IOException e) {
                System.out.println("Invalid option");
            } catch (Exception e) {
                System.out.println("Something went wrong");
            }

            if (option.equals("s") || option.equals("S")) {
                supplierMenu.apply();
            } else if (option.equals("i") || option.equals("I")) {
                terminate = inventoryMenu.mainLoop();
            } else if (option.equals("c") || option.equals("C")) {
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
        SupInvDBConn.closeConn();
    }

    private static void managerMenu() throws Buisness_Exception {
        while (true) {
            System.out.println("--------------\n");
            System.out.println("\t[s] Shift report\n" +
                    "           \t[w] Workers report\n" +
                            "\t[gr] Get All Items Report \n" +
                            "\t[gi] Get Item Report by id \n" +
                            "\t[gc] Get Item Report By Category \n" +
                            "\t[gs] Get Shortage Item Report\n" +
                            "\t[ss] Show transportation\n" +
                            "\t[st] Show trucks\n" +
                            "\t[b] back\n");
            System.out.print("Option: ");
            while (!sc.hasNext()) {
                System.out.println("Invalid input, please try again");
                sc.next();
            }
            String ansStr = sc.next().toUpperCase();
            if(ansStr.equals("S")) {

            }
            else if(ansStr.equals("W")){

            }
            else if(ansStr.equals("GR")) {

            }
            else if(ansStr.equals("GI")) {

            }
            else if(ansStr.equals("GC")) {

            }
            else if(ansStr.equals("GS")) {

            }
            else if(ansStr.equals("SS")) {

            }
            else if(ansStr.equals("ST")) {

            }
            else if(ansStr.equals("b") || ansStr.equals("B")) {
                chooseJob(sc);
            }
            else {
                System.out.println("Invalid input, please try again");
            }
        }
    }


}