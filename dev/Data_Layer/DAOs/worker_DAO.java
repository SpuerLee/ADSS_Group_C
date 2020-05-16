package Data_Layer.DAOs;

import Business_Layer.Workers.Modules.Shift;
import Business_Layer.Workers.Modules.Worker.Worker;
import Business_Layer.Workers.Utils.enums;
import Data_Layer.Dummy_objects.dummy_Worker;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class worker_DAO {
    public void insert(Worker workerToInsert){
        String workerInsertQuery = "INSERT INTO \"main\".\"Workers\"\n" +
                "(\"SN\", \"ID\", \"Name\", \"PhoneNumber\", \"BankAccount\", \"Salary\", \"Date\", \"Worker_Type\", \"StoreSN\")\n" +
                String.format("VALUES ('%d', '%d', '%s', '%s', '%d', '%d', '%date', '%d', '%d');",
                        workerToInsert.getWorkerSn(),workerToInsert.getWorkerId(),workerToInsert.getWorkerName(),
                        workerToInsert.getWorkerPhone(),workerToInsert.getWorkerBankAccount(),workerToInsert.getWorkerSalary(),
                        workerToInsert.getWorkerStartingDate(),workerToInsert.getWorkerJobTitle(),workerToInsert.getStoreSN());

        for(Map.Entry<Pair<enums,enums>,Boolean> constrains : workerToInsert.getWorkerConstrains().entrySet()){
            Pair<enums,enums> cons = constrains.getKey();

            String workersConstrainsQuery = "INSERT INTO \"Constrains\"" +
                    "(\"WorkerSN\", \"Shift_typeSN\", \"DayOfWeek\", \"CanWork\") " +
                    String.format("VALUES ('%d', '%d', '%d', '%s')", workerToInsert.getWorkerSn(),cons.getValue(),cons.getKey(),constrains.getValue().toString());
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

    public Worker selectWorkersByStoreSN(int storeSN){
        Worker workerToReturn = null;

        String selectQuery = String.format("select * from Workers where StoreSN = '%d", storeSN);

        return workerToReturn;
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

    public void insertLicense(int sn, int license){
        String query = "INSERT INTO \"main\".\"Driver_License\"\n" +
                "(\"DriverSN\",\"LicenseSN\")\n" +
                String.format("VALUES ('%d','%d');",sn, license);

    }

    public void updateSalary(int sn,int salary){
        String query_Salary = "UPDATE \"main\".\"Workers\"\n" + "SET Salary = " + salary +" WHERE SN = "+sn;
    }

    public void updateConstraints(int sn,HashMap <Pair< Integer ,Integer>, Boolean> NewConst){
        String sql = "DELETE FROM \"main\".\"Workers_Constraints\"\n WHERE SN = " + sn;
        for(Pair x : NewConst.keySet()){
            boolean canWork =  NewConst.get(x);
            String query_constraints = "INSERT INTO \"main\".\"Workers_Constraints\"\n" +
                    "(\"WorkerSN\",\"Shift_type\",\"DayOfWeek\",\"CanWork\")\n" +
                    String.format("VALUES ('%d','%d','%d','%b');",sn, x.getValue(), x.getKey(),canWork);

        }
    }

    public void delete(int sn) {
        String sql = "DELETE FROM \"main\".\"Workers\"\n WHERE SN = " + sn;
    } */

}
