package Data_Layer.DAOs;

import Business_Layer.Transportations.Utils.Buisness_Exception;
import Business_Layer.Workers.Utils.enums;
import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Address;
import Data_Layer.Dummy_objects.dummy_Items_File;
import Data_Layer.Dummy_objects.dummy_Transportation;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class transportation_DAO {

    private String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Date parseToDate(String str) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try{
            return simpleDateFormat.parse(str);
        }catch (Exception e){
            return new Date();
        }
    }

    public void insert(dummy_Transportation transportation){
        String query_items="INSERT INTO \"main\".\"Transportations\"\n" +
                "(\"SN\", \"Date\", \"DepartureTime\",\"TruckWeight\")\n" +
                String.format("VALUES ('%d', '%s','%d', '%.2f');", transportation.getId(),simpleDateFormat.format(transportation.getDate()),
                        transportation.getLeaving_time(), transportation.getTruck_weight());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query_items);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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

        String insert_driver="INSERT INTO \"main\".\"Transportation_Driver\"\n" +
                "(\"DriverSN\", \"TransportationSN\")\n" +
                String.format("VALUES ('%d', '%d');", transportation.getDriverSn(), transportation.getId());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(insert_driver);
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

    }

    public dummy_Transportation select(int SN){
        String query="SELECT Transportations.*, Transportation_Driver.DriverSN ,Transportation_Truck.TruckSN\n"+
                "FROM Transportations INNER JOIN Transportation_Driver\n" +
                "On Transportations.SN = Transportation_Driver.TransportationSN\n" +
                "INNER JOIN Transportation_Truck\n" +
                "On Transportations.SN = Transportation_Truck.TransportationSN\n" +
                String.format("WHERE SN = '%d';",SN);;
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            if(rs2.next()==false)
                return null;
            else
            {
                dummy_Transportation dummy_transportation = new dummy_Transportation(rs2.getInt("SN"),
                        parseToDate(rs2.getString("Date")),rs2.getInt("DepartureTime"),rs2.getDouble("TruckWeight"),
                        rs2.getInt("TruckSN"),rs2.getInt("DriverSN"));


                dummy_transportation.setStores(select_stores(SN));
                dummy_transportation.setSuppliers(select_suppliers(SN));
                dummy_transportation.setItemsFile(select_items_files(SN));

                return dummy_transportation;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public List<dummy_Transportation> selectAll(){
        String query="SELECT Transportations.*, Transportation_Driver.DriverSN ,Transportation_Truck.TruckSN\n"+
                "FROM Transportations INNER JOIN Transportation_Driver\n" +
                "On Transportations.SN = Transportation_Driver.TransportationSN\n" +
                "INNER JOIN Transportation_Truck\n" +
                "On Transportations.SN = Transportation_Truck.TransportationSN\n";
        List<dummy_Transportation> dummy_transportations=new LinkedList<>();
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            while (rs2.next()){
                String d = rs2.getString("Date");
                dummy_Transportation dummy_transportation = new dummy_Transportation(rs2.getInt("SN"),
                        parseToDate(rs2.getString("Date")),rs2.getInt("DepartureTime"),rs2.getDouble("TruckWeight"),
                        rs2.getInt("TruckSN"),rs2.getInt("DriverSN"));


                dummy_transportation.setStores(select_stores(dummy_transportation.getId()));
                dummy_transportation.setSuppliers(select_suppliers(dummy_transportation.getId()));
                dummy_transportation.setItemsFile(select_items_files(dummy_transportation.getId()));
                dummy_transportations.add(dummy_transportation);
            }
            return dummy_transportations;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public Integer getNextSN(){
        String query="SELECT MAX(SN) FROM Transportations";
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            if(rs2.next()==false)
                return 1;
            else
                return rs2.getInt("MAX(SN)") + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public List<Integer> select_items_files(int SN) {
        List<Integer> items=new LinkedList<>();
        String query = "SELECT ItemFileSN FROM Transportation_ItemFile\n" +
                String.format("WHERE TransportationSN = '%d';", SN);
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);
            while (rs2.next()){
                items.add(rs2.getInt("ItemFileSN"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
    public List<Integer> select_suppliers(int SN) {
        List<Integer> suppliers1=new LinkedList<>();
        String query = "SELECT SupplierSN FROM Transportation_Supplier\n" +
                String.format("WHERE TransportationSN = '%d';", SN);
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);
            while (rs2.next()){
                suppliers1.add(rs2.getInt("SupplierSN"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return suppliers1;
    }
    public List<Integer> select_stores(int SN) {
        List<Integer> suppliers1=new LinkedList<>();
        String query = "SELECT StoreSN FROM Transportation_Store\n" +
                String.format("WHERE TransportationSN = '%d';", SN);
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);
            while (rs2.next()){
                suppliers1.add(rs2.getInt("StoreSN"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return suppliers1;
    }

//    public List<dummy_Transportation> select(){
//        String query="SELECT * FROM Transportations";
//        List <dummy_Transportation> transportation=new LinkedList<>();
//        try {
//            Statement stmt2 = Connection.getInstance().getConn().createStatement();
//            ResultSet rs2  = stmt2.executeQuery(query);
//            while (rs2.next()) {
//                int Departure=rs2.getInt("DepartureTime");
//                enums type;
//                if(Departure==1){
//                    type=enums.MORNING;
//                }
//                else
//                    type=enums.NIGHT;
//
//                int SN=rs2.getInt("SN");
//                transportation.add(new dummy_Transportation(rs2.getInt("SN"),rs2.getDate("Date"),Departure,rs2.getInt("TruckWeight"), rs2.getInt("TruckSN"),getItems(SN),select_supplier_by_id(SN), select_store_by_id(SN), get_Driver(SN)));
//            }
//            return transportation;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        throw new NullPointerException();
//    }

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

//    public dummy_Transportation select_by_TransportationsId(int id){
//        String selectQuery = String.format("SELECT * from Transportations where SN = '%d'",id);
//        try {
//
//            Statement stmt2 = Connection.getInstance().getConn().createStatement();
//            ResultSet rs2  = stmt2.executeQuery(selectQuery);
//            Date d1 = new Date(rs2.getDate("Date").getTime());
//            return new dummy_Transportation(rs2.getInt("SN"),d1,rs2.getInt("DepartureTime"),rs2.getInt("TruckWeight"), rs2.getInt("TruckSN"),getItems(id),select_supplier_by_id(id), select_store_by_id(id), get_Driver(id));
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        throw new NullPointerException();
//
//    }

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

    public void add_driver(int transport, int driver){
        String insert_driver="INSERT INTO \"main\".\"Transportation_Driver\"\n" +
                "(\"DriverSN\", \"TransportationSN\")\n" +
                String.format("VALUES ('%d', '%d');", driver, transport);

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(insert_driver);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add_Truck(int transport, int truck){
        String truck_query="INSERT INTO \"main\".\"Transportation_Truck\"\n" +
                "(\"TruckSN\", \"TransportationSN\")\n" +
                String.format("VALUES ('%d', '%d');", truck, transport);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(truck_query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove_Driver(int transport,int driver){
        String delete_driver=String.format("DELETE from Transportation_Driver where Transportation_Driver.TransportationSN = '%d'",transport);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(delete_driver);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove_Truck(int transport,int truck){
        String delete_trucks=String.format("DELETE from Transportation_Truck where Transportation_Truck.TransportationSN = '%d'",transport);
        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(delete_trucks);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
