package Data_Layer.DAOs;

import Business_Layer.Workers.Modules.Shift;
import Business_Layer.Workers.Modules.Worker.Worker;
import Business_Layer.Workers.Utils.enums;
import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Address;
import Data_Layer.Dummy_objects.dummy_Worker;
import Data_Layer.Dummy_objects.dummy_supplier;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class worker_DAO {
    /*public void insert(Worker workerToInsert){
        String workerInsertQuery = "INSERT INTO \"main\".\"Workers\"\n" +
                "(\"SN\", \"ID\", \"Name\", \"PhoneNumber\", \"BankAccount\", \"Salary\", \"Date\", \"Worker_Type\", \"StoreSN\")\n" +
                String.format("VALUES ('%d', '%d', '%s', '%s', '%d', '%d', '?', '%s', '%d');",
                        workerToInsert.getWorkerSn(),workerToInsert.getWorkerId(),workerToInsert.getWorkerName(),
                        workerToInsert.getWorkerPhone(),workerToInsert.getWorkerBankAccount(),workerToInsert.getWorkerSalary(),
                        workerToInsert.getWorkerJobTitle(),workerToInsert.getStoreSN());

        java.sql.Date sqlDate = new java.sql.Date(workerToInsert.getWorkerStartingDate().getTime());

        try {
            PreparedStatement statement= Connection.getInstance().getConn().prepareStatement(workerInsertQuery);
            statement.setDate(7,sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Map.Entry<Pair<enums,enums>,Boolean> constrains : workerToInsert.getWorkerConstrains().entrySet()){
            Pair<enums,enums> cons = constrains.getKey();

            String workersConstrainsQuery = "INSERT INTO \"Constrains\"" +
                    "(\"WorkerSN\", \"Shift_typeSN\", \"DayOfWeek\", \"CanWork\") " +
                    String.format("VALUES ('%d', '%d', '%d', '%s')", workerToInsert.getWorkerSn(),cons.getValue(),cons.getKey(),constrains.getValue().toString());
        }
    }*/

    public void insert(dummy_Worker worker) {
        String query = "INSERT INTO \"main\".\"Workers\"\n" +
                "(\"SN\",\"ID\",\"Name\",\"PhoneNumber\",\"BankAccount\", \"Salary\", \"StoreSN\", \"date\", \"jobTitle\")\n" +
                String.format("VALUES ('%d','%d','%s','%d','%d','%d','%d', '%date', '%s');", worker.getId(), worker.getName(), worker.getPhone(), worker.getBankAccount(), worker.getSalary(), worker.getStoreSN(), worker.getStart_Date(), worker.getJob_title());

        for(Pair x : worker.getConstrains().keySet()){
            boolean canWork =  worker.getConstrains().get(x);
            String query_constraints = "INSERT INTO \"main\".\"Workers_Constraints\"\n" +
                    "(\"WorkerSN\",\"Shift_type\",\"DayOfWeek\",\"CanWork\")\n" +
                    String.format("VALUES ('%d','%d','%d','%b');",worker.getSN(), x.getValue(), x.getKey(),canWork);

        }

    }

    public void delete(int workerSN){
        String sql = "DELETE FROM Workers WHERE SN = " + workerSN;
    }

    public void update(Shift shiftToInsert,Worker worker){
        String shiftWorkersQuery = "INSERT INTO \"main\".\"Shift_Worker\"\n" +
                "(\"SNShift\", \"SNWorker\")\n" +
                String.format("VALUES ('%d', '%d');", shiftToInsert.getShiftSn(),worker.getWorkerSn());
    }

    public Worker selectWorkerBySN(int workerSN){
        Worker workerToReturn = null;
        String selectQuery = String.format("select * from Workers where Workers.SN = '%d'",workerSN);


        // if driver ??
        return workerToReturn;
    }

    public String selectConstrainsByWorkerSN(int workerSN){
        String constrainsQuery = String.format("select * from Workers_Constrains where WorkerSN = '%d", workerSN);
        return String.format("select * from Workers_Constrains where WorkerSN = '%d", workerSN);
    }
/*
    public dummy_Worker selectWorkersByStoreSN(int storeSN){
        dummy_Worker workerToReturn = null;

        String selectQuery = String.format("select * from Workers where StoreSN = '%d", storeSN);

<<<<<<< HEAD
        return workerToReturn;
    }
    public void insertLicense(int sn, int license){
        String query = "INSERT INTO \"main\".\"Driver_License\"\n" +
                "(\"DriverSN\",\"LicenseSN\")\n" +
                String.format("VALUES ('%d','%d');",sn, license);

    }
=======
        try {

            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(selectQuery);
            return new dummy_Worker(rs2.getString("SN"),rs2.getString("ID"),rs2.getString("Name"),rs2.getInt("PhoneNumber"),rs2.getInt("BankAccount"),rs2.getInt("Salary"),rs2.getInt("StoreSN"),AddressSn,rs2.getInt("AreaSN"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    } */
>>>>>>> worker_transportation

    public void updateSalary(int sn,int salary){
        String query_Salary = "UPDATE \"main\".\"Workers\"\n" + "SET Salary = " + salary +" WHERE SN = "+sn;
    }

    public void deleteConstraints(int workerSN){
        String sql = "DELETE FROM \"main\".\"Workers_Constraints\"\n WHERE SN = " + workerSN;
    }
    public void addConstraints(int workerSn,int selectedDay,int sType){
        String query_constraints = "INSERT INTO \"main\".\"Workers_Constraints\"\n" +
                "(\"WorkerSN\",\"Shift_type\",\"DayOfWeek\",\"CanWork\")\n" +
                String.format("VALUES ('%d','%d','%d','%b');",workerSn,selectedDay, sType,false);
    }
/*
    public void insert(dummy_Worker worker) {
        String query = "INSERT INTO \"main\".\"Workers\"\n" +
                "(\"SN\",\"ID\",\"Name\",\"PhoneNumber\",\"BankAccount\", \"Salary\", \"StoreSN\", \"date\", \"jobTitle\")\n" +
                String.format("VALUES ('%d','%d','%s','%d','%d','%d','%d', '%date', '%s');", worker.getId(), worker.getName(), worker.getPhone(), worker.getBankAccount(), worker.getSalary(), worker.getStoreSN(), worker.getStart_Date(), worker.getJob_title());

        for(Pair x : worker.getConstrains().keySet()){
            boolean canWork =  worker.getConstrains().get(x);
            String query_constraints = "INSERT INTO \"main\".\"Workers_Constraints\"\n" +
                    "(\"WorkerSN\",\"Shift_type\",\"DayOfWeek\",\"CanWork\")\n" +
                    String.format("VALUES ('%d','%d','%d','%b');",worker.getSN(), x.getValue(), x.getKey(),canWork);

        }

    }





    public void delete(int sn) {
        String sql = "DELETE FROM \"main\".\"Workers\"\n WHERE SN = " + sn;
    } */

}
