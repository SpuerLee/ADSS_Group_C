package Workers.InterfaceLayer;

import Workers.BusinessLayer.ShiftController;
import Workers.BusinessLayer.WorkerController;
import Workers.BusinessLayer.Utils.Shift;
import Workers.BusinessLayer.Utils.Worker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HR {

    public HR(){

    }

    public void start() throws ParseException {
        //initSuperLeeWithWorkers();
        System.out.println("Welcome to SuperLee");
        printMenu();

        Scanner sc=new Scanner(System.in);
        int userChoose = sc.nextInt();
        while(userChoose != 0) {
            switch (userChoose) {
                case 1: // add shift
                    addShift(sc);
                    break;
                case 2: // Display shift
                    displayShifts(sc);
                    break;
                case 3: // add worker
                    addWorker(sc);
                    break;
                case 4: // Display Worker
                    displayWorkers(sc);
                    break;
            }
            printMenu();
            userChoose = sc.nextInt();
        }
    }

    private void printMenu(){
        System.out.println("Enter your next choice:");
        System.out.println("1. Add shift");
        System.out.println("2. Display shifts");
        System.out.println("3. Add worker");
        System.out.println("4. Display workers");
        System.out.println("Enter 0 to stop");
    }

    public void addShift(Scanner sc) throws ParseException {
        System.out.println("Please select a date - dd/mm/yyyy");
        String date = sc.next();
        System.out.println("Please select shift type Morning or Night");
        String shiftType = sc.next().toUpperCase(); // { Morning , Night }
        System.out.println("Please select manager SN for the shift");
        boolean Manager = WorkerController.getInstance().printAllManagers(date,shiftType);
        if(Manager) {
            int selectedManagerSn = sc.nextInt();
            System.out.println("Please select workers SN's for the shift");
            WorkerController.getInstance().printAllWorkers(date, shiftType);
            String chosenWorkersSn = sc.next(); // 1,2,6,9
            ShiftController.getInstance().createShift(shiftType, selectedManagerSn, chosenWorkersSn, date);
        }else{
            System.out.println("We're sorry, Shift must have a Manager.");
        }
    }

    public  void displayShifts(Scanner sc){
        HashMap<Integer, Shift> shiftHistory = ShiftController.getInstance().getShiftHistory();
        if(!shiftHistory.isEmpty()) {
            System.out.println("Select shift by SN");
            for (Shift shift : shiftHistory.values()) {
                SimpleDateFormat daty = new SimpleDateFormat("dd/MM/yyyy");
                String dat = daty.format(shift.getDate());
                System.out.println(shift.getShiftSn() + ". Date: " + dat + " Type: " + shift.getShiftType());
            }
            int shiftSn = sc.nextInt();
            ShiftController.getInstance().printShift(shiftSn);
        } else {
            System.out.println("No shifts to display\n");
        }
    }

    public  void addWorker(Scanner sc) throws ParseException {
        System.out.println("Enter worker Id:");
        int workerId = sc.nextInt();
        System.out.println("Enter worker name:");
        String workerName = sc.next();
        System.out.println("Enter phone:");
        int workerPhoneNumber = sc.nextInt();
        System.out.println("Enter bank account:");
        int workerBankAccount = sc.nextInt();
        System.out.println("Enter salary:");
        int workerSalary = sc.nextInt();
        System.out.println("Enter starting date - dd/mm/yyyy");
        String dateOfStart = sc.next();
        System.out.println("Enter job title:");
        String workerJobTitle = sc.next();
        System.out.println("Enter constrains day:");
        int workerToAddSn = WorkerController.getInstance().addWorker(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle);
        String constrainsDay = sc.next(); // { Day }
        addConstrains(sc, workerToAddSn, constrainsDay);
    }

    private void addConstrains(Scanner sc, int workerToAddSn, String constrainsDay) {
        while(!constrainsDay.equals("0")){
            System.out.println("Enter shift type Morning or Night");
            String _shiftType = sc.next().toUpperCase(); // { Type }
            WorkerController.getInstance().addConstrainsToWorkerByWorkerSn(workerToAddSn,constrainsDay,_shiftType);
            System.out.println("Enter constrains day:");
            constrainsDay = sc.next().toUpperCase(); // { Day }
        }
    }

    public void displayWorkers(Scanner sc){
        if(WorkerController.getInstance().printAllWorker()) {
            int workerSn = sc.nextInt();
            WorkerController.getInstance().printWorkerBySn(workerSn);
            System.out.println("Choose action: ");
            System.out.println("1. Edit worker constrains");
            System.out.println("2. Edit worker salary");
            System.out.println("3. Fire worker");
            System.out.println("Enter 0 to stop");
            int userChooseForWorker = sc.nextInt();
            switch (userChooseForWorker) {
                case 1: // Edit constrains
                    WorkerController.getInstance().getWorkerBySn(workerSn).setWorkerConstrains();
                    System.out.println("Enter constrains day:");
                    String constrainsDay = sc.next().toUpperCase(); // { Day }
                    addConstrains(sc, workerSn, constrainsDay);
                    System.out.println("These constrains have been added: ");
                    System.out.println(WorkerController.getInstance().getWorkerBySn(workerSn).printConstrains());
                    break;
                case 2: // Edit salary
                    System.out.println("Enter new salary");
                    int newSalary = sc.nextInt();
                    WorkerController.getInstance().setNewSalaryBySn(workerSn, newSalary);
                    break;
                case 3: // Fire worker
                    WorkerController.getInstance().removeWorker(workerSn);
                    break;
            }
        }else {
            System.out.println("No workers to display\n");
        }
    }

    public void initSuperLeeWithWorkers() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-15");
        Worker w1 = new Worker(100,"Andrey Palman",100,123,100,date,"Cashier", WorkerController.getInstance().getSnFactory());
        Worker w2 = new Worker(101,"Hadar Kor",101,124,2500,date,"Manager", WorkerController.getInstance().getSnFactory());
        Worker w3 = new Worker(102,"Tomer Hacham",102,125,10000,date,"Storekeeper", WorkerController.getInstance().getSnFactory());
        WorkerController.getInstance().getWorkerList().put(w1.getWorkerSn(),w1);
        WorkerController.getInstance().getWorkerList().put(w2.getWorkerSn(),w2);
        WorkerController.getInstance().getWorkerList().put(w3.getWorkerSn(),w3);
    }
}
