import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ShiftController{

    private HashMap<Integer,Shift> shiftHistory;
    private static ShiftController shiftController = null;
    private int snFactory;

    private ShiftController() {
        this.shiftHistory = new HashMap<>();
        this.snFactory = 0;
    }

    public int getSnFactory(){
        return this.snFactory++;
    }

    public static ShiftController getInstance(){
        if(shiftController == null){
            shiftController = new ShiftController();
        }
        return shiftController;
    }

    public boolean validateNewShift(Date date, HR.ShiftType shiftType){
        return false;
    }

    public void createShift(int _shiftType,int managerSn, String listOfWorkersSn,String _date) throws ParseException {
        HR.ShiftType sType = WorkerController.selectShiftType(_shiftType);
        Worker manager = WorkerController.getInstance().getWorkerBySn(managerSn);
        String[] workersSn = listOfWorkersSn.split(",");
        List<Worker> workersListOfCurrentShift = new LinkedList<>();
        for (String workerSn : workersSn) {
            Worker workerToAdd = WorkerController.getInstance().getWorkerBySn(Integer.parseInt(workerSn));
            workersListOfCurrentShift.add(workerToAdd);
        }
        Date date = new SimpleDateFormat("yyyy-M-d").parse(_date);
        Shift shiftToAdd = new Shift(date,sType,manager,workersListOfCurrentShift,getSnFactory());
        this.shiftHistory.put(shiftToAdd.getSn(),shiftToAdd);
    }

    public void printShift(int shiftIndex){
        Shift shift = this.shiftHistory.get(shiftIndex);
        shift.printShift();
        for(Worker worker : shift.getShiftWorker()){
            if(WorkerController.getInstance().getWorkerList().containsValue(worker)){
                worker.printWorker();
            }
        }
    }

    public void setShiftLists() {
        this.shiftHistory = new HashMap<>();
    }

    public HashMap<Integer,Shift> getShiftHistory() {
        return this.shiftHistory;
    }
}

