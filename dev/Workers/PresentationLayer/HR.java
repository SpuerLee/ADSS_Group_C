package Workers.PresentationLayer;


import Workers.BusinessLayer.Utils.InfoObject;
import Workers.InterfaceLayer.SystemInterface;

import java.text.ParseException;
import java.util.Scanner;

public class HR {
    public static void main(String[] args) throws ParseException {
        start();
    }

    public static void start() {

        Scanner sc=new Scanner(System.in);
        System.out.println("Welcome to SuperLee");
        System.out.println("1. Choose start with data");
        System.out.println("2. Start new system");
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int selectedOption = sc.nextInt();
        if(selectedOption == 1){
            initSuperLeeWithWorkers();
        }
        workingLoop(sc);
    }

    private static void workingLoop(Scanner sc){
        printMenu();
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
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
                case 0: // quit
                    System.exit(0);
            }
            printMenu();
            while(!sc.hasNextInt()){
                System.out.println("Invalid input, please try again");
                sc.next();
            }
            userChoose = sc.nextInt();
        }
        System.exit(0);
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
        while(!sc.hasNext()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String date = sc.next();
        System.out.println("Please select shift type Morning or Night");
        while(!sc.hasNext()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String shiftType = sc.next().toUpperCase(); // { Morning , Night }
        System.out.println("Please select manager SN for the shift");
        checkResponse(SystemInterface.getInstance().printAllManagersAvailableInDates(date, shiftType), sc);
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int selectedManagerSn = sc.nextInt();
        System.out.println("Please select workers SN's for the shift");
        InfoObject infoObject = SystemInterface.getInstance().printAllWorkersAvailableInDates(date, shiftType);
        if(infoObject.getMessage().equals("There are no available workers")){
            System.out.println(infoObject.getMessage());
            checkResponse(SystemInterface.getInstance().createShift(shiftType, selectedManagerSn, "", date), sc);
        } else {
            while(!sc.hasNext()){
                System.out.println("Invalid input, please try again");
                sc.next();
            }
            String chosenWorkersSn = sc.next(); // 1,2,6,9
            chosenWorkersSn = chosenWorkersSn.replaceAll("\\s+", "");
            checkResponse(SystemInterface.getInstance().createShift(shiftType, selectedManagerSn, chosenWorkersSn, date), sc);
        }
    }

    public static void displayShifts(Scanner sc){
        checkResponse(SystemInterface.getInstance().printAllShifts(),sc);
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int shiftSn = sc.nextInt();
        if(shiftSn == 0){
            workingLoop(sc);
        }
        checkResponse(SystemInterface.getInstance().printShift(shiftSn),sc);
    }

    public static void addWorker(Scanner sc)  {
        System.out.println("Enter worker Id:");
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int workerId = sc.nextInt();
        System.out.println("Enter worker name:");
        String workerName = sc.nextLine();
        workerName = sc.nextLine();
        System.out.println("Enter phone:");
        while(!sc.hasNext()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String workerPhoneNumber = sc.nextLine();
        System.out.println("Enter bank account:");
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int workerBankAccount = sc.nextInt();
        System.out.println("Enter salary:");
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int workerSalary = sc.nextInt();
        System.out.println("Enter starting date - dd-mm-yyyy");
        while(!sc.hasNext()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String dateOfStart = sc.next();
        System.out.println("Enter job title:");
        while(!sc.hasNext()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String workerJobTitle = sc.next();
        System.out.println("Enter constrains: - Day-ShiftType , Day-ShiftType, etc...");
        while(!sc.hasNext()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String constrains = sc.next();
        constrains = constrains.replaceAll("\\s+","");
        checkResponse(SystemInterface.getInstance().addWorker(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle,constrains),sc);
    }

    public static void displayWorkers(Scanner sc) {
        checkResponse(SystemInterface.getInstance().printAllWorkers(), sc);
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int workerSn = sc.nextInt();
        if(workerSn == 0){
            workingLoop(sc);
        }
        checkResponse(SystemInterface.getInstance().printWorkerBySn(workerSn), sc);
        System.out.println("Choose action: ");
        System.out.println("1. Edit worker constrains");
        System.out.println("2. Edit worker salary");
        System.out.println("3. Fire worker");
        System.out.println("Enter 0 to stop");
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int userChooseForWorker = sc.nextInt();

        switch (userChooseForWorker) {
            case 1: // Edit constrains
                EditWorkerConstrains(sc, workerSn);
                break;
            case 2: // Edit salary
                EditWorkerSalary(sc, workerSn);
                break;
            case 3: // Fire worker
                checkResponse(SystemInterface.getInstance().removeWorkerBySn(workerSn), sc);
                checkResponse(SystemInterface.getInstance().removeLaterShiftForFiredManagerByManagerSn(workerSn),sc);
                break;
            case 0: // quit
                workingLoop(sc);
        }
    }

    private static void EditWorkerSalary(Scanner sc, int workerSn) {
        System.out.println("Enter new salary");
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int newSalary = sc.nextInt();
        checkResponse(SystemInterface.getInstance().setNewSalaryBySn(workerSn, newSalary),sc);
    }

    private static void EditWorkerConstrains(Scanner sc, int workerSn) {
        checkResponse(SystemInterface.getInstance().resetWorkerConstrainsBySn(workerSn), sc);
        System.out.println("Enter constrains: - Day-ShiftType , Day-ShiftType, etc...");
        while(!sc.hasNextInt()){
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String constrains = sc.next();
        constrains = constrains.replaceAll("\\s+","");
        checkResponse(SystemInterface.getInstance().editWorkerConstrainsBySn(workerSn,constrains),sc);
        checkResponse(SystemInterface.getInstance().printWorkerConstrainsBySn(workerSn), sc);
    }

    public static void initSuperLeeWithWorkers() {
        SystemInterface.getInstance().addWorker(100,"Andrey Palman","100",123,100,"15-04-2020","Cashier","");
        SystemInterface.getInstance().addWorker(101,"Hadar Kor","101",124,2500,"15-04-2020","Manager","");
        SystemInterface.getInstance().addWorker(102,"Tomer Hacham","102",125,10000,"15-04-2020","Storekeeper","");
        SystemInterface.getInstance().addWorker(103,"Amit Rubin","102",125,10000,"15-04-2020","Storekeeper","");
        SystemInterface.getInstance().addWorker(104,"Reut Levy","102",125,10000,"15-04-2020","Storekeeper","");
        SystemInterface.getInstance().addWorker(105,"Hadas Zaira","102",125,10000,"15-04-2020","Storekeeper","");
        SystemInterface.getInstance().addWorker(106,"Roi Benhus","102",125,10000,"15-04-2020","Storekeeper","");
        SystemInterface.getInstance().createShift("MORNING",2,"1,3","19-12-2020");
        SystemInterface.getInstance().createShift("NIGHT",2,"1,3,4","19-12-2020");
        SystemInterface.getInstance().createShift("MORNING",2,"1,3,5","20-12-2020");
        SystemInterface.getInstance().createShift("NIGHT",2,"1,3,6,7","20-12-2020");
        SystemInterface.getInstance().createShift("MORNING",2,"1,3,4,5,6,7","21-12-2020");
        SystemInterface.getInstance().createShift("NIGHT",2,"1,3,5","21-12-2020");
        SystemInterface.getInstance().createShift("MORNING",2,"1,6,7","22-12-2020");
        SystemInterface.getInstance().createShift("NIGHT",2,"1,4,5","22-12-2020");
    }

    private static void checkResponse(InfoObject infoObject,Scanner sc){
        if(!infoObject.isSucceeded()){
            System.out.println(infoObject.getMessage());
            workingLoop(sc);
        } else {
            if(!infoObject.getMessage().equals("")){
                System.out.println(infoObject.getMessage());
            }
        }
    }
}
