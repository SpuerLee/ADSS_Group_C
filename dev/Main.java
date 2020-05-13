import Presentation_Layer.Transportations.TransportationMenu;
import Presentation_Layer.Workers.HR;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
//        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:DB/example.db")){
//            System.out.println("Open DB");
//
//            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Product WHERE barcode = 1");
//            ResultSet res = pstmt.executeQuery();
//
//            System.out.println("barcode: " + res.getInt("barcode") + " name: " + res.getString("name"));
//
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }

        while (true)
        {
            Scanner scan = new Scanner(System.in);
            String option = "";
            String[] main_menu = new String[]{"HR", "TransportationMenu", "Exit"};
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
