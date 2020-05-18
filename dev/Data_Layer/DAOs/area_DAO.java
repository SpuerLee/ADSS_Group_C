package Data_Layer.DAOs;

import Data_Layer.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;

public class area_DAO {
    public void insert(int SN, String area){
        String query = MessageFormat.format(
                "INSERT INTO \"main\".\"Area\"\n(\"SN\",\"AreaName\")\n{0}", String.format("VALUES ('%d','%s');",
                        SN,area));

        executeQuery(query);

    }

    private void executeQuery(String query){
        try {
            // java.sql.Date sqlDate = new java.sql.Date(worker.getStart_Date().getTime());
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            //statement.setDate(7,sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void delete(){

    }

    public void update(){

    }
}
