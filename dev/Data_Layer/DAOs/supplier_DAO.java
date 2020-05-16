package Data_Layer.DAOs;

import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Address;
import Data_Layer.Dummy_objects.dummy_store;
import Data_Layer.Dummy_objects.dummy_supplier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class supplier_DAO {

    address_DAO address_dao=new address_DAO();
    public void insert(dummy_supplier supplier){
        String query="INSERT INTO \"main\".\"Supplieres\"\n" +
                "(\"SN\", \"Name\", \"Phone\", \"ContactName\", \"AddressSN\", \"AreaSN\")\n" +
                String.format("VALUES ('%d', '%s', '%s', '%s', '%d', '%d');", supplier.getId(), supplier.getName(), supplier.getPhone(), supplier.getContact_name(), supplier.getAddress_Sn() , supplier.getAreaSn());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String address_query="INSERT INTO \"main\".\"Address\"\n" +
                "(\"SN\", \"City\", \"Street\", \"Number\")\n" +
                String.format("VALUES ('%d', '%s', '%s', '%d');", supplier.getAddress_Sn(), supplier.getCity(), supplier.getStreet(), supplier.getNumber());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(address_query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public dummy_supplier select_by_SupplieresId(int id){
        String selectQuery = String.format("select * from Supplieres where Supplieres.SN = '%d'",id);
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            int AddressSn=rs2.getInt("AddressSN");
            dummy_Address dummy_address=address_dao.select(AddressSn);
            return new dummy_supplier(rs2.getString("Phone"),rs2.getString("ContactName"),rs2.getString("Name"),rs2.getInt("SN"),dummy_address.getCity(),dummy_address.getStreet(),dummy_address.getNumber(),AddressSn,rs2.getInt("AreaSN"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public List<dummy_supplier> select_by_area(int areaSn){
        String selectQuery = String.format("select * from Stores where Supplieres.AreaSN = '%d'",areaSn);
        List<dummy_supplier> list=new LinkedList<>();
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            while (rs2.next()) {
                int AddressSn=rs2.getInt("AddressSN");
                dummy_Address dummy_address=address_dao.select(AddressSn);
                list.add(new dummy_supplier(rs2.getString("Phone"),rs2.getString("ContactName"),rs2.getString("Name"),rs2.getInt("SN"),dummy_address.getCity(),dummy_address.getStreet(),dummy_address.getNumber(),AddressSn,rs2.getInt("AreaSN")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public void delete(int sn) {
        String sql = "DELETE FROM \"main\".\"Supplieres\"\n WHERE SN = " + sn;
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<dummy_supplier> select(){
        String query="SELECT * FROM Supplieres";
        List<dummy_supplier> list_to_return=new LinkedList<>();
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            while (rs2.next()) {
                int AddressSn=rs2.getInt("AddressSN");
                dummy_Address dummy_address=address_dao.select(AddressSn);
                list_to_return.add(new dummy_supplier(rs2.getString("Phone"),rs2.getString("ContactName"),rs2.getString("Name"),rs2.getInt("SN"),dummy_address.getCity(),dummy_address.getStreet(),dummy_address.getNumber(),AddressSn,rs2.getInt("AreaSN")));
            }
            return list_to_return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }
}
