package Data_Layer;

import Business_Layer.Modules.Store;

public class store_DAO {

    public void insert(Store storeToInsert){

        String query = "INSERT INTO \"main\".\"Stores\"" +
                "(\"SN\", \"Name\", \"Phone\", \"ContactName\", \"AddressSN\", \"AreaSN\")" +
                String.format("VALUES ('%d', '%s', '%s', '%s', '%d', '%d')",storeToInsert.getId(),storeToInsert.getName(),
                        storeToInsert.getPhone(),storeToInsert.getContact_name(),storeToInsert.getAddress(),storeToInsert.getArea());
    }

    public void delete(){


    }

    public void update(){


    }
}
