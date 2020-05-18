import Business_Layer.Transportations.Utils.Buisness_Exception;
import Data_Layer.DAOs.transportation_DAO;
import Data_Layer.Dummy_objects.dummy_Transportation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main {
    public static void main(String[] args)  throws Buisness_Exception {
//        while (true)
//        {
//            Scanner scan = new Scanner(System.in);
//            String option = "";
//            String[] main_menu = new String[]{"HR", "Transportation Menu", "Exit"};
//            for (int i = 0; i < main_menu.length; i++) {
//                System.out.println(i + 1 + ". " + main_menu[i]);
//            }
//            option = scan.nextLine();
//          /*  if (option.equals("1"))
//             //   HR.Menu(); */
//            if (option.equals("2"))
//                TransportationMenu.Menu();
//            else if (option.equals("3"))
//                break;
//        }

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Date insert= null;
        try {
            insert = simpleDateFormat.parse("10-12-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        transportation_DAO transporatation_dao=new transportation_DAO();
        dummy_Transportation dummy_transportation=transporatation_dao.select_by_TransportationsId(18);

    }
}
