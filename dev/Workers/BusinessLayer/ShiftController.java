package Workers.BusinessLayer;

import Workers.BusinessLayer.Utils.InfoObject;
import Workers.BusinessLayer.Modules.Shift;
import Workers.BusinessLayer.Modules.Worker;
import Workers.BusinessLayer.Utils.enums;

import java.text.SimpleDateFormat;
import java.util.*;


public class ShiftController{

    private HashMap<Integer, Shift> shiftHistory;
    private WorkerController workerController;
    private int snFactory;

    public ShiftController(WorkerController workerController) {
        this.shiftHistory = new HashMap<>();
        this.workerController = workerController;
        this.snFactory = 0;
    }

    public InfoObject printAllShits() {
        InfoObject infoObject = new InfoObject("",true);
        if(shiftHistory.isEmpty()){
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("There are no shifts to display");
            return infoObject;
        }
        System.out.println("Select shift by SN");
        List<Shift> shiftToDisplay = new LinkedList<>();
        shiftToDisplay.addAll(shiftHistory.values());
        Collections.sort(shiftToDisplay);
        for (Shift shift : shiftToDisplay) {
            SimpleDateFormat day = new SimpleDateFormat("dd-MM-yyyy");
            String date = day.format(shift.getDate());
            System.out.println(shift.getShiftSn() + ". Date: " + date + " Type: " + shift.getShiftType());
        }
        return infoObject;
    }

    private int getSnFactory(){
        return ++this.snFactory;
    }

    public InfoObject validateNewShiftDate(Date date, enums shiftType){
        InfoObject infoObject = new InfoObject("",true);
        for (Shift shifty: this.shiftHistory.values()) {
            if((shifty.getDate().compareTo(date)==0) && shifty.getShiftType().equals(shiftType)){
                infoObject.setIsSucceeded(false);
                infoObject.setMessage("There is already a shift on this date");
                return infoObject;
            }
        }
        Date toDay= new Date();
        if(date.compareTo(toDay)<0){
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Date already passed");
            return infoObject;
        }
        return infoObject;
    }

    public InfoObject createShift(String shiftType, int managerSn, String listOfWorkersSn, String _date) {
        InfoObject infoObject = new InfoObject("",true);
        enums sType;
        try {
            sType = enums.valueOf(shiftType);
        }
        catch (Exception e){
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Invalid shift type");
            return infoObject;
        }

        Date date = parseDate(_date);
        if(date==null){
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Invalid date format");
            return infoObject;
        }
        infoObject = validateNewShiftDate(date,sType);
        if(!infoObject.isSucceeded()){
            return infoObject;
        }

        if(!(workerController.getWorkerList().containsKey(managerSn))){
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Invalid manager SN");
            return infoObject;
        }
        if(!(workerController.getWorkerList().get(managerSn).getWorkerJobTitle().toUpperCase().equals("MANAGER"))){
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Invalid manager SN");
            return infoObject;
        }
        Worker manager = workerController.getWorkerList().get(managerSn);
        String[] workersSn;
        List<Worker> workersListOfCurrentShift = new LinkedList<>();
        infoObject = validateManagerConstrains(manager,sType,date);
        if(!infoObject.isSucceeded()){
            return infoObject;
        }
        workersListOfCurrentShift.add(manager);
        if(!listOfWorkersSn.toUpperCase().equals("NONE")) {
            try {
                workersSn = listOfWorkersSn.split(",");
            } catch (Exception e) {
                workersSn = null;
            }
            if (workersSn == null) {
                infoObject.setIsSucceeded(false);
                infoObject.setMessage("Invalid workers SN format input");
                return infoObject;
            }
            if (!workersSn[0].equals("")) {
                // validate workers SN
                for (String workerSn : workersSn) {
                    if (!(workerController.getWorkerList().containsKey(Integer.parseInt(workerSn)))) {
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage("There is no worker with serial number " + Integer.parseInt(workerSn));
                        return infoObject;
                    }
                }

                // validate workers starting date and shift date
                for (String workerSn : workersSn) {
                    Date workersDate = workerController.getWorkerList().get(Integer.parseInt(workerSn)).getWorkerStartingDate();
                    if (date.compareTo(workersDate) < 0) {
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage("This worker : " + workerController.getWorkerList().get(Integer.parseInt(workerSn)).getWorkerName() + " Start working only from " +
                                workerController.getWorkerList().get(Integer.parseInt(workerSn)).getWorkerStartingDate());
                        return infoObject;
                    }
                }

                // validate workers constrains  with shift date
                for (String workerSn : workersSn) {
                    Worker workerToAdd = workerController.getWorkerList().get(Integer.parseInt(workerSn));
                    if (!(workerToAdd.available(date, sType))) {
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage(workerToAdd.getWorkerName() + " Can't work on this shift because of his constrains");
                        return infoObject;
                    }
                    if(workersListOfCurrentShift.contains(workerToAdd)){
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage(workerToAdd.getWorkerName() + " Already in this shift. Cant add the same worker twice");
                        return infoObject;
                    }
                    workersListOfCurrentShift.add(workerToAdd);
                }
            }
        }else{
            workersListOfCurrentShift = new LinkedList<>();
        }
        Shift shiftToAdd = new Shift(date, sType, manager, workersListOfCurrentShift, getSnFactory());
        this.shiftHistory.put(shiftToAdd.getShiftSn(),shiftToAdd);
        infoObject.setMessage("Shift created successfully");
        return infoObject;
    }

    public InfoObject printShift(int shiftIndex) {
        InfoObject infoObject = new InfoObject("", true);
        if (!(shiftHistory.containsKey(shiftIndex))) {
            infoObject.setMessage("There is no shift with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        Shift shift = this.shiftHistory.get(shiftIndex);
        shift.printShift();
        if (shift.getShiftWorkers().isEmpty()) {
            System.out.println("\n" + "Workers: No workers for this shift");
        } else {
            System.out.println("\n" + "Workers: ");
            for (Worker worker : shift.getShiftWorkers()) {
                if ((shift.getDate().compareTo(new Date()) < 0) | this.workerController.getWorkerList().containsValue(worker)) {
                    if (!worker.getWorkerJobTitle().toUpperCase().equals("MANAGER")) {
                        worker.printWorker();
                    }
                }
            }
        }
        System.out.println("\n");
        return infoObject;
    }

    public HashMap<Integer, Shift> getShiftHistory() {
        return this.shiftHistory;
    }

    public WorkerController getWorkerController() {
        return this.workerController;
    }

    private Date parseDate(String _date){
        Date date;
        try {
            date = new SimpleDateFormat("d-MM-yyyy").parse(_date);
        }
        catch (Exception e){
            return null;
        }
        return date;
    }

    private InfoObject validateManagerConstrains(Worker manager,enums shiftType,Date shiftDate){
        InfoObject infoObject = new InfoObject("",true);
        Date date = new Date();
        Date workersDate = manager.getWorkerStartingDate();
        if (date.compareTo(workersDate) < 0) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("This worker : " + manager.getWorkerName() + " Start working only from " +
                    manager.getWorkerStartingDate());
            return infoObject;
        }

        if (!(manager.available(shiftDate, shiftType))) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage(manager.getWorkerName() + " Can't work on this shift because of his constrains");
            return infoObject;
        }

        return infoObject;
    }

    public InfoObject removeLaterShiftForFiredManagerByManagerSn(int workerSn) {
        InfoObject infoObject = new InfoObject("",true);

        Date today = new Date();
        List<Shift> listOfShiftsRemoved = new LinkedList<>();
        for(Shift shift : shiftHistory.values()){
            Date shiftDate = shift.getDate();
            // shift date occurs after today
            if(shiftDate.compareTo(today)>0){
                if(shift.getManager().getWorkerSn() == workerSn) {
                    listOfShiftsRemoved.add(shift);
                }
            }
        }
        for(Shift shiftToRemove: listOfShiftsRemoved){
            this.shiftHistory.remove(shiftToRemove.getShiftSn());
        }
        System.out.println("This shifts has been removed : \n");
        for(Shift deletedShift : listOfShiftsRemoved){
            deletedShift.printShift();
            System.out.println("");
        }

        return infoObject;
    }
}

