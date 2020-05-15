package Test.Workers;

import Business_Layer.Service;
import Business_Layer.Workers.Controllers.ShiftController;
import Business_Layer.Workers.Utils.enums;
import Business_Layer.Workers.Controllers.WorkerController;
import javafx.util.Pair;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class projectTests {

    private static Service service;


    public projectTests() {
    }

    @BeforeEach
    void init(TestInfo testInfo) {
        service = null;
        service = Service.getInstance();
        service.getWorkerList().clear();
        service.getWorkerController().resetSnFactory();
        service.getShiftController().resetSnFactory();
        service.getShiftHistory().clear();
        service.getShiftController().setCurrentStoreSN(1);
        service.getWorkerController().setCurrentStoreSN(1);
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
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        assertEquals(1, service.getWorkerList(1).size());
    }

    @Test
    @DisplayName("Add new Shift with date that already passed")
    @Order(2)
    void test2() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().addWorker(101, "Hadar Korny", "101", 124, 2500, "15-04-2020", "Manager","");
        service.getWorkerController().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper","");
        service.getShiftController().createShift("MORNING", 2, "1,3", "19-04-2020");
        assertEquals(0, service.getShiftHistory(1).size());
    }

    @Test
    @DisplayName("Create Shift without manager")
    @Order(3)
    void test3() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper","");
        service.getShiftController().createShift("NIGHT", 1, "1,3", "19-12-2020");
        assertEquals(0, service.getShiftHistory(1).values().size());
    }

    @Test
    @DisplayName("Add worker to a shift that there is a conflict with his constrains")
    @Order(4)
    void test4() {
        service.getWorkerController().addWorker(101, "Hadar Korny", "101", 124, 2500, "10-04-2020", "Manager","SATURDAY-MORNING");
        service.getShiftController().createShift("MORNING", 1, "", "26-12-2020");
        assertEquals(0, service.getShiftHistory(1).size());
    }

    @Test
    @DisplayName("Remove worker")
    @Order(5)
    void test5() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().removeWorkerBySn(1);
        assertEquals(0, service.getWorkerList().size());
    }

    @Test
    @DisplayName("Add worker Id to a shift that the worker id doesnt exists")
    @Order(6)
    void test6() {
        service.getShiftController().createShift("NIGHT", 2, "", "25-12-2020");
        assertEquals(0, service.getShiftHistory().size());
    }

    @Test
    @DisplayName("Edit worker salary")
    @Order(7)
    void test7() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().setNewSalaryBySn(1, 200);
        assertEquals(200, service.getWorkerList(1).get(1).getWorkerSalary());
    }

    @Test
    @DisplayName("Edit constrains")
    @Order(8)
    void test8() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","SUNDAY-MORNING");
        service.getWorkerController().resetWorkerConstrainsBySn(1);
        service.getWorkerController().editWorkerConstrainsBySn(1,"MONDAY-MORNING");
        HashMap<Pair<enums, enums>, Boolean> constrains = new HashMap<>();
        constrains.put(new Pair<>(enums.MONDAY, enums.MORNING), false);
        assertEquals(constrains, service.getWorkerList(1).get(1).getWorkerConstrains());
    }

    @Test
    @DisplayName("Create shift with invalid manager id")
    @Order(9)
    void test9() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().addWorker(101, "Hadar Korny", "101", 124, 2500, "15-04-2020", "Manager","");
        service.getWorkerController().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper","");
        service.getShiftController().createShift("MORNING", 4, "1,3", "19-12-2020");
        assertEquals(0, service.getShiftHistory(1).size());
    }

    @Test
    @DisplayName("Create shift with date and shift type that already exists")
    @Order(10)
    void test10() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().addWorker(101, "Hadar Korny", "101", 124, 2500, "15-04-2020", "Manager","");
        service.getWorkerController().addWorker(102, "Tomer Hacham", "102", 125, 10000, "15-04-2020", "Storekeeper","");
        service.getShiftController().createShift("MORNING", 2, "1,3", "26-12-2020");
        service.getShiftController().createShift("MORNING", 2, "1,3", "26-12-2020");
        assertEquals(1, service.getShiftHistory(1).size());
    }

    @Test
    @DisplayName("Add worker to a shift before he starts to work")
    @Order(11)
    void test11() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().addWorker(101, "Hadar Korny", "100", 124, 2500, "15-04-2020", "Manager","");
        service.getWorkerController().addWorker(102, "Tomer Hacham", "100", 125, 10000, "15-04-2020", "Storekeeper","");
        service.getShiftController().createShift("MORNING", 4, "1,3", "26-12-2020");
        assertEquals(0, service.getShiftHistory(1).size());
    }

    @Test
    @DisplayName("Create shift with date that already passed")
    @Order(12)
    void test12() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().addWorker(101, "Hadar Korny", "100", 124, 2500, "15-04-2020", "Manager","");
        service.getWorkerController().addWorker(102, "Tomer Hacham", "100", 125, 10000, "15-04-2020", "Storekeeper","");
        service.getShiftController().createShift("MORNING", 4, "1,3", "26-03-2020");
        assertEquals(0, service.getShiftHistory(1).size());
    }

    @Test
    @DisplayName("Add new Shift")
    @Order(13)
    void test13() {
        service.getWorkerController().addWorker(100, "Andrey Palman", "100", 123, 100, "15-04-2020", "Cashier","");
        service.getWorkerController().addWorker(101, "Hadar Korny", "100", 124, 2500, "15-04-2020", "Manager","");
        service.getWorkerController().addWorker(102, "Tomer Hacham", "100", 125, 10000, "15-04-2020", "Storekeeper","");
        service.getShiftController().createShift("MORNING", 2, "1,3", "26-12-2020");
        assertEquals(1, service.getShiftHistory(1).values().size());
    }
}
