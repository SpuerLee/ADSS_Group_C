package Data_Layer.DAOs;

import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

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

    public void delete(Integer SN){
        String address_query="DELETE FROM \"main\".\"Address\"\n" +
                String.format("WHERE SN = '%d';",SN);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(address_query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getNextSN(){
        String query="SELECT MAX(SN) FROM Address";
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
//            System.out.println(rs2.wasNull());
            if(rs2.next()==false)
                return 1;
            else
                return rs2.getInt("MAX(SN)") + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public void update(){

    }

    public List<dummy_Address> selectAll(){
        List<dummy_Address> output = new LinkedList<>();
        String query="SELECT * FROM Address";
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            while (rs2.next()) {
                dummy_Address dummy_address = new dummy_Address(rs2.getInt("SN"),rs2.getString("City"),rs2.getString("Street"),rs2.getInt("Number"));
                output.add(dummy_address);
            }
            return output;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public dummy_Address select(int SN){
        String query="SELECT * FROM Address\n"+
        String.format("WHERE SN = '%d';",SN);;
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            if(rs2.next()==false)
                return null;
            else
                return new dummy_Address(rs2.getInt("SN"),rs2.getString("City"),rs2.getString("Street"),rs2.getInt("Number"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }
}
