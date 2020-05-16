package Data_Layer.DAOs;

import Data_Layer.Dummy_objects.dummy_Items_File;

public class item_file_DAO {

    public void insert(){
        String query = "INSERT INTO \"main\".\"ItemFiles\"\n" +
                "(\"SN\", \"StoreSN\", \"SupplierSN\")\n" +
                "VALUES ('', '', '');";

    }
    public void insert(dummy_Items_File items_file){
        String query="INSERT INTO \"main\".\"ItemFiles\"\n" +
                "(\"SN\", \"StoreSN\", \"SupplierSN\")\n" +
                String.format("VALUES ('%d', '%d', '%d');", items_file.getSn(),items_file.getStore_id(), items_file.getSupplier_id());

        for(int i=0;i<items_file.getItems().size();i++) {
            String query_items = "INSERT INTO \"main\".\"Items\"\n" +
                    "(\"SN\", \"ItemFileSN\", \"Amount\", \"ItemName\")\n" +
                    String.format("VALUES ('%d', '%d', '%d', '%s');",i,items_file.getSn(), items_file.getItems().get(i).getValue(), items_file.getItems().get(i).getKey());
        }
    }

    public void delete(){

    }

    public void select(){
        String query="SELECT * FROM ItemFiles";
    }

    public void update(){

    }
}
