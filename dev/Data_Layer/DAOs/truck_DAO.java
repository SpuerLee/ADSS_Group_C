package Data_Layer.DAOs;

import Data_Layer.Dummy_objects.dummy_Truck;

public class truck_DAO {
    public void insert(dummy_Truck truck){
        String query="INSERT INTO \"main\".\"Trucks\"\n" +
                "(\"SN\", \"LicenseNumber\", \"Model\", \"Weight\", \"MaxWeight\")\n" +
                String.format("VALUES ('%d', '%d', '%s', '%.2f', '%.2f');", truck.getId(), truck.getLicense_number(), truck.getModel(), truck.getWeight(), truck.getMax_weight());
        String query_type="INSERT INTO \"main\".\"Truck_License\"\n" +
                "(\"TruckSN\", \"LicenseSN\")\n" +
                String.format("VALUES ('%d', '%d');", truck.getId(), truck.getLicense_type());
    }

    public void delete(int id) {
        String sql = "DELETE FROM \"main\".\"Trucks\"\n WHERE SN = " + id;
    }

    public void select(){
        String query="SELECT * FROM Trucks";
    }
}
