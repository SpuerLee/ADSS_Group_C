import javafx.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WorkerController {
    private HashMap<Integer,Worker> workerList;
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

    public List<Worker> getAllAvailableWorkers(String _date,int _shiftType) throws ParseException {


        // convert date to day
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(_date);
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        HR.Day selectedDay=selecetShiftDay(dayOfWeek);
        HR.ShiftType sType =selectShiftType(_shiftType);

        List<Worker> workersToPrint = new LinkedList<Worker>();

        for (Worker worker : workerList.values()) {
            if(!worker.getConstrains().containsKey(new Pair<>(selectedDay,sType))){
                workersToPrint.add(worker);
            }
        }

        return workersToPrint;
    }

    public boolean validateWorker(){
        return false;
    }

    public void printAllManagers(String date, int shiftType) throws ParseException {
        List<Worker> listOfAvailableWorkers = WorkerController.getInstance().getAllAvailableWorkers(date,shiftType);
        for (Worker manager : listOfAvailableWorkers) {
            if(manager.getJobTitle().equals("Manager")) {
                System.out.println(manager.getSn() + ". ID: " + manager.getId() + " Name: " + manager.getName());
            }
        }
    }

    public void printAllWorkers(String date, int shiftType) throws ParseException {
        List<Worker> listOfAvailableWorkers = WorkerController.getInstance().getAllAvailableWorkers(date,shiftType);
        for (Worker listOfAvailableWorker : listOfAvailableWorkers) {
            if(!listOfAvailableWorker.getJobTitle().equals("Manager")) {
                System.out.println(listOfAvailableWorker.getSn() + ". ID: " + listOfAvailableWorker.getId() + " Name: " + listOfAvailableWorker.getName() + " Job title: " + listOfAvailableWorker.getJobTitle());
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
        workerList.get(workerSn).setSalary(newSalaty);
    }

    public void printAllWorker(){
        for(Worker worker : workerList.values()){
            System.out.println(worker.getSn() + ". ID: " + worker.getId() + " Name: " + worker.getName());
        }
    }

    public int getSnFactory(){
        return this.snFactory++;
    }

    public Worker getWorkerBySn(int snOfWorker){
        return this.workerList.get(snOfWorker);
    }

    public void addConstrainsToWorkerByWorkerSn(int workerSn,String day,int _shiftType){
        HR.Day selectedDay= selecetShiftDay(day);
        HR.ShiftType sType = selectShiftType(_shiftType);

        WorkerController.getInstance().getWorkerBySn(workerSn).addConstrains(selectedDay,sType);

    }

    public HR.Day selecetShiftDay(String day){
        HR.Day selectedDay=null;
        switch (day) {
            case "Sunday":
                selectedDay = HR.Day.SUNDAY;
                break;
            case "Monday":
                selectedDay = HR.Day.MONDAY;
                break;
            case "Tuesday":
                selectedDay = HR.Day.TUESDAY;
                break;
            case "Wednesday":
                selectedDay = HR.Day.WEDNESDAY;
                break;
            case "Thursday":
                selectedDay = HR.Day.THURSDAY;
                break;
            case "Friday":
                selectedDay = HR.Day.FRIDAY;
                break;
            case "Saturday":
                selectedDay = HR.Day.SATURDAY;
                break;
        }
        return selectedDay;
    }

    public static HR.ShiftType selectShiftType(int _shiftType) {
        HR.ShiftType sType=null;
        switch (_shiftType){
            case 1:
                sType = HR.ShiftType.MORNING;
                break;
            case 2:
                sType = HR.ShiftType.NIGHT;
                break;
        }
        return  sType;
    }

    public int addWorker(int id, String name, int phoneNumber, int bankAccount, int salary, String _date, String jobTitle) throws ParseException {

        Date date = new SimpleDateFormat("yyyy-M-d").parse(_date);
        Worker workerToAdD = new Worker(id,name,phoneNumber,bankAccount,salary,date,jobTitle,getSnFactory());
        workerList.put(workerToAdD.getSn(),workerToAdD);
        return workerToAdD.getSn();

    }





}
