package Data_Layer.DAOs;

import Business_Layer.Workers.Modules.Shift;
import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Shift;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class shift_DAO {


    public void insert(dummy_Shift shift) {
        int sn = shift.getSn();
        String query = "INSERT INTO \"main\".\"Shift\"\n" +
                "(\"SN\", \"StoreSN\", \"ShiftType\", \"ManagerSN\", \"date\")\n" +
                String.format("VALUES ('%d','%d','%d','%d', '%s');",
                        sn , shift.getBranch(),shift.getShift_type(), shift.getManager(),"date");
        System.out.println(query);
     //   java.sql.Date sqlDate = new java.sql.Date(shift.getDate().getTime());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            //statement.setDate(5,sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert_Shift_Workers(int workerSN, int shiftSN){
        String query = "INSERT INTO \"main\".\"Shift_Worker\"\n" +
                "(\"SNShift\",\"SNWorker\")\n" +
                String.format("VALUES ('%d','%d');",shiftSN, workerSN);

        executeQuery(query);
    }

    public void delete(int shiftSN){
        String sql = "DELETE FROM Shift WHERE SN =" + shiftSN;
    }

    public dummy_Shift selectShiftBySN(int shiftSN) throws NullPointerException{
        String sql = "select * from Shift where SN =" + shiftSN;

        try {
            Statement stmt = Connection.getInstance().getConn().createStatement();
            ResultSet rs  = stmt.executeQuery(sql);
            return new dummy_Shift(rs.getDate("Date"),rs.getInt("ShiftType"),rs.getInt("ManagerSN"),rs.getInt("SN"),rs.getInt("StoreSN"));
        }
       catch (SQLException e) {
        e.printStackTrace();
       }
        throw new NullPointerException();
    }

    public List<dummy_Shift> selectShiftByStoreSN(int StoreSN) throws NullPointerException{
        List<dummy_Shift> shiftsToReturn = new LinkedList<>();
        String selectQuery = "select * from Shift where StoreSN =" + StoreSN;
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            while (rs2.next()) {
                dummy_Shift toADD = new dummy_Shift(rs2.getDate("Date"),rs2.getInt
                        ("ShiftType"),rs2.getInt("ManagerSN"),rs2.getInt("SN"),
                       StoreSN);
                shiftsToReturn.add(toADD);
            }
            return shiftsToReturn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public List<Integer> selectShiftWorkersByShiftSN(int ShiftSN){
        String constrainsQuery = String.format("select * from Shift_Worker where SNShift = '%d", ShiftSN);
        List<Integer> workers = new LinkedList<>();
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(constrainsQuery);
            while (rs2.next()) {
                int toADD =rs2.getInt("SNWorker");
                workers.add(toADD);
            }
            return workers;
        } catch (SQLException e) {
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

    private void executeQuery(String query){
        try {
            // java.sql.Date sqlDate = new java.sql.Date(worker.getStart_Date().getTime());
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(query);
            //statement.setDate(7,sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* public Shift selectShiftsByStoreSN(int storeSN){
        Shift shiftToReturn = null;
        String sql = "select * from Shift where StoreSN =" + storeSN;
        return shiftToReturn;
    } */

}
