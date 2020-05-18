package Data_Layer.DAOs;

import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Address;

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
}
