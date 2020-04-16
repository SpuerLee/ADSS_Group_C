package Workers.BusinessLayer;

import Workers.BusinessLayer.Utils.Worker;
import Workers.BusinessLayer.Utils.enums;
import javafx.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WorkerController {

    private HashMap<Integer, Worker> workerList;
    private static WorkerController workerController = null;
    private int snFactory;

    private WorkerController() {
        this.workerList = new HashMap<>();
        this.snFactory = 0;
    }

    public static WorkerController getInstance(){
        if(workerController == null){
            workerController = new WorkerController();
        }
        return  workerController;
    }

    public HashMap<Integer, Worker> getWorkerList() {
        return workerList;
    }

    public List<Worker> getAllAvailableWorkers(String _date, String shiftType) throws ParseException {


        // convert date to day
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(_date);
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        enums selectedDay= enums.valueOf(dayOfWeek);
        enums sType =enums.valueOf(shiftType);

        List<Worker> workersToPrint = new LinkedList<Worker>();

        for (Worker worker : workerList.values()) {
            if(!worker.getWorkerConstrains().containsKey(new Pair<>(selectedDay,sType))){
                workersToPrint.add(worker);
            }
        }

        return workersToPrint;
    }

    public void printAllManagers(String date, String shiftType) throws ParseException {
        List<Worker> listOfAvailableWorkers = WorkerController.getInstance().getAllAvailableWorkers(date,shiftType);
        for (Worker manager : listOfAvailableWorkers) {
            if(manager.getWorkerJobTitle().equals("Manager")) {
                System.out.println(manager.getWorkerSn() + ". ID: " + manager.getWorkerId() + " Name: " + manager.getWorkerName());
            }
        }
    }

    public void printAllWorkers(String date, String shiftType) throws ParseException {
        List<Worker> listOfAvailableWorkers = WorkerController.getInstance().getAllAvailableWorkers(date,shiftType);
        for (Worker listOfAvailableWorker : listOfAvailableWorkers) {
            if(!listOfAvailableWorker.getWorkerJobTitle().equals("Manager")) {
                System.out.println(listOfAvailableWorker.getWorkerSn() + ". ID: " + listOfAvailableWorker.getWorkerId() + " Name: " + listOfAvailableWorker.getWorkerName() + " Job title: " + listOfAvailableWorker.getWorkerJobTitle());
            }
        }
    }

    public void printWorkerBySn(int workerSn){
        System.out.println(workerList.get(workerSn));
    }

    public void removeWorker(int workerSn){
        this.workerList.remove(workerSn);

    }

    public void setNewSalaryBySn(int workerSn,int newSalaty){
        workerList.get(workerSn).getWorkerSalary(newSalaty);
    }

    public boolean printAllWorker(){
        if(workerList.isEmpty()){
            return false;
        }
        System.out.println("Choose worker by SN");
        for(Worker worker : workerList.values()){
            System.out.println(worker.getWorkerSn() + ". ID: " + worker.getWorkerId() + " Name: " + worker.getWorkerName());
        }
        return true;
    }

    public int getSnFactory(){
        return ++this.snFactory;
    }

    public Worker getWorkerBySn(int snOfWorker){
        return this.workerList.get(snOfWorker);
    }

    public void addConstrainsToWorkerByWorkerSn(int workerSn, String day, String shiftType){
        enums selectedDay= enums.valueOf(day);
        enums sType = enums.valueOf(shiftType);
        WorkerController.getInstance().getWorkerBySn(workerSn).addConstrainsToWorker(selectedDay,sType);

    }

    public int addWorker(int id, String name, int phoneNumber, int bankAccount, int salary, String _date, String jobTitle) throws ParseException {

        Date date = new SimpleDateFormat("yyyy-M-d").parse(_date);
        Worker workerToAdD = new Worker(id,name,phoneNumber,bankAccount,salary,date,jobTitle,getSnFactory());
        workerList.put(workerToAdD.getWorkerSn(),workerToAdD);
        return workerToAdD.getWorkerSn();

    }





}
