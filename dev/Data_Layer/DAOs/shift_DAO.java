package Data_Layer.DAOs;

import Business_Layer.Workers.Modules.Shift;
import Data_Layer.Dummy_objects.dummy_Shift;

public class shift_DAO {


    public void insert(dummy_Shift shift) {
        int sn = shift.getSn();
        String query = "INSERT INTO \"main\".\"Shifts\"\n" +
                "(\"SN\", \"StoreSN\", \"ShiftType\", \"ManagerSN\", \"date\")\n" +
                String.format("VALUES ('%d','%d','%d','%d','%date');",
                        sn , shift.getBranch(),shift.getShift_type(), shift.getManager(), shift.getDate().toString());

        for(Integer x : shift.getShift_workers()){
            String query_constraints = "INSERT INTO \"main\".\"Shift_Workers\"\n" +
                    "(\"ShiftSN\",\"WorkerSN\")\n" +
                    String.format("VALUES ('%d','%d');",sn, x);
        }

    }

    public void delete(int shiftSN){
        String sql = "DELETE FROM Shift WHERE SN =" + shiftSN;
    }

    public Shift selectShiftBySN(int shiftSN){
        Shift shiftToReturn = null;
        String sql = "select * from Shift where SN =" + shiftSN;
        return shiftToReturn;
    }

    public Shift selectShiftsByStoreSN(int storeSN){
        Shift shiftToReturn = null;
        String sql = "select * from Shift where StoreSN =" + storeSN;
        return shiftToReturn;
    }

}
