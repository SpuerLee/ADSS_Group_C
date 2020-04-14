import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HR {

    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    public enum ShiftType{
        MORNING,
        NIGHT
    }

    public HR(){

    }

    public void start() throws ParseException {
        initSuperLeeWithWorkers();
        System.out.println("Welcome to SuperLee");
        System.out.println("Select option:");
        System.out.println("1. Add shift");
        System.out.println("2. Display shift");
        System.out.println("3. Add worker");
        System.out.println("4. Display worker");
        System.out.println("Enter 0 to stop");

        Scanner sc=new Scanner(System.in);
        int userChoose = sc.nextInt();
        while(userChoose != 0) {
            switch (userChoose) {
                case 1: // add shift
                    addShift(sc);
                    break;
                case 2: // Display shift
                    displayShift(sc);
                    break;
                case 3: // add worker
                    addWorker(sc);
                    break;
                case 4: // Display Worker
                    displayWorker(sc);
                    break;
            }
            System.out.println("Enter your next choice:");
            System.out.println("1. Add shift");
            System.out.println("2. Display shift");
            System.out.println("3. Add worker");
            System.out.println("4. Display worker");
            System.out.println("Enter 0 to stop");
            userChoose = sc.nextInt();
        }
    }

    public void addShift(Scanner sc) throws ParseException {
        System.out.println("Please select a date");
        String date = sc.next();
        System.out.println("Please select shift type");
        System.out.println("1.Morning");
        System.out.println("2.Night");
        int shiftType = sc.nextInt();
        System.out.println("Please select manager SN for the shift");
        WorkerController.getInstance().printAllManagers(date,shiftType);
        int selectedManagerSn = sc.nextInt();
        System.out.println("Please select workers SN's for the shift");
        WorkerController.getInstance().printAllWorkers(date,shiftType);
        String chosenWorkersSn = sc.next(); // 1,2,6,9
        ShiftController.getInstance().createShift(shiftType,selectedManagerSn,chosenWorkersSn,date);
    }

    public  void displayShift(Scanner sc){
        System.out.println("Select shift by SN");
        HashMap<Integer,Shift> shiftHistory = ShiftController.getInstance().getShiftHistory();
        for(Shift shift : shiftHistory.values()){
            System.out.println(shift.getSn() + ". Date: " + shift.getDate() + " Type: " + shift.getStype());
        }
        int shiftSn = sc.nextInt();
        ShiftController.getInstance().printShift(shiftSn);
    }

    public  void addWorker(Scanner sc) throws ParseException {
        System.out.println("Enter worker Id:");
        int workerId = sc.nextInt();
        System.out.println("Enter worker name:");
        String workerName = sc.next();
        System.out.println("Enter phone:");
        int workerphoneNumber = sc.nextInt();
        System.out.println("Enter bank account:");
        int workerBankAcount = sc.nextInt();
        System.out.println("Enter salary:");
        int workerSalary = sc.nextInt();
        System.out.println("Enter starting date");
        String dateOfStart = sc.next();
        System.out.println("Enter job title:");
        String workerJobTitle = sc.next();
        System.out.println("Enter constrains day:");
        int workerToAddSn = WorkerController.getInstance().addWorker(workerId,workerName,workerphoneNumber,workerBankAcount,workerSalary,dateOfStart,workerJobTitle);
        String constrainsDay = sc.next(); // { Day }
        while(!constrainsDay.equals("Stop")){
            System.out.println("Enter shift type");
            int _shiftType = sc.nextInt(); // { Type }
            WorkerController.getInstance().addConstrainsToWorkerByWorkerSn(workerToAddSn,constrainsDay,_shiftType);
            System.out.println("Enter constrains day:");
            constrainsDay = sc.next(); // { Day }
        }
    }

    public void displayWorker(Scanner sc){
        System.out.println("Choose worker by SN");
        WorkerController.getInstance().printAllWorker();
        int workerSn = sc.nextInt();
        WorkerController.getInstance().printWorkerBySn(workerSn);
        System.out.println("Choose action: ");
        System.out.println("1. Edit worker constrains");
        System.out.println("2. Edit worker salary");
        System.out.println("3. Fire worker");
        int userChooseForWorker = sc.nextInt();
        switch (userChooseForWorker){
            case 1: // Edit constrains
                WorkerController.getInstance().getWorkerBySn(workerSn).setConstrains();
                System.out.println("Enter constrains day:");
                String constrainsDay = sc.next(); // { Day }
                while(!constrainsDay.equals("Stop")){
                    System.out.println("Enter shift type");
                    int _shiftType = sc.nextInt(); // { Type }
                    WorkerController.getInstance().addConstrainsToWorkerByWorkerSn(workerSn,constrainsDay,_shiftType);
                    System.out.println("Enter constrains day:");
                    constrainsDay = sc.next(); // { Day }
                }
                break;
            case 2: // Edit salary
                System.out.println("Enter new salary");
                int newSalary = sc.nextInt();
                WorkerController.getInstance().setNewSalaryBySn(workerSn,newSalary);
                break;
            case 3: // Fire worker
                WorkerController.getInstance().removeWorker(workerSn);
                break;
        }
    }

    public void initSuperLeeWithWorkers() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-M-d").parse("2020-4-15");
        Worker w1 = new Worker(100,"Andrey Palman",100,123,100,date,"Cashier",WorkerController.getInstance().getSnFactory());
        Worker w2 = new Worker(101,"Hadar Kor",101,124,2500,date,"Manager",WorkerController.getInstance().getSnFactory());
        Worker w3 = new Worker(102,"Tomer Hacham",102,125,10000,date,"Storekeeper",WorkerController.getInstance().getSnFactory());
        WorkerController.getInstance().getWorkerList().put(w1.getSn(),w1);
        WorkerController.getInstance().getWorkerList().put(w2.getSn(),w2);
        WorkerController.getInstance().getWorkerList().put(w3.getSn(),w3);
    }
}
