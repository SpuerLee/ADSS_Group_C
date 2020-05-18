package Data_Layer.DAOs;

import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Truck;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class truck_DAO {
    public void insert(dummy_Truck truck){
        String query="INSERT INTO \"main\".\"Trucks\"\n" +
                "(\"SN\", \"LicenseNumber\", \"Model\", \"Weight\", \"MaxWeight\")\n" +
                String.format("VALUES ('%s', '%s', '%s', '%.2f', '%.2f');", truck.getId(), truck.getLicense_number(), truck.getModel(), truck.getWeight(), truck.getMax_weight());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query_type="INSERT INTO \"main\".\"Truck_License\"\n" +
                "(\"TruckSN\", \"LicenseSN\")\n" +
                String.format("VALUES ('%d', '%d');",truck.getId(),getLicense_SN(truck.getLicense_type()));

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getLicense_SN(String type){
        String selectQuery = String.format("select * from License where LicenseType = '%s'",type);
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            int SN= rs2.getInt("SN");
            return SN;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public List<String> upload_license(){
        String selectQuery = "select * from License";
        List<String> list=new LinkedList<>();
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            while (rs2.next()){
                String add=rs2.getString("LicenseType");
                if(!list.contains(add))
                    list.add(add);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void upload_license_types(){
        String query_type="INSERT INTO \"main\".\"License\"\n" +
                "(\"SN\", \"LicenseType\")\n" +
                String.format("VALUES ('%d', '%s');", 1, "C");

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query_type);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query_1="INSERT INTO \"main\".\"License\"\n" +
                "(\"SN\", \"LicenseType\")\n" +
                String.format("VALUES ('%d', '%s');", 2, "C1");

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query_1);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM \"main\".\"Trucks\"\n WHERE SN = " + id;
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<dummy_Truck> select(){
        String query="SELECT * FROM Trucks";
        List<dummy_Truck> list=new LinkedList<>();
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            while (rs2.next()) {
                list.add(new dummy_Truck(rs2.getInt("SN"),rs2.getInt("LicenseNumber"),rs2.getString("Model"),rs2.getInt("Weight"),rs2.getInt("MaxWeight"),get_type(rs2.getInt("SN"))));
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }


    public dummy_Truck select_by_id(int id){

        String selectQuery = String.format("select * from Trucks where Trucks.SN = '%d'",id);
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
             dummy_Truck result = new dummy_Truck(rs2.getInt("SN"),rs2.getInt("LicenseNumber"),rs2.getString("Model"),rs2.getInt("Weight"),rs2.getInt("MaxWeight"),get_type(rs2.getInt("SN")));
             return result;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    private String get_type(int id){
        String selectQuery = String.format("select * from Truck_License where TruckSN = '%d'",id);

        int sn;
        String result;
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            sn=rs2.getInt("LicenseSN");
            String query1=String.format("SELECT LicenseType FROM License WHERE SN = '%d' ",sn);
            Statement stmt = Connection.getInstance().getConn().createStatement();
            ResultSet rs  = stmt.executeQuery(query1);
            result=rs.getString("License_Type");
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }


}
