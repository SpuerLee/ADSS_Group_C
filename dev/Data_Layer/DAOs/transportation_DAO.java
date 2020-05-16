package Data_Layer.DAOs;

import Data_Layer.Dummy_objects.dummy_Transportation;

import java.sql.Date;


public class transportation_DAO {

    public  java.sql.Date  convert(Date date){
        java.sql.Date date_inset = new java.sql.Date(date.getTime());
        return date;
    }

    public void insert(dummy_Transportation transportation){
        String query="INSERT INTO \"main\".\"Transportations\"\n" +
                "(\"SN\", \"Date\", \"DepartureTime\", \"TruckSN\", \"TruckWeight\")\n" +
                String.format("VALUES ('%d', '%date', '%d', '%d', '%.2f');", transportation.getId(), transportation.getDate(),  transportation.getLeaving_time(), transportation.getTrucksn(), transportation.getTruck_weight());

        for(int i=0;i<transportation.getItemsFile().size(); i++){
            String query_items="INSERT INTO \"main\".\"Transportation_ItemFile\"\n" +
                    "(\"ItemFileSN\", \"TransportationSN\")\n" +
                    String.format("VALUES ('%d', '%d');", transportation.getItemsFile().get(i), transportation.getId());
        }
        String truck_query="INSERT INTO \"main\".\"Transportation_Truck\"\n" +
                "(\"TruckSN\", \"TransportationSN\")\n" +
                String.format("VALUES ('%d', '%d');", transportation.getTrucksn(), transportation.getId());

        for(int i=0;i<transportation.getSuppliers().size(); i++) {
            String query_supplier = "INSERT INTO \"main\".\"Transportation_Supplier\"\n" +
                    "(\"SupplierSN\", \"TransportationSN\")\n" +
                    String.format("VALUES ('%d', '%d');", transportation.getStores().get(i), transportation.getId());
        }

        for(int i=0;i<transportation.getStores().size(); i++){
            String query_store = "INSERT INTO \"main\".\"Transportation_Store\"\n" +
                    "(\"StoreSN\", \"TransportationSN\")\n" +
                    String.format("VALUES ('%d', '%d');", transportation.getSuppliers().get(i), transportation.getId());
        }
        String insert_driver="INSERT INTO \"main\".\"Transportation_Driver\"\n" +
                "(\"DriverSN\", \"TransportationSN\")\n" +
                String.format("VALUES ('%d', '%d');", transportation.getDriverSn(), transportation.getId());
    }

    public void select(){
        String query="SELECT * FROM Transportations";
    }

    public void select_by_TransportationsId(int id){
        String selectQuery = String.format("SELECT * from Stores where Transportations.SN = '%d'",id);
    }
    public void delete(int sn) {
        String selectQuery = String.format("DELETE from Transportations where Transportations.SN = '%d'",sn);
        String delete_driver=String.format("DELETE from Transportation_Driver where Transportation_Driver.TransportationSN = '%d'",sn);
        String delete_stores=String.format("DELETE from Transportation_Store where Transportation_Store.TransportationSN = '%d'",sn);
        String delete_suppliers=String.format("DELETE from Transportation_Supplier where Transportation_Supplier.TransportationSN = '%d'",sn);
        String delete_trucks=String.format("DELETE from Transportation_Truck where Transportation_Truck.TransportationSN = '%d'",sn);
        String delete_items=String.format("DELETE from Transportation_ItemFile where Transportation_ItemFile.TransportationSN = '%d'",sn);
    }

}
