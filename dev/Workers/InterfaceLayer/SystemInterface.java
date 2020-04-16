package Workers.InterfaceLayer;

import Workers.BusinessLayer.ShiftController;
import Workers.BusinessLayer.WorkerController;
import Workers.BusinessLayer.Utils.Shift;
import Workers.BusinessLayer.Utils.Worker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SystemInterface {

    private static SystemInterface systemInterface = null;
    private WorkerController workerController;
    private ShiftController shiftController;

    private SystemInterface() {
        workerController = new WorkerController();
        shiftController = new ShiftController();
    }

    public static SystemInterface getInstance(){
        if(systemInterface == null){
            systemInterface = new SystemInterface();
        }
        return systemInterface;
    }

    public boolean printAllManagers(String date,String shiftType) throws ParseException {
        return workerController.printAllManagers(date,shiftType);
    }

    public boolean printAllWorkers(){
        return workerController.printAllWorker();
    }

    public void printAllWorkers(String date,String shiftType) throws ParseException {
        workerController.printAllWorkers(date, shiftType);
    }

    public void createShift(String shiftType, int selectedManagerSn,String chosenWorkersSn,String date) throws ParseException {
        shiftController.createShift(shiftType, selectedManagerSn, chosenWorkersSn, date);
    }

    public HashMap<Integer,Shift> getShiftHistory(){
        return shiftController.getShiftHistory();
    }

    public void printShift(int shiftSn){
        shiftController.printShift(shiftSn);
    }

    public int addWorker(int workerId,String workerName,int workerPhoneNumber,int workerBankAccount,int workerSalary,String dateOfStart,String workerJobTitle) throws ParseException {
        return workerController.addWorker(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle);
    }

    public void addConstrainsToWorkerByWorkerSn(int workerToAddSn,String constrainsDay,String _shiftType){
        workerController.addConstrainsToWorkerByWorkerSn(workerToAddSn,constrainsDay,_shiftType);
    }

    public void printWorkerBySn(int workerSn){
        workerController.printWorkerBySn(workerSn);
    }

    public void setWorkerConstrains(int workerSn){
        workerController.getWorkerBySn(workerSn).setWorkerConstrains();
    }

    public void printWorkerConstrains(int workerSn){
        System.out.println(workerController.getWorkerBySn(workerSn).printConstrains());
    }

    public void setNewSalaryBySn(int workerSn,int newSalary){
        workerController.setNewSalaryBySn(workerSn, newSalary);
    }

    public void removeWorker(int workerSn){
        workerController.removeWorker(workerSn);
    }

    public HashMap<Integer,Worker> getWorkerList(){
        return workerController.getWorkerList();
    }



}