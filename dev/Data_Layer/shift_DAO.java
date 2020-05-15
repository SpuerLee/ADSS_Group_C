package Data_Layer;

import Business_Layer.Workers.Modules.Shift;
import Business_Layer.Workers.Modules.Worker.Worker;

public class shift_DAO {

    public void insert(Shift shiftToInsert){
        String shiftInsertQuery = "INSERT INTO \"main\".\"Shift\"\n" +
                "(\"SN\", \"StoreSN\", \"ShiftType\", \"ManagerSN\", \"Date\")\n" +
                String.format("VALUES ('%d', '%d', '%d', '%d', '%s');", shiftToInsert.getShiftSn(),shiftToInsert.getStoreSN(),
                        shiftToInsert.getShiftType(),shiftToInsert.getManager().getWorkerSn(),shiftToInsert.getDate().toString());

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
