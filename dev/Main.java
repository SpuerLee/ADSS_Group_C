import Business_Layer.Transportations.Utils.Buisness_Exception;
import Presentation_Layer.Transportations.TransportationMenu;
import Presentation_Layer.Workers.HR;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  throws Buisness_Exception {
        System.out.println("Welcome to SuperLee");
        while (true)
        {
            Scanner scan = new Scanner(System.in);
            String option = "";
            String[] main_menu = new String[]{"HR", "Transportation Menu", "Exit"};
            for (int i = 0; i < main_menu.length; i++) {
                System.out.println(i + 1 + ". " + main_menu[i]);
            }
            option = scan.nextLine();
            if (option.equals("1"))
                HR.Menu();
            else if (option.equals("2"))
                TransportationMenu.Menu();
            else if (option.equals("3"))
                break;
        }
    }
}
