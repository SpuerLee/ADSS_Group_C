package Business_Layer.Workers.Controllers;

import Business_Layer.Service;
import Business_Layer.Workers.Modules.Worker.Driver;
import Business_Layer.Workers.Utils.InfoObject;
import Business_Layer.Workers.Modules.Worker.Worker;
import Business_Layer.Workers.Utils.enums;

import java.text.SimpleDateFormat;
import java.util.*;

public class WorkerController {

    private int snFactory;
    private int currentStoreSN;

    public WorkerController() {
        this.snFactory = 0;
        this.currentStoreSN = -1;
    }

    public List<String> getAllDrivers(Date date,String shiftType,List<String> licenses){
        List<String> listToReturn = null;

        enums sType;
        try{
            sType = enums.valueOf(shiftType);
        }
        catch (Exception e){
            System.out.println("No such shift type");
            return listToReturn;
        }

        listToReturn= new LinkedList<>();

        for (Worker worker : Service.getInstance().getWorkerList().values()) {
            if(worker.available(date,sType)){
                if(worker.getWorkerJobTitle().toUpperCase().equals("DRIVER")) {
                    // check if driver license in the licenses list

                    String driverToString = worker.getWorkerSn() + ". " + worker.getWorkerName();
                    listToReturn.add(driverToString);
                }
            }
        }

        return listToReturn;
    }

    public boolean isStooreKeeperAvaialble(Date date,String shiftType, int storeSN){
        return false;
    }

    private List<Worker> getAllAvailableWorkers(Date date, enums shiftType) {
        List<Worker> workersToPrint = new LinkedList<>();

        for (Worker worker : Service.getInstance().getWorkerList(getCurrentStoreSN()).values()) {
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
            if(manager.getWorkerJobTitle().toUpperCase().equals("MANAGER")) {
                System.out.println(manager.getWorkerSn() + ". ID: " + manager.getWorkerId() + " Name: " + manager.getWorkerName() + " Job title: " + manager.getWorkerJobTitle());
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
            if(!listOfAvailableWorker.getWorkerJobTitle().toUpperCase().equals("MANAGER")) {
                System.out.println(listOfAvailableWorker.getWorkerSn() + ". ID: " + listOfAvailableWorker.getWorkerId() + " Name: " + listOfAvailableWorker.getWorkerName() + " Job title: " + listOfAvailableWorker.getWorkerJobTitle());
                availableWorker = true;
            }
        }
        if(!availableWorker){
            infoObject.setMessage("There are no available workers");
            infoObject.setIsSucceeded(true);
        }
        return infoObject;
    }

    public InfoObject removeWorkerBySn(int workerSn){
        InfoObject infoObject = new InfoObject("Worker removed successfully",true);
        if(Service.getInstance().getWorkerList(getCurrentStoreSN()).containsValue(getWorkerBySn(workerSn))){
            Service.getInstance().getWorkerList().remove(workerSn);
        } else {
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
        }
        return infoObject;
    }

    public InfoObject setNewSalaryBySn(int workerSn, int newSalary){
        InfoObject infoObject = new InfoObject("Workers salary edited successfully",true);
        if(Service.getInstance().getWorkerList(getCurrentStoreSN()).containsValue(getWorkerBySn(workerSn))){
            if(newSalary<0){
                infoObject.setMessage("Invalid new salary");
                infoObject.setIsSucceeded(false);
            } else {
                Service.getInstance().getWorkerList(getCurrentStoreSN()).get(workerSn).setWorkerSalary(newSalary);
            }
        } else {
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
        }
        return infoObject;
    }

    public InfoObject printAllWorker(){
        InfoObject infoObject = new InfoObject("",true);
        if(Service.getInstance().getWorkerList(getCurrentStoreSN()).isEmpty()){
            infoObject.setMessage("The company has no workers yet");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        System.out.println("Choose worker by SN");
        for(Worker worker : Service.getInstance().getWorkerList(getCurrentStoreSN()).values()){
            System.out.println(worker.getWorkerSn() + ". ID: " + worker.getWorkerId() + " Name: " + worker.getWorkerName() + " Job title: " + worker.getWorkerJobTitle());
        }
        return infoObject;
    }

    private int getSnFactory(){
        return ++this.snFactory;
    }

    private Worker getWorkerBySn(int snOfWorker){
        return Service.getInstance().getWorkerList(getCurrentStoreSN()).get(snOfWorker);
    }

    public InfoObject printWorkersConstrainsBySn(int workerSn){
        InfoObject infoObject = new InfoObject("",true);
        if(!(Service.getInstance().getWorkerList(getCurrentStoreSN()).containsValue(getWorkerBySn(workerSn)))){
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        System.out.println(getWorkerBySn(workerSn).printConstrains());
        return infoObject;
    }

    private InfoObject addConstrainsToWorkerByWorkerSn(int workerSn, String day, String shiftType){
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
        if(!(Service.getInstance().getWorkerList(getCurrentStoreSN()).containsValue(getWorkerBySn(workerSn)))){
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        this.getWorkerBySn(workerSn).addConstrainsToWorker(selectedDay,sType);

        return infoObject;
    }

    public InfoObject addDriver(int id, String name, String phoneNumber, int bankAccount, int salary, String dateToParse, String jobTitle, String constrains, String licenses){
        InfoObject infoObject = new InfoObject("",true);
        Date date = parseDate(dateToParse);
        String[] workerConstrains=null;
        if(!constrains.toUpperCase().equals("NONE")){
            try{
                workerConstrains = constrains.split(",");
            }
            catch (Exception e){
                infoObject.setMessage("Invalid constrains format");
                infoObject.setIsSucceeded(false);
                return infoObject;
            }
        }
        infoObject = validateWorkerCredentials(id, name, phoneNumber, bankAccount, salary, jobTitle, infoObject, date);
        if(!infoObject.isSucceeded()){
            return infoObject;
        }
        Driver driverToAdd = new Driver(id,name,phoneNumber,bankAccount,salary,date,jobTitle,getSnFactory(),getCurrentStoreSN(),licenses);
        Service.getInstance().getWorkerList().put(driverToAdd.getWorkerSn(),driverToAdd);
        if(!constrains.toUpperCase().equals("NONE")) {
            if (!workerConstrains[0].equals("")) {
                for (String workerConstrain : workerConstrains) {
                    String day = "";
                    String shiftType = "";
                    try {
                        day = workerConstrain.split("-")[0].toUpperCase();
                        shiftType = workerConstrain.split("-")[1].toUpperCase();
                    }catch (Exception e){
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage("Invalid constrains format");
                        return infoObject;
                    }
                    infoObject = addConstrainsToWorkerByWorkerSn(driverToAdd.getWorkerSn(), day, shiftType);
                    if (!(infoObject.isSucceeded())) {
                        Service.getInstance().getWorkerList().remove(driverToAdd.getWorkerSn());
                        return infoObject;
                    }
                }
            }
        }
        infoObject.setMessage("Worker added successful");
        return infoObject;
    }

    public InfoObject addWorker(int id, String name, String phoneNumber, int bankAccount, int salary, String dateToParse, String jobTitle, String constrains) {
        InfoObject infoObject = new InfoObject("",true);
        Date date = parseDate(dateToParse);
        String[] workerConstrains=null;
        if(!constrains.toUpperCase().equals("NONE")){
            try{
                workerConstrains = constrains.split(",");
            }
            catch (Exception e){
                infoObject.setMessage("Invalid constrains format");
                infoObject.setIsSucceeded(false);
                return infoObject;
            }
        }
        infoObject = validateWorkerCredentials(id, name, phoneNumber, bankAccount, salary, jobTitle, infoObject, date);
        if(!infoObject.isSucceeded()){
            return infoObject;
        }
        Worker workerToAdD = new Worker(id,name,phoneNumber,bankAccount,salary,date,jobTitle,getSnFactory(),getCurrentStoreSN());
        Service.getInstance().getWorkerList().put(workerToAdD.getWorkerSn(),workerToAdD);
        if(!constrains.toUpperCase().equals("NONE")) {
            if (!workerConstrains[0].equals("")) {
                for (String workerConstrain : workerConstrains) {
                    String day = "";
                    String shiftType = "";
                    try {
                        day = workerConstrain.split("-")[0].toUpperCase();
                        shiftType = workerConstrain.split("-")[1].toUpperCase();
                    }catch (Exception e){
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage("Invalid constrains format");
                        return infoObject;
                    }
                    infoObject = addConstrainsToWorkerByWorkerSn(workerToAdD.getWorkerSn(), day, shiftType);
                    if (!(infoObject.isSucceeded())) {
                        Service.getInstance().getWorkerList().remove(workerToAdD.getWorkerSn());
                        return infoObject;
                    }
                }
            }
        }
        infoObject.setMessage("Worker added successful");
        return infoObject;
    }

    private InfoObject validateWorkerCredentials(int id, String name, String phoneNumber, int bankAccount, int salary, String jobTitle, InfoObject infoObject, Date date) {
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
        for(Worker worker: Service.getInstance().getWorkerList(getCurrentStoreSN()).values()){
            if(worker.getWorkerId() == id){
                infoObject.setMessage("There is already Worker with this ID");
                infoObject.setIsSucceeded(false);
                return infoObject;
            }
        }
        if(phoneNumber.equals("")){
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

    private Date parseDate(String dateToParse){
        Date date;
        try {
            date = new SimpleDateFormat("d-MM-yyyy").parse(dateToParse);
        }
        catch (Exception e){
            return null;
        }
        return date;
    }

    public InfoObject printWorkerBySn(int workerSn) {
        InfoObject infoObject = new InfoObject("",true);
        if(!(Service.getInstance().getWorkerList(getCurrentStoreSN()).containsValue(getWorkerBySn(workerSn)))){
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        System.out.println(getWorkerBySn(workerSn));
        return infoObject;
    }

    public InfoObject resetWorkerConstrainsBySn(int workerSn) {
        InfoObject infoObject = new InfoObject("All constrains has been removed",true);
        if(!(Service.getInstance().getWorkerList(getCurrentStoreSN()).containsValue(getWorkerBySn(workerSn)))){
            infoObject.setMessage("There is no worker with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        getWorkerBySn(workerSn).resetConstrains();
        return infoObject;
    }

    public InfoObject editWorkerConstrainsBySn(int workerSn, String newConstrains) {
        InfoObject infoObject = new InfoObject("Edit workers constrains successfully",true);
        if(!newConstrains.toUpperCase().equals("NONE")) {
            String[] workersConstrains = newConstrains.split(",");
            if (workersConstrains[0].equals("")) {
                return infoObject;
            } else {
                for (String workerConstrain : workersConstrains) {
                    String day= "";
                    String shiftType = "";
                    try {
                        day = workerConstrain.split("-")[0].toUpperCase();
                        shiftType = workerConstrain.split("-")[1].toUpperCase();
                    }
                    catch (Exception e){
                        infoObject.setMessage("Invalid constrains format");
                        infoObject.setIsSucceeded(false);
                        return infoObject;
                    }
                    infoObject = this.addConstrainsToWorkerByWorkerSn(workerSn, day, shiftType);
                    if (!infoObject.isSucceeded()) {
                        return infoObject;
                    }
                }
            }
        }else{
            resetWorkerConstrainsBySn(workerSn);
        }
        return infoObject;
    }

    public int getCurrentStoreSN() {
        return currentStoreSN;
    }

    public void setCurrentStoreSN(int currentStoreSN) {
        this.currentStoreSN = currentStoreSN;
    }

    public void resetSnFactory(){
        this.snFactory = 0;
    }
}
