package Data_Layer.DAOs;

import Data_Layer.Dummy_objects.dummy_store;

public class store_DAO {

    public void insert(dummy_store store){
        String query="INSERT INTO \"main\".\"Stores\"\n" +
                "(\"SN\", \"Name\", \"Phone\", \"ContactName\", \"AddressSN\", \"AreaSN\")\n" +
                String.format("VALUES ('%d', '%s', '%s', '%s', '%d', '%d');", store.getId(), store.getName(), store.getPhone(), store.getContact_name(), store.getAddress_Sn() , store.getAreaSn());

        String address_query="INSERT INTO \"main\".\"Address\"\n" +
                "(\"SN\", \"City\", \"Street\", \"Number\")\n" +
                String.format("VALUES ('%d', '%s', '%s', '%d');", store.getAddress_Sn(), store.getCity(), store.getStreet(), store.getNumber());
    }

    public void delete(int sn) {
        String sql = "DELETE FROM \"main\".\"Stores\"\n WHERE SN = " + sn;
    }

    public void select(){
        String query="SELECT * FROM Stores";
    }

    public void select_by_storeId(int id){
        String selectQuery = String.format("select * from Stores where Stores.SN = '%d'",id);
    }

    public void select_by_area(int areaSn){
        String selectQuery = String.format("select * from Stores where Stores.AreaSN = '%d'",areaSn);
    }


    public void delete(){


    }

    public void update(){


    }
}
