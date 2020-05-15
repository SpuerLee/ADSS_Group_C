package Data_Layer;

import Business_Layer.Workers.Modules.Shift;
import Business_Layer.Workers.Modules.Worker.Worker;
import Business_Layer.Workers.Utils.enums;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class worker_DAO {
    public void insert(Worker workerToInsert){
        String workerInsertQuery = "INSERT INTO \"main\".\"Workers\"\n" +
                "(\"SN\", \"ID\", \"Name\", \"PhoneNumber\", \"BankAccount\", \"Salary\", \"Date\", \"Worker_Type\", \"StoreSN\")\n" +
                String.format("VALUES ('%d', '%d', '%s', '%s', '%d', '%d', '%date', '%d', '%d');",
                        workerToInsert.getWorkerSn(),workerToInsert.getWorkerId(),workerToInsert.getWorkerName(),
                        workerToInsert.getWorkerPhone(),workerToInsert.getWorkerBankAccount,workerToInsert.getWorkerSalary(),
                        workerToInsert.getWorkerStartingDate(),workerToInsert.getWorkerJobTitle(),workerToInsert.getStoreSN());

        for(Map.Entry<Pair<enums,enums>,Boolean> constrains : workerToInsert.getWorkerConstrains().entrySet()){
            Pair<enums,enums> cons = constrains.getKey();

            String workersConstrainsQuery = "INSERT INTO \"Workers_Constrains\"" +
                    "(\"WorkerSN\", \"Shift_typeSN\", \"DayOfWeek\", \"CanWork\") " +
                    String.format("VALUES ('%d', '%d', '%d', '%s')", workerToInsert.getWorkerSn(),cons.getValue(),cons.getKey(),constrains.getValue().toString());
        }
    }

    public void delete(int workerSN){
        String sql = "DELETE FROM Workers WHERE SN = " + workerSN;
    }

    public void update(){
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

}
