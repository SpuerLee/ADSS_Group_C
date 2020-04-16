package Workers.PresentationLayer;


import Workers.BusinessLayer.Utils.Shift;
import Workers.BusinessLayer.Utils.Worker;
import Workers.InterfaceLayer.SystemInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;


public class HR {
    public static void main(String[] args) throws ParseException {
        start();
    }

    public static void start() throws ParseException {
        initSuperLeeWithWorkers();
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

    private static void printMenu(){
        System.out.println("Enter your next choice:");
        System.out.println("1. Add shift");
        System.out.println("2. Display shifts");
        System.out.println("3. Add worker");
        System.out.println("4. Display workers");
        System.out.println("Enter 0 to stop");
    }

    public static void addShift(Scanner sc) throws ParseException {
        System.out.println("Please select a date - dd/mm/yyyy");
        String date = sc.next();
        System.out.println("Please select shift type Morning or Night");
        String shiftType = sc.next().toUpperCase(); // { Morning , Night }
        System.out.println("Please select manager SN for the shift");
        boolean Manager = SystemInterface.getInstance().printAllManagers(date,shiftType);
        if(Manager) {
            int selectedManagerSn = sc.nextInt();
            System.out.println("Please select workers SN's for the shift");
            SystemInterface.getInstance().printAllWorkers(date, shiftType);
            String chosenWorkersSn = sc.next(); // 1,2,6,9
            SystemInterface.getInstance().createShift(shiftType, selectedManagerSn, chosenWorkersSn, date);
        }else{
            System.out.println("We're sorry, Shift must have a Manager.");
        }
    }

    public static void displayShifts(Scanner sc){
        HashMap<Integer, Shift> shiftHistory = SystemInterface.getInstance().getShiftHistory();
        if(!shiftHistory.isEmpty()) {
            System.out.println("Select shift by SN");
            for (Shift shift : shiftHistory.values()) {
                SimpleDateFormat daty = new SimpleDateFormat("dd/MM/yyyy");
                String dat = daty.format(shift.getDate());
                System.out.println(shift.getShiftSn() + ". Date: " + dat + " Type: " + shift.getShiftType());
            }
            int shiftSn = sc.nextInt();
            SystemInterface.getInstance().printShift(shiftSn);
        } else {
            System.out.println("No shifts to display\n");
        }
    }

    public static void addWorker(Scanner sc) throws ParseException {
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
        int workerToAddSn = SystemInterface.getInstance().addWorker(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle);
        String constrainsDay = sc.next(); // { Day }
        addConstrains(sc, workerToAddSn, constrainsDay);
    }

    private static void addConstrains(Scanner sc, int workerToAddSn, String constrainsDay) {
        while(!constrainsDay.equals("0")){
            System.out.println("Enter shift type Morning or Night");
            String _shiftType = sc.next().toUpperCase(); // { Type }
            SystemInterface.getInstance().addConstrainsToWorkerByWorkerSn(workerToAddSn,constrainsDay,_shiftType);
            System.out.println("Enter constrains day:");
            constrainsDay = sc.next().toUpperCase(); // { Day }
        }
    }

    public static void displayWorkers(Scanner sc){
        if(SystemInterface.getInstance().printAllWorkers()) {
            int workerSn = sc.nextInt();
            SystemInterface.getInstance().printWorkerBySn(workerSn);
            System.out.println("Choose action: ");
            System.out.println("1. Edit worker constrains");
            System.out.println("2. Edit worker salary");
            System.out.println("3. Fire worker");
            System.out.println("Enter 0 to stop");
            int userChooseForWorker = sc.nextInt();
            switch (userChooseForWorker) {
                case 1: // Edit constrains
                    SystemInterface.getInstance().setWorkerConstrains(workerSn);
                    System.out.println("Enter constrains day:");
                    String constrainsDay = sc.next().toUpperCase(); // { Day }
                    addConstrains(sc, workerSn, constrainsDay);
                    System.out.println("These constrains have been added:");
                    SystemInterface.getInstance().printWorkerConstrains(workerSn);
                    break;
                case 2: // Edit salary
                    System.out.println("Enter new salary");
                    int newSalary = sc.nextInt();
                    SystemInterface.getInstance().setNewSalaryBySn(workerSn, newSalary);
                    break;
                case 3: // Fire worker
                    SystemInterface.getInstance().removeWorker(workerSn);
                    break;
            }
        }else {
            System.out.println("No workers to display\n");
        }
    }

    public static void initSuperLeeWithWorkers() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-15");
        Worker w1 = new Worker(100,"Andrey Palman",100,123,100,date,"Cashier",1);
        Worker w2 = new Worker(101,"Hadar Kor",101,124,2500,date,"Manager",2);
        Worker w3 = new Worker(102,"Tomer Hacham",102,125,10000,date,"Storekeeper",3);
        SystemInterface.getInstance().getWorkerList().put(w1.getWorkerSn(),w1);
        SystemInterface.getInstance().getWorkerList().put(w2.getWorkerSn(),w2);
        SystemInterface.getInstance().getWorkerList().put(w3.getWorkerSn(),w3);
    }
}
