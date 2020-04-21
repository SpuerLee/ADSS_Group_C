package Workers.BusinessLayer;

import Workers.BusinessLayer.Utils.InfoObject;
import Workers.BusinessLayer.Modules.Worker;
import Workers.BusinessLayer.Utils.enums;

import java.text.SimpleDateFormat;
import java.util.*;

public class WorkerController {

    private HashMap<Integer, Worker> workerList;
    private int snFactory;

    public WorkerController() {
        this.workerList = new HashMap<>();
        this.snFactory = 0;
    }

    public HashMap<Integer, Worker> getWorkerList() {
        return workerList;
    }

    private List<Worker> getAllAvailableWorkers(Date date, enums shiftType) {
        List<Worker> workersToPrint = new LinkedList<>();

        for (Worker worker : workerList.values()) {
            if(worker.available(date,shiftType)){
                workersToPrint.add(worker);
            }
        }
        return workersToPrint;
    }

    public InfoObject printAllManagersAvailableInThisDate(String dateToParse, String shiftType) {
        InfoObject infoObject = new InfoObject("",true);
        Date date = parseDate(dateToParse);
        if(date==null){
            infoObject.setMessage("Invalid date format");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        enums sType;
        try {
            sType = enums.valueOf(shiftType);
        }
        catch (Exception e){
            infoObject.setMessage("Invalid shift type");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        boolean availableManager=false;
        List<Worker> listOfAvailableWorkers = getAllAvailableWorkers(date,sType);
        for (Worker manager : listOfAvailableWorkers) {
            if(manager.getWorkerJobTitle().equals("Manager")) {
                System.out.println(manager.getWorkerSn() + ". ID: " + manager.getWorkerId() + " Name: " + manager.getWorkerName());
                availableManager = true;
            }
        }
        if(!availableManager){
            infoObject.setMessage("There are no available managers");
            infoObject.setIsSucceeded(false);
        }
        return infoObject;
    }

    public InfoObject printAllWorkersAvailableInThisDate(String dateToParse, String shiftType) {
        InfoObject infoObject = new InfoObject("",true);
        Date date = parseDate(dateToParse);
        if(date==null){
            infoObject.setMessage("Invalid date format");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        enums sType;
        try {
            sType = enums.valueOf(shiftType);
        }
        catch (Exception e){
            infoObject.setMessage("Invalid shift type");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        List<Worker> listOfAvailableWorkers = getAllAvailableWorkers(date,sType);
        boolean availableWorker = false;
        for (Worker listOfAvailableWorker : listOfAvailableWorkers) {
            if(!listOfAvailableWorker.getWorkerJobTitle().equals("Manager")) {
                System.out.println(listOfAvailableWorker.getWorkerSn() + ". ID: " + listOfAvailableWorker.getWorkerId() + " Name: " + listOfAvailableWorker.getWorkerName() + " Job title: " + listOfAvailableWorker.getWorkerJobTitle());
                availableWorker = true;
            }
        }
        if(!availableWorker){
            infoObject.setMessage("There are no available workers");
            infoObject.setIsSucceeded(false);
        }
        return infoObject;
    }

    public InfoObject removeWorkerBySn(int workerSn){
        InfoObject infoObject = new InfoObject("Worker removed successfully",true);
        if(this.getWorkerList().containsValue(getWorkerBySn(workerSn))){
            this.workerList.remove(workerSn);
        } else {
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
        }
        return infoObject;
    }

    public InfoObject setNewSalaryBySn(int workerSn, int newSalary){
        InfoObject infoObject = new InfoObject("Workers salary edited successfully",true);
        if(this.getWorkerList().containsValue(getWorkerBySn(workerSn))){
            if(newSalary<0){
                infoObject.setMessage("Invalid new salary");
                infoObject.setIsSucceeded(false);
            } else {
                workerList.get(workerSn).setWorkerSalary(newSalary);
            }
        } else {
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
        }
        return infoObject;
    }

    public InfoObject printAllWorker(){
        InfoObject infoObject = new InfoObject("",true);
        if(workerList.isEmpty()){
            infoObject.setMessage("The company has no workers yet");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        System.out.println("Choose worker by SN");
        for(Worker worker : workerList.values()){
            System.out.println(worker.getWorkerSn() + ". ID: " + worker.getWorkerId() + " Name: " + worker.getWorkerName());
        }
        return infoObject;
    }

    public int getSnFactory(){
        return ++this.snFactory;
    }

    private Worker getWorkerBySn(int snOfWorker){
        return workerList.get(snOfWorker);
    }

    public InfoObject printWorkersConstrainsBySn(int workerSn){
        InfoObject infoObject = new InfoObject("",true);
        if(!(this.getWorkerList().containsValue(getWorkerBySn(workerSn)))){
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        System.out.println(getWorkerBySn(workerSn).printConstrains());
        return infoObject;
    }

    public InfoObject addConstrainsToWorkerByWorkerSn(int workerSn, String day, String shiftType){
        InfoObject infoObject = new InfoObject("Worker constrains updated successfully",true);
        enums selectedDay;
        enums sType;
        try {
            selectedDay = enums.valueOf(day);
        }
        catch (Exception e){
            infoObject.setMessage("No such day");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        try{
            sType = enums.valueOf(shiftType);
        }
        catch (Exception e){
            infoObject.setMessage("No such shift type");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        if(!(this.getWorkerList().containsValue(getWorkerBySn(workerSn)))){
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        this.getWorkerBySn(workerSn).addConstrainsToWorker(selectedDay,sType);

        return infoObject;
    }

    public InfoObject addWorker(int id, String name, int phoneNumber, int bankAccount, int salary, String _date, String jobTitle) {
        InfoObject infoObject = new InfoObject("Worker added successfully",true);
        Date date = parseDate(_date);
        infoObject = validateWorkerCredentials(id, name, phoneNumber, bankAccount, salary, jobTitle, infoObject, date);
        if(!infoObject.isSucceeded()){
            return infoObject;
        }
        Worker workerToAdD = new Worker(id,name,phoneNumber,bankAccount,salary,date,jobTitle,getSnFactory());
        workerList.put(workerToAdD.getWorkerSn(),workerToAdD);
        return infoObject;
    }

    private InfoObject validateWorkerCredentials(int id, String name, int phoneNumber, int bankAccount, int salary, String jobTitle, InfoObject infoObject, Date date) {
        if(date==null){
            infoObject.setMessage("Invalid date format");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        if(id<0){
            infoObject.setMessage("Invalid worker id");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        for(Worker worker: workerList.values()){
            if(worker.getWorkerId() == id){
                infoObject.setMessage("There is already Worker with this ID");
                infoObject.setIsSucceeded(false);
                return infoObject;
            }
        }
        if(phoneNumber<0){
            infoObject.setMessage("Invalid phone number");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        if(bankAccount<0){
            infoObject.setMessage("Invalid bank account");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        if(salary<0){
            infoObject.setMessage("Invalid salary");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        if(name.equals("")){
            infoObject.setMessage("Worker must to have a name");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        if(jobTitle.equals("")){
            infoObject.setMessage("Worker must have job title");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        return infoObject;
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

    public InfoObject printWorkerBySn(int workerSn) {
        InfoObject infoObject = new InfoObject("",true);
        if(!(this.getWorkerList().containsValue(getWorkerBySn(workerSn)))){
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        System.out.println(getWorkerBySn(workerSn));
        return infoObject;
    }

    public InfoObject setWorkerConstrainsBySn(int workerSn) {
        InfoObject infoObject = new InfoObject("",true);
        if(!(this.getWorkerList().containsValue(getWorkerBySn(workerSn)))){
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        getWorkerBySn(workerSn).setWorkerConstrains();
        return infoObject;
    }

    public Worker getWorkerById(int workerId) {
        Worker workerToReturn=null;
        for(Worker worker : workerList.values()){
            if(worker.getWorkerId() == workerId){
                workerToReturn = worker;
            }
        }
        return workerToReturn;
    }
}