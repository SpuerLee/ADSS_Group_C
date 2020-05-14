package Presentation;

import DataAccess.SupInvDBConn;
import Inventory.View.InvService;
import Suppliers.Presentation.MainMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class WelcomeMenu {

    MainMenu supplierMenu;
    InvService inventoryMenu;

    public WelcomeMenu(){
        supplierMenu = new MainMenu();
        inventoryMenu = InvService.getInstance();
    }

    public void start(){
        int option = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        inventoryMenu.loadDB();

        while(true){
            System.out.println("1) Supplier menu\n2) Inventory menu\n3) Close");
            System.out.print("Option: ");

            try {
                option = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println("Invalid option");
            }

            if(option == 1){
                supplierMenu.apply();
            } else if(option == 2){
                inventoryMenu.mainLoop();
            } else if(option == 3){
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
        SupInvDBConn.closeConn();
    }
}
