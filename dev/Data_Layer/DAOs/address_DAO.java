package Data_Layer.DAOs;

import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class address_DAO {
    public void insert(dummy_Address dummy_address){
        String address_query="INSERT INTO \"main\".\"Address\"\n" +
                "(\"SN\", \"City\", \"Street\", \"Number\")\n" +
                String.format("VALUES ('%d', '%s', '%s', '%d');", dummy_address.getSN(), dummy_address.getCity(), dummy_address.getStreet(), dummy_address.getNumber());
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(address_query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            return new dummy_Address(rs2.getInt("SN"),rs2.getString("City"),rs2.getString("Street"),rs2.getInt("Number"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }
}
