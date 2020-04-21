package Workers.InterfaceLayer;

import Workers.BusinessLayer.ShiftController;
import Workers.BusinessLayer.Utils.InfoObject;
import Workers.BusinessLayer.WorkerController;
import Workers.BusinessLayer.Modules.Worker;

public class SystemInterface {

    private static SystemInterface systemInterface = null;
    private WorkerController workerController;
    private ShiftController shiftController;

    private SystemInterface() {
        workerController = new WorkerController();
        shiftController = new ShiftController(workerController);
    }

    public static SystemInterface getInstance(){
        if(systemInterface == null){
            systemInterface = new SystemInterface();
        }
        return systemInterface;
    }

    public InfoObject printAllManagersAvailableInDates(String date, String shiftType) {
        return workerController.printAllManagersAvailableInThisDate(date,shiftType);
    }

    public InfoObject printAllWorkers(){
        return workerController.printAllWorker();
    }

    public InfoObject printAllWorkersAvailableInDates(String date, String shiftType) {
        return workerController.printAllWorkersAvailableInThisDate(date, shiftType);
    }

    public InfoObject createShift(String shiftType, int selectedManagerSn,String chosenWorkersSn,String date) {
        return shiftController.createShift(shiftType, selectedManagerSn, chosenWorkersSn, date);
    }

    public InfoObject printShift(int shiftSn){
        return shiftController.printShift(shiftSn);
    }

    public InfoObject addWorker(int workerId,String workerName,String workerPhoneNumber,int workerBankAccount,int workerSalary,String dateOfStart,String workerJobTitle,String constrains) {
        return workerController.addWorker(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle,constrains);
    }

    public InfoObject printWorkerBySn(int workerSn){
        return workerController.printWorkerBySn(workerSn);
    }

    public InfoObject printWorkerConstrainsBySn(int workerSn){
        return workerController.printWorkersConstrainsBySn(workerSn);
    }

    public InfoObject setNewSalaryBySn(int workerSn,int newSalary){
        return workerController.setNewSalaryBySn(workerSn, newSalary);
    }

    public InfoObject removeWorkerBySn(int workerSn){
        return workerController.removeWorkerBySn(workerSn);
    }

    public InfoObject resetWorkerConstrainsBySn(int workerSn){
        return workerController.resetWorkerConstrainsBySn(workerSn);
    }

    public InfoObject printAllShifts(){
        return shiftController.printAllShits();
    }

    public InfoObject editWorkerConstrainsBySn(int workerSn, String newConstrains){
        return workerController.editWorkerConstrainsBySn(workerSn,newConstrains);
    }

}