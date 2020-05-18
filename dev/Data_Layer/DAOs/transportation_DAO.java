package Data_Layer.DAOs;

import Business_Layer.Workers.Utils.enums;
import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Items_File;
import Data_Layer.Dummy_objects.dummy_Transportation;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


public class transportation_DAO {

    public  java.sql.Date  convert(Date date){
        java.sql.Date date_inset = new java.sql.Date(date.getTime());
        return date;
    }
    public java.util.Date convertToDate(java.sql.Date date){
        java.util.Date utilDate = new java.util.Date(date.getTime());
        return utilDate;
    }

    private String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void insert(dummy_Transportation transportation){

    //    java.sql.Date sqlDate = new java.sql.Date(transportation.getDate().getTime());

        String query_items="INSERT INTO \"main\".\"Transportations\"\n" +
                "(\"SN\", \"Date\", \"DepartureTime\", \"TruckSN\", \"TruckWeight\")\n" +
                String.format("VALUES ('%d', '%s', '%d','%d', '%.2f');", transportation.getId(),simpleDateFormat.format(transportation.getDate()) ,transportation.getLeaving_time(),transportation.getTrucksn(),transportation.getTruck_weight());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query_items);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i=0;i<transportation.getItemsFile().size(); i++){
            String query_items1="INSERT INTO \"main\".\"Transportation_ItemFile\"\n" +
                    "(\"ItemFileSN\", \"TransportationSN\")\n" +
                    String.format("VALUES ('%d', '%d');", transportation.getItemsFile().get(i), transportation.getId());
            try {
                PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query_items1);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String truck_query="INSERT INTO \"main\".\"Transportation_Truck\"\n" +
                "(\"TruckSN\", \"TransportationSN\")\n" +
                String.format("VALUES ('%d', '%d');", transportation.getTrucksn(), transportation.getId());
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(truck_query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i=0;i<transportation.getSuppliers().size(); i++) {
            String query_supplier = "INSERT INTO \"main\".\"Transportation_Supplier\"\n" +
                    "(\"SupplierSN\", \"TransportationSN\")\n" +
                    String.format("VALUES ('%d', '%d');", transportation.getSuppliers().get(i) ,transportation.getId());
            try {
                PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query_supplier);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<transportation.getStores().size(); i++){
            String query_store = "INSERT INTO \"main\".\"Transportation_Store\"\n" +
                    "(\"StoreSN\", \"TransportationSN\")\n" +
                    String.format("VALUES ('%d', '%d');", transportation.getStores().get(i), transportation.getId());
            try {
                PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query_store);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String insert_driver="INSERT INTO \"main\".\"Transportation_Driver\"\n" +
                "(\"DriverSN\", \"TransportationSN\")\n" +
                String.format("VALUES ('%d', '%d');", transportation.getDriverSn(), transportation.getId());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(insert_driver);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<dummy_Transportation> select(){
        String query="SELECT * FROM Transportations";
        List <dummy_Transportation> transportation=new LinkedList<>();
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            while (rs2.next()) {
                int Departure=rs2.getInt("DepartureTime");
                enums type;
                if(Departure==1){
                    type=enums.MORNING;
                }
                else
                    type=enums.NIGHT;

                int SN=rs2.getInt("SN");
                transportation.add(new dummy_Transportation(rs2.getInt("SN"),rs2.getDate("Date"),Departure,rs2.getInt("TruckWeight"), rs2.getInt("TruckSN"),getItems(SN),select_supplier_by_id(SN), select_store_by_id(SN), get_Driver(SN)));
            }
            return transportation;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public List<Integer> select_store_by_id(int id){
        store_DAO store_dao=new store_DAO();
        String selectQuery = String.format("select * from Transportation_Store where Transportation_Store.TransportationSN = '%d'",id);
        List<Integer> list=new LinkedList<>();
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            while (rs2.next()) {

                list.add(rs2.getInt("TransportationSN"));
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public List<Integer> select_supplier_by_id(int id){
        supplier_DAO supplier_dao=new supplier_DAO();
        String selectQuery = String.format("select * from Transportation_Supplier where Transportation_Supplier.TransportationSN = '%d'",id);
        List<Integer> list=new LinkedList<>();
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            while (rs2.next()) {

                list.add(rs2.getInt("TransportationSN"));
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public dummy_Transportation select_by_TransportationsId(int id){
        String selectQuery = String.format("SELECT * from Transportations where SN = '%d'",id);
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            Date d1 = new Date(rs2.getDate("Date").getTime());
            return new dummy_Transportation(rs2.getInt("SN"),d1,rs2.getInt("DepartureTime"),rs2.getInt("TruckWeight"), rs2.getInt("TruckSN"),getItems(id),select_supplier_by_id(id), select_store_by_id(id), get_Driver(id));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();

    }

    public void delete(int sn) {
        String selectQuery = String.format("DELETE from Transportations where Transportations.SN = '%d'",sn);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(selectQuery);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String delete_driver=String.format("DELETE from Transportation_Driver where Transportation_Driver.TransportationSN = '%d'",sn);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(delete_driver);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String delete_stores=String.format("DELETE from Transportation_Store where Transportation_Store.TransportationSN = '%d'",sn);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(delete_stores);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String delete_suppliers=String.format("DELETE from Transportation_Supplier where Transportation_Supplier.TransportationSN = '%d'",sn);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(delete_suppliers);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String delete_trucks=String.format("DELETE from Transportation_Truck where Transportation_Truck.TransportationSN = '%d'",sn);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(delete_trucks);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String delete_items=String.format("DELETE from Transportation_ItemFile where Transportation_ItemFile.TransportationSN = '%d'",sn);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(delete_items);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getItems(int id){
        item_file_DAO item_file_dao=new item_file_DAO();
        String selectQuery = String.format("select * from Transportation_ItemFile where Transportation_ItemFile.TransportationSN = '%d'",id);
        List<Integer> list=new LinkedList<>();
        List<dummy_Items_File> list1=new LinkedList<>();
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            while (rs2.next()) {
                list.add(rs2.getInt("TransportationSN"));
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public Integer get_Driver(int id){
        String selectQuery = String.format("select * from Transportation_Driver where Transportation_Driver.TransportationSN = '%d'",id);
        Integer id_return=-1;
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            id_return=rs2.getInt("DriverSN");

            return id_return;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

}
