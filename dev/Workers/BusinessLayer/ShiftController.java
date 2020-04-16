package Workers.BusinessLayer;

import Workers.BusinessLayer.Utils.Shift;
import Workers.BusinessLayer.Utils.Worker;
import Workers.BusinessLayer.Utils.enums;
import Workers.InterfaceLayer.HR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ShiftController{

    private HashMap<Integer, Shift> shiftHistory;
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

    public boolean validateNewShift(Date date, enums shiftType){
        for (Shift shifty: this.shiftHistory.values()) {
            if((shifty.getDate().compareTo(date)==0) && shifty.getStype().equals(shiftType)){
                return false;
            }
        }
        return true;
    }

    public void createShift(String shiftType, int managerSn, String listOfWorkersSn, String _date) throws ParseException {
        enums sType = enums.valueOf(shiftType);
        Worker manager = WorkerController.getInstance().getWorkerBySn(managerSn);
        String[] workersSn = listOfWorkersSn.split(",");
        List<Worker> workersListOfCurrentShift = new LinkedList<>();
        for (String workerSn : workersSn) {
            Worker workerToAdd = WorkerController.getInstance().getWorkerBySn(Integer.parseInt(workerSn));
            workersListOfCurrentShift.add(workerToAdd);
        }
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(_date);
        Shift shiftToAdd = new Shift(date,sType,manager,workersListOfCurrentShift,getSnFactory());
        if(this.validateNewShift(date,sType)) {
            this.shiftHistory.put(shiftToAdd.getSn(), shiftToAdd);
            System.out.println("New shift has been added successfully" +"\n");
        }
        else{
            System.out.println("We're sorry, Shift already exists"+"\n");
        }
    }

    public void printShift(int shiftIndex){
        Shift shift = this.shiftHistory.get(shiftIndex);
        shift.printShift();
        System.out.println("\n"+"Workers: ");
        for(Worker worker : shift.getShiftWorker()){
            if((shift.getDate().compareTo(new Date()) < 0) | WorkerController.getInstance().getWorkerList().containsValue(worker)){
                worker.printWorker();
            }
        }
        System.out.println("\n");
    }

    public void setShiftLists() {
        this.shiftHistory = new HashMap<>();
    }

    public HashMap<Integer, Shift> getShiftHistory() {
        return this.shiftHistory;
    }
}

