package Workers.Test;

import Workers.BusinessLayer.ShiftController;
import Workers.BusinessLayer.Utils.enums;
import Workers.BusinessLayer.WorkerController;
import javafx.util.Pair;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class projectTests {

    private static ShiftController shiftController;
    private static WorkerController workerController;


    public projectTests() {
    }

    @BeforeEach
    void init(TestInfo testInfo) {
        workerController = new WorkerController();
        shiftController = new ShiftController(workerController);
        System.out.println(testInfo.getTestMethod().get().getName() + " - " + testInfo.getDisplayName());
    }

    @AfterEach
    void afterEachTest(TestInfo testInfo){
        String result = testInfo.getTestMethod().isPresent() ? "Pass" : "Failed";
        if(result.equals("Pass")) {
            System.out.println(testInfo.getTestMethod().get().getName() + " - "  + result);
        } else {
            System.out.println(testInfo.getTestMethod().get().getName() + " - "  + result);
        }
    }

    @Test
    @DisplayName("Add new Worker")
    @Order(1)
    void test1() {
        workerController.addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        assertEquals(1, workerController.getWorkerList().size());
    }

    @Test
    @DisplayName("Add new Shift with date that already passed")
    @Order(2)
    void test2() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", "101", 124, 2500, "15-04-2020", "Manager","");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper","");
        shiftController.createShift("MORNING", 2, "1,3", "19-04-2020");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Create Shift without manager")
    @Order(3)
    void test3() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper","");
        shiftController.createShift("NIGHT", 1, "1,3", "19-12-2020");
        assertEquals(0, shiftController.getShiftHistory().values().size());
    }

    @Test
    @DisplayName("Add worker to a shift that there is a conflict with his constrains")
    @Order(4)
    void test4() {
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", "101", 124, 2500, "10-04-2020", "Manager","SATURDAY-MORNING");
        shiftController.createShift("MORNING", 2, "", "26-12-2020");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Remove worker")
    @Order(5)
    void test5() {
        workerController.addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        workerController.removeWorkerBySn(1);
        assertEquals(0, workerController.getWorkerList().size());
    }

    @Test
    @DisplayName("Add worker Id to a shift that the worker id doesnt exists")
    @Order(6)
    void test6() {
        shiftController.createShift("NIGHT", 2, "", "25-12-2020");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Edit worker salary")
    @Order(7)
    void test7() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        shiftController.getWorkerController().setNewSalaryBySn(1, 200);
        assertEquals(200, shiftController.getWorkerController().getWorkerList().get(1).getWorkerSalary());
    }

    @Test
    @DisplayName("Edit constrains")
    @Order(8)
    void test8() {
        workerController.addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","SUNDAY-MORNING");
        workerController.resetWorkerConstrainsBySn(1);
        workerController.editWorkerConstrainsBySn(1,"MONDAY-MORNING");
        HashMap<Pair<enums, enums>, Boolean> constrains = new HashMap<>();
        constrains.put(new Pair<>(enums.MONDAY, enums.MORNING), false);
        assertEquals(constrains, workerController.getWorkerList().get(1).getWorkerConstrains());
    }

    @Test
    @DisplayName("Create shift with invalid manager id")
    @Order(9)
    void test9() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", "101", 124, 2500, "15-04-2020", "Manager","");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper","");
        shiftController.createShift("MORNING", 4, "1,3", "19-12-2020");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Create shift with date and shift type that already exists")
    @Order(10)
    void test10() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", "101", 124, 2500, "15-04-2020", "Manager","");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper","");
        shiftController.createShift("MORNING", 2, "1,3", "26-12-2020");
        shiftController.createShift("MORNING", 2, "1,3", "26-12-2020");
        assertEquals(1, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Add worker to a shift before he starts to work")
    @Order(11)
    void test11() {

        shiftController.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", "100", 124, 2500, "15-04-2020", "Manager","");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", "100", 125, 10000, "15-04-2020", "Storekeeper","");
        shiftController.createShift("MORNING", 4, "1,3", "26-12-2020");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Create shift with date that already passed")
    @Order(12)
    void test12() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", "100", 124, 2500, "15-04-2020", "Manager","");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", "100", 125, 10000, "15-04-2020", "Storekeeper","");
        shiftController.createShift("MORNING", 4, "1,3", "26-03-2020");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Add new Shift")
    @Order(13)
    void test13() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", "100", 124, 2500, "15-04-2020", "Manager","");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", "100", 125, 10000, "15-04-2020", "Storekeeper","");
        shiftController.createShift("MORNING", 2, "1,3", "26-12-2020");
        assertEquals(1, shiftController.getShiftHistory().values().size());
    }
}
