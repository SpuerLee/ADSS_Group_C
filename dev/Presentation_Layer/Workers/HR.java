package Presentation_Layer.Workers;


import Business_Layer.Transportations.Utils.Buisness_Exception;
import Business_Layer.Workers.Utils.InfoObject;
import Interface_Layer.Workers.SystemInterfaceWorkers;
import Test.Workers.projectTests;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class HR {

    public static void Menu() {
        System.out.println("Welcome! Enter you choice");
        try {
            run();
        }
        catch (Exception e){

        }
    }

    public static void run() throws Buisness_Exception {
        System.out.println("1. Run tests");
        System.out.println("2. Start system");
        System.out.println("Enter 0 to exit program");
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int selectedOption = sc.nextInt();
        if (selectedOption == 1) {
            runTest();
            System.out.println();
        }
        if (selectedOption == 2) {
            start(sc);
        }
        if (selectedOption == 0) {
            throw new Buisness_Exception("Going back");
        }
        if (selectedOption < 0 || selectedOption > 2) {
            System.out.println("Invalid input, please try again");
            run();
        }
        run();

    }

    public static void runTest() {
        System.out.println("Starting Tests\n");
        final LauncherDiscoveryRequest request =
                LauncherDiscoveryRequestBuilder.request()
                        .selectors(selectClass(projectTests.class))
                        .build();

        final Launcher launcher = LauncherFactory.create();
        final SummaryGeneratingListener listener = new SummaryGeneratingListener();

        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        final TestExecutionSummary summary = listener.getSummary();

        final long testsFoundCount = summary.getTestsFoundCount();

        System.out.println("");


        final long succeededTests = summary.getTestsSucceededCount();
        System.out.println("Total tests Succeeded  " + succeededTests + "/" + testsFoundCount);

        final long testsSkippedCount = summary.getTestsSkippedCount();
        System.out.println("tests Skipped Count  " + testsSkippedCount + "/" + testsFoundCount);

        final long testsFailed = summary.getTestsFailedCount();
        System.out.println("tests Failed Count  " + testsFailed + "/" + testsFoundCount);

        final long testAborted = summary.getTestsAbortedCount();
        System.out.println("tests Aborted Count  " + testAborted + "/" + testsFoundCount);
        System.out.println();

    }

    public static void start(Scanner sc) {
        systemStart(sc);
    }

    private static void systemStart(Scanner sc) {
        System.out.println("Welcome to SuperLee");
        System.out.println("1. Choose start with data");
        System.out.println("2. Start new system");
        System.out.println("Enter 0 to exit program");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int selectedOption = sc.nextInt();
        if (selectedOption == 1) {
            initSuperLeeWithWorkers();
        }
        if (selectedOption == 0) {
            System.exit(0);
        }
        if (selectedOption < 0 || selectedOption > 2) {
            System.out.println("Invalid input, please try again");
            systemStart(sc);
        }
        chooseStore(sc);
    }

    private static void chooseStore(Scanner sc) {
        System.out.println("1. Choose store");
        System.out.println("2. Add store");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int userChoose = sc.nextInt();
        if (userChoose == 1) {
            if (!(SystemInterfaceWorkers.getInstance().printAllStores())) {
                // Empty
                chooseStore(sc);
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input, please try again");
                    sc.next();
                }
                userChoose = sc.nextInt();
                if (!(SystemInterfaceWorkers.getInstance().setCurrentStore(userChoose))) {
                    chooseStore(sc);
                }
            }
        } else if (userChoose == 2) {

            Scanner scan = new Scanner(System.in);
            System.out.println("Please choose the name of the store"); //chose area
            String name = scan.nextLine();
            System.out.println("Please choose city of the store");
            String city = scan.nextLine();
            System.out.println("Please choose street of the store");
            String street = scan.nextLine();
            System.out.println("Please choose street's number of the store");
            String number = scan.nextLine();
            System.out.println("Please enter name of contact for the store");
            String name_of_contact = scan.nextLine();
            System.out.println("Please enter contact's person phone");
            String phone = scan.nextLine();
            System.out.println("Please choose store's area (A/B/C/D)");
            String area = scan.nextLine();
            boolean result = false;
            try {
                result = SystemInterfaceWorkers.getInstance().addNewStore(name, city, street, number, name_of_contact, phone, area);
            } catch (Buisness_Exception e) {
                System.out.println("Can't add new Store!");
            }
            if (result) {
                System.out.println("The store was added successfully\n");
                workingLoop(sc);
            } else {
                System.out.println("Input error\n");
                chooseStore(sc);
            }
        } else {
            System.out.println("Invalid input, please try again");
            chooseStore(sc);
        }
    }

    private static void workingLoop(Scanner sc) {
        printMenu();
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int userChoose = sc.nextInt();
        while (userChoose != 0) {
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
                default:
                    System.out.println("Invalid input, please try again");
            }
            printMenu();
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input, please try again");
                sc.next();
            }
            userChoose = sc.nextInt();
        }
        System.exit(0);
    }

    private static void printMenu() {
        System.out.println("Enter your next choice:");
        System.out.println("1. Add shift");
        System.out.println("2. Display shifts");
        System.out.println("3. Add worker");
        System.out.println("4. Display workers");
        System.out.println("Enter 0 to exit program");
    }

    public static void addShift(Scanner sc) {
        System.out.println("Please select a date - dd-mm-yyyy");
        while (!sc.hasNext()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String date = sc.next();
        System.out.println("Please select shift type Morning or Night");
        while (!sc.hasNext()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String shiftType = sc.next().toUpperCase(); // { Morning , Night }
        System.out.println("Please select manager SN for the shift");
        checkResponse(SystemInterfaceWorkers.getInstance().printAllManagersAvailableInDates(date, shiftType), sc);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int selectedManagerSn = sc.nextInt();
        System.out.println("Please select workers SN's for the shift");
        InfoObject infoObject = SystemInterfaceWorkers.getInstance().printAllWorkersAvailableInDates(date, shiftType);
        if (infoObject.getMessage().equals("There are no available workers")) {
            System.out.println(infoObject.getMessage());
            checkResponse(SystemInterfaceWorkers.getInstance().createShift(shiftType, selectedManagerSn, "", date), sc);
        } else {
            String chosenWorkersSn = sc.nextLine();
            chosenWorkersSn = sc.nextLine();
            chosenWorkersSn = chosenWorkersSn.replaceAll(" ", "");
            checkResponse(SystemInterfaceWorkers.getInstance().createShift(shiftType, selectedManagerSn, chosenWorkersSn, date), sc);
        }
    }

    public static void displayShifts(Scanner sc) {
        checkResponse(SystemInterfaceWorkers.getInstance().printAllShifts(), sc);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int shiftSn = sc.nextInt();
        if (shiftSn == 0) {
            workingLoop(sc);
        }
        checkResponse(SystemInterfaceWorkers.getInstance().printShift(shiftSn), sc);
    }

    public static void addWorker(Scanner sc) {
        System.out.println("Enter worker Id:");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int workerId = sc.nextInt();
        System.out.println("Enter worker name:");
        String workerName = sc.nextLine();
        workerName = sc.nextLine();
        System.out.println("Enter phone:");
        while (!sc.hasNext()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String workerPhoneNumber = sc.nextLine();
        System.out.println("Enter bank account:");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int workerBankAccount = sc.nextInt();
        System.out.println("Enter salary:");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int workerSalary = sc.nextInt();
        System.out.println("Enter starting date - dd-mm-yyyy");
        while (!sc.hasNext()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String dateOfStart = sc.next();
        System.out.println("Enter job title:");
        while (!sc.hasNext()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String workerJobTitle = sc.next();
        String licenses = "";
        if (workerJobTitle.toUpperCase().equals("DRIVER")) {
            System.out.println("Enter driver's license (C/C1)");
            licenses = sc.nextLine();
        }
        System.out.println("Enter constrains: - Day-ShiftType , Day-ShiftType, etc...");
        while (!sc.hasNext()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        String constrains = sc.nextLine();
        constrains = sc.nextLine();
        constrains = constrains.replaceAll(" ", "");
        if (workerJobTitle.toUpperCase().equals("DRIVER")) {
            checkResponse(SystemInterfaceWorkers.getInstance().addDriver(workerId, workerName, workerPhoneNumber, workerBankAccount, workerSalary, dateOfStart, workerJobTitle, constrains, licenses), sc);
        } else {
            checkResponse(SystemInterfaceWorkers.getInstance().addWorker(workerId, workerName, workerPhoneNumber, workerBankAccount, workerSalary, dateOfStart, workerJobTitle, constrains), sc);
        }
    }

    public static void displayWorkers(Scanner sc) {
        checkResponse(SystemInterfaceWorkers.getInstance().printAllWorkers(), sc);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int workerSn = sc.nextInt();
        if (workerSn == 0) {
            workingLoop(sc);
        }
        checkResponse(SystemInterfaceWorkers.getInstance().printWorkerBySn(workerSn), sc);
        System.out.println("Choose action: ");
        System.out.println("1. Edit worker constrains");
        System.out.println("2. Edit worker salary");
        System.out.println("3. Fire worker");
        System.out.println("Enter 0 to go back to main menu");
        while (!sc.hasNextInt()) {
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
                checkResponse(SystemInterfaceWorkers.getInstance().removeWorkerBySn(workerSn), sc);
                checkResponse(SystemInterfaceWorkers.getInstance().removeLaterShiftForFiredManagerByManagerSn(workerSn), sc);
                break;
            case 0: // quit
                workingLoop(sc);
                break;
            default:
                System.out.println("Invalid input, going back to display workers");
                displayWorkers(sc);
        }
    }

    private static void EditWorkerSalary(Scanner sc, int workerSn) {
        System.out.println("Enter new salary");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please try again");
            sc.next();
        }
        int newSalary = sc.nextInt();
        checkResponse(SystemInterfaceWorkers.getInstance().setNewSalaryBySn(workerSn, newSalary), sc);
    }

    private static void EditWorkerConstrains(Scanner sc, int workerSn) {
        checkResponse(SystemInterfaceWorkers.getInstance().resetWorkerConstrainsBySn(workerSn), sc);
        System.out.println("Enter constrains: - Day-ShiftType , Day-ShiftType, etc...");
        String constrains = sc.nextLine();
        constrains = sc.nextLine();
        constrains = constrains.replaceAll("\\s+", "");
        checkResponse(SystemInterfaceWorkers.getInstance().editWorkerConstrainsBySn(workerSn, constrains), sc);
        checkResponse(SystemInterfaceWorkers.getInstance().printWorkerConstrainsBySn(workerSn), sc);
    }

    public static void initSuperLeeWithWorkers() {
        SystemInterfaceWorkers.getInstance().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier", "");
        SystemInterfaceWorkers.getInstance().addWorker(101, "Hadar Kor", "101", 124, 2500, "15-04-2020", "Manager", "");
        SystemInterfaceWorkers.getInstance().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper", "");
        SystemInterfaceWorkers.getInstance().addWorker(103, "Amit Rubin", "102", 125, 10000, "15-04-2020", "Storekeeper", "");
        SystemInterfaceWorkers.getInstance().addWorker(104, "Reut Levy", "102", 125, 10000, "15-04-2020", "Storekeeper", "");
        SystemInterfaceWorkers.getInstance().addWorker(105, "Hadas Zaira", "102", 125, 10000, "15-04-2020", "Storekeeper", "");
        SystemInterfaceWorkers.getInstance().addWorker(106, "Roi Benhus", "102", 125, 10000, "15-04-2020", "Storekeeper", "");
        SystemInterfaceWorkers.getInstance().createShift("MORNING", 2, "1,3", "19-12-2020");
        SystemInterfaceWorkers.getInstance().createShift("NIGHT", 2, "1,3,4", "19-12-2020");
        SystemInterfaceWorkers.getInstance().createShift("MORNING", 2, "1,3,5", "20-12-2020");
        SystemInterfaceWorkers.getInstance().createShift("NIGHT", 2, "1,3,6,7", "20-12-2020");
        SystemInterfaceWorkers.getInstance().createShift("MORNING", 2, "1,3,4,5,6,7", "21-12-2020");
        SystemInterfaceWorkers.getInstance().createShift("NIGHT", 2, "1,3,5", "21-12-2020");
        SystemInterfaceWorkers.getInstance().createShift("MORNING", 2, "1,6,7", "22-12-2020");
        SystemInterfaceWorkers.getInstance().createShift("NIGHT", 2, "1,4,5", "22-12-2020");
    }

    private static void checkResponse(InfoObject infoObject, Scanner sc) {
        if (!infoObject.isSucceeded()) {
            System.out.println(infoObject.getMessage());
            workingLoop(sc);
        } else {
            if (!infoObject.getMessage().equals("")) {
                System.out.println(infoObject.getMessage());
            }
        }
    }
}
