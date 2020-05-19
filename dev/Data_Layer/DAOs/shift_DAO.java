package Data_Layer.DAOs;

import Business_Layer.Workers.Modules.Shift;
import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Shift;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class shift_DAO {


    public void insert(dummy_Shift shift) {
        int sn = shift.getSn();
        String query = "INSERT INTO \"main\".\"Shifts\"\n" +
                "(\"SN\", \"StoreSN\", \"ShiftType\", \"ManagerSN\", \"date\")\n" +
                String.format("VALUES ('%d','%d','%d','%d', ?,);",
                        sn , shift.getBranch(),shift.getShift_type(), shift.getManager());
        java.sql.Date sqlDate = new java.sql.Date(shift.getDate().getTime());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            statement.setDate(5,sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        for(Integer x : shift.getShift_workers()){
            String query_constraints = "INSERT INTO \"main\".\"Shift_Workers\"\n" +
                    "(\"ShiftSN\",\"WorkerSN\")\n" +
                    String.format("VALUES ('%d','%d');",sn, x);
            try {
                PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query_constraints);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert_types() {
        String query = "INSERT INTO \"main\".\"Shift_Type\"\n" +
                "(\"SN\", \"DayType\")\n" +
                String.format("VALUES ('%d','%s');",
                        1 , "Morning");

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void delete(int shiftSN){
        String sql = "DELETE FROM Shift WHERE SN =" + shiftSN;
    }

    public dummy_Shift selectShiftBySN(int shiftSN) throws NullPointerException{
        String sql = "select * from Shift where SN =" + shiftSN;

        try {
            Statement stmt = Connection.getInstance().getConn().createStatement();
            ResultSet rs  = stmt.executeQuery(sql);
            return new dummy_Shift(rs.getDate("Date"),rs.getInt("ShiftType"),rs.getInt("ManagerSN"),get_workers(shiftSN),rs.getInt("SN"),rs.getInt("StoreSN"));
        }
       catch (SQLException e) {
        e.printStackTrace();
       }
        throw new NullPointerException();
    }

    private List<Integer> get_workers(int Sn){
        String sql_workers = "select * from Shift_Worker where SN =" + Sn;
        List<Integer> workers=new LinkedList<>();

        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(sql_workers);
            while (rs2.next()) {
                workers.add(rs2.getInt("SNWorker"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }

   /* public Shift selectShiftsByStoreSN(int storeSN){
        Shift shiftToReturn = null;
        String sql = "select * from Shift where StoreSN =" + storeSN;
        return shiftToReturn;
    } */

}
