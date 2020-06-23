import Presentation.WelcomeMenu;
import Sup_Inv.DataAccess.SupInvDBConn;
import Trans_HR.Business_Layer.Transportations.Utils.Buisness_Exception;
import Trans_HR.Presentation_Layer.Workers.HR;

import java.sql.SQLException;

public class Main {
    public static void main (String[] argv) throws Buisness_Exception {
        HR.getAllSN();
        WelcomeMenu menu = new WelcomeMenu();

        menu.newStart();
        try {
            SupInvDBConn.getInstance().getConn().close();
        } catch (SQLException throwables) {
        }
    }
}
