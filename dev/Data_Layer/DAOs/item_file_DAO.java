package Data_Layer.DAOs;

import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Items_File;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class item_file_DAO {

    public void insert(dummy_Items_File items_file){
        String query="INSERT INTO \"main\".\"ItemFiles\"\n" +
                "(\"SN\", \"StoreSN\", \"SupplierSN\")\n" +
                String.format("VALUES ('%d', '%d', '%d');", items_file.getSn(),items_file.getStore_id(), items_file.getSupplier_id());

        try {
            PreparedStatement statement=Connection.getInstance().getConn().prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i=0;i<items_file.getItems().size();i++) {
            String query_items = "INSERT INTO \"main\".\"Items\"\n" +
                    "(\"SN\", \"ItemFileSN\", \"Amount\", \"ItemName\")\n" +
                    String.format("VALUES ('%d', '%d', '%d', '%s');", i, items_file.getSn(), items_file.getItems().get(i).getValue(), items_file.getItems().get(i).getKey());
            try {
                PreparedStatement statement = Connection.getInstance().getConn().prepareStatement(query_items);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(){

    }

   /* public List<dummy_Items_File> select(){
        String query="SELECT * FROM ItemFiles";
        List<dummy_Items_File> value=new LinkedList<>();
        try {
            PreparedStatement statement=Connection.getInstance().getConn().prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public dummy_Items_File select_by_id(int id){
        String selectQuery = String.format("select * from ItemFiles where ItemFiles.SN = '%d'",id);
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            List<Pair<String,Integer>> pairs=select_items(rs2.getInt("SN"));
            return new dummy_Items_File(rs2.getInt("SN"),rs2.getInt("StoreSN"),rs2.getInt("SupplierSN"),pairs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    private List<Pair<String,Integer>> select_items(int id) {
        String selectQuery = String.format("select * from Items where Items.ItemFilesSN = '%d'", id);
        List<Pair<String, Integer>> list = new LinkedList<>();
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2 = stmt2.executeQuery(selectQuery);
            while (rs2.next()) {
                list.add(new Pair<>(rs2.getString("ItemName"), rs2.getInt("Amount")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new NullPointerException();
    }

    public void update(){

    }
}
