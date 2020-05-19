package Data_Layer.DAOs;

import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class address_DAO {
    public void insert(){

    }

    public void delete(){

    }

    public void update(){

    }

    public dummy_Address select(int Sn){
        String query="SELECT * FROM Address";
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            return new dummy_Address(rs2.getString("City"),rs2.getString("Street"),rs2.getInt("Number"),rs2.getInt("SN"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }
    private void executeQuery(String query){
        try {
            // java.sql.Date sqlDate = new java.sql.Date(worker.getStart_Date().getTime());
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            //statement.setDate(7,sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAll() {
        String delete = "DELETE from Address;";
        executeQuery(delete);
    }

    public int getAddressSn() {
        String query = "select SN from Address\n" +
                " ORDER BY SN DESC\n" +
                " LIMIT 1;";
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            rs2.next();
            return rs2.getInt("SN");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }
}
