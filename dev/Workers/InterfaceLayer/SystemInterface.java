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

    public InfoObject printAllManagers(String date, String shiftType) {
        return workerController.printAllManagersAvailableInThisDate(date,shiftType);
    }

    public InfoObject printAllWorkers(){
        return workerController.printAllWorker();
    }

    public InfoObject printAllWorkers(String date,String shiftType) {
        return workerController.printAllWorkersAvailableInThisDate(date, shiftType);
    }

    public InfoObject createShift(String shiftType, int selectedManagerSn,String chosenWorkersSn,String date) {
        return shiftController.createShift(shiftType, selectedManagerSn, chosenWorkersSn, date);
    }

    public InfoObject printShift(int shiftSn){
        return shiftController.printShift(shiftSn);
    }

    public InfoObject addWorker(int workerId,String workerName,int workerPhoneNumber,int workerBankAccount,int workerSalary,String dateOfStart,String workerJobTitle) {
        return workerController.addWorker(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle);
    }

    public InfoObject addConstrainsToWorkerByWorkerSn(int workerToAddSn,String constrainsDay,String _shiftType){
        return workerController.addConstrainsToWorkerByWorkerSn(workerToAddSn,constrainsDay,_shiftType);
    }

    public InfoObject printWorkerBySn(int workerSn){
        return workerController.printWorkerBySn(workerSn);
    }

    public InfoObject printWorkerConstrains(int workerSn){
        return workerController.printWorkersConstrainsBySn(workerSn);
    }

    public InfoObject setNewSalaryBySn(int workerSn,int newSalary){
        return workerController.setNewSalaryBySn(workerSn, newSalary);
    }

    public InfoObject removeWorker(int workerSn){
        return workerController.removeWorkerBySn(workerSn);
    }

    public InfoObject setWorkerConstrains(int workerSn){
        return workerController.setWorkerConstrainsBySn(workerSn);
    }

    public InfoObject printAllShifts(){
        return shiftController.printAllShits();
    }

    public Worker getWorkerById(int workerId){
        return workerController.getWorkerById(workerId);
    }

}