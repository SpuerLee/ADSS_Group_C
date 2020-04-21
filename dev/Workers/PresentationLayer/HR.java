package Workers.PresentationLayer;


import Workers.BusinessLayer.Modules.Shift;
import Workers.BusinessLayer.Modules.Worker;
import Workers.BusinessLayer.Utils.InfoObject;
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
        Scanner sc=new Scanner(System.in);
        System.out.println("Welcome to SuperLee");
        System.out.println("1. Choose start with data");
        System.out.println("2. Start new system");
        int selectedOption = sc.nextInt();
        if(selectedOption == 1){
            initSuperLeeWithWorkers();
        }
        printMenu();
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

    public static void addShift(Scanner sc) {
        System.out.println("Please select a date - dd-mm-yyyy");
        String date = sc.next();
        System.out.println("Please select shift type Morning or Night");
        String shiftType = sc.next().toUpperCase(); // { Morning , Night }
        System.out.println("Please select manager SN for the shift");
        checkResponse(SystemInterface.getInstance().printAllManagers(date,shiftType));
        int selectedManagerSn = sc.nextInt();
        System.out.println("Please select workers SN's for the shift");
        checkResponse(SystemInterface.getInstance().printAllWorkers(date, shiftType));
        String chosenWorkersSn = sc.next(); // 1,2,6,9
        checkResponse(SystemInterface.getInstance().createShift(shiftType, selectedManagerSn, chosenWorkersSn, date));
    }

    public static void displayShifts(Scanner sc){
        checkResponse(SystemInterface.getInstance().printAllShifts());
        int shiftSn = sc.nextInt();
        checkResponse(SystemInterface.getInstance().printShift(shiftSn));
    }

    public static void addWorker(Scanner sc)  {
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
        System.out.println("Enter 0 to stop");
        checkResponse(SystemInterface.getInstance().addWorker(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle));
        String constrainsDay = sc.next(); // { Day }
        addConstrains(sc, SystemInterface.getInstance().getWorkerById(workerId).getWorkerSn(), constrainsDay);
    }

    private static void addConstrains(Scanner sc, int workerToAddSn, String constrainsDay) {
        while(!constrainsDay.equals("0")){
            System.out.println("Enter shift type Morning or Night");
            String _shiftType = sc.next().toUpperCase(); // { Type }
            checkResponse(SystemInterface.getInstance().addConstrainsToWorkerByWorkerSn(workerToAddSn,constrainsDay,_shiftType));
            System.out.println("Enter constrains day:");
            constrainsDay = sc.next().toUpperCase(); // { Day }
        }
    }

    public static void displayWorkers(Scanner sc){
        checkResponse(SystemInterface.getInstance().printAllWorkers());
        int workerSn = sc.nextInt();
        checkResponse(SystemInterface.getInstance().printWorkerBySn(workerSn));
        System.out.println("Choose action: ");
        System.out.println("1. Edit worker constrains");
        System.out.println("2. Edit worker salary");
        System.out.println("3. Fire worker");
        System.out.println("Enter 0 to stop");
        int userChooseForWorker = sc.nextInt();

        switch (userChooseForWorker) {
            case 1: // Edit constrains
                EditWorkerConstrains(sc, workerSn);
                break;
            case 2: // Edit salary
                EditWorkerSalary(sc, workerSn);
                break;
            case 3: // Fire worker
                checkResponse(SystemInterface.getInstance().removeWorker(workerSn));
                break;
        }

    }

    private static void EditWorkerSalary(Scanner sc, int workerSn) {
        System.out.println("Enter new salary");
        int newSalary = sc.nextInt();
        SystemInterface.getInstance().setNewSalaryBySn(workerSn, newSalary);
    }

    private static void EditWorkerConstrains(Scanner sc, int workerSn) {
        checkResponse(SystemInterface.getInstance().setWorkerConstrains(workerSn));
        System.out.println("Enter constrains day:");
        String constrainsDay = sc.next().toUpperCase(); // { Day }
        addConstrains(sc, workerSn, constrainsDay);
        System.out.println("These constrains have been added:");
        checkResponse(SystemInterface.getInstance().printWorkerConstrains(workerSn));
    }

    public static void initSuperLeeWithWorkers() {
        SystemInterface.getInstance().addWorker(100,"Andrey Palman",100,123,100,"2020-04-15","Cashier");
        SystemInterface.getInstance().addWorker(101,"Hadar Kor",101,124,2500,"2020-04-15","Manager");
        SystemInterface.getInstance().addWorker(102,"Tomer Hacham",102,125,10000,"2020-04-15","Storekeeper");
        SystemInterface.getInstance().createShift("MORNING",2,"1,3","19-12-2020");
    }

    private static void checkResponse(InfoObject infoObject){
        if(!infoObject.isSucceeded()){
            System.out.println(infoObject.getMessage());
            printMenu();
        } else {
            if(!infoObject.getMessage().equals("")){
                System.out.println(infoObject.getMessage());
            }
        }
    }
}
