package Data_Layer.DAOs;

import Data_Layer.Dummy_objects.dummy_Missing_items;

public class missing_items_DAO {

    public void insert(dummy_Missing_items missing_items){

        String sql="INSERT INTO \"main\".\"MIssing_Items\"\n" +
                "(\"SN\", \"StoreSN\", \"SupplierSN\")\n" +
                String.format("VALUES ('%d', '%d', '%d');", missing_items.getId(), missing_items.getStore_id(),missing_items.getSupplier_id());

        for(int i=0;i<missing_items.getMissing().size();i++) {
            String query_items = "INSERT INTO \"main\".\"Missing_Items_list\"\n" +
                    "(\"SN\", \"MIssing_ItemSN\", \"Amount\", \"ItemName\")\n" +
                    String.format("VALUES ('%d', '%d', '%d', '%s');", i, missing_items.getId(), missing_items.getMissing().get(i).getValue(),missing_items.getMissing().get(i).getKey());
        }
    }

    public void delete(){

    }

    public void update(){

    }

}
