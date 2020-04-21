package Workers.Test;

import Workers.BusinessLayer.ShiftController;
import Workers.BusinessLayer.Utils.enums;
import Workers.BusinessLayer.WorkerController;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class projectTests {

    private static ShiftController shiftController;
    private static WorkerController workerController;

    public projectTests() {
    }

    @BeforeEach
    void init() {
        shiftController = new ShiftController();
        workerController = new WorkerController();
    }

    @Test
    @DisplayName("Add new Worker")
    void test1() {
        workerController.addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        assertEquals(1, workerController.getWorkerList().size());
    }

    @Test
    @DisplayName("Add new Shift with date that already passed")
    void test2() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", 101, 124, 2500, "2020-04-15", "Manager");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", 102, 125, 10000, "2020-04-15", "Storekeeper");
        shiftController.createShift("MORNING", 2, "1,3", "19-04-2020");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Create Shift without manager")
    void test4() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", 102, 125, 10000, "2020-04-15", "Storekeeper");
        shiftController.createShift("NIGHT", 1, "1,3", "2020-12-19");
        assertEquals(0, shiftController.getShiftHistory().values().size());
    }

    @Test
    @DisplayName("Add worker to a shift that there is a conflict with his constrains")
    void test5() {
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", 101, 124, 2500, "2020-04-10", "Manager");
        shiftController.getWorkerController().addConstrainsToWorkerByWorkerSn(1, "SATURDAY", "MORNING");
        shiftController.createShift("MORNING", 2, "", "2020-12-26");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Remove worker")
    void test6() {
        workerController.addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        workerController.removeWorkerBySn(1);
        assertEquals(0, workerController.getWorkerList().size());
    }

    @Test
    @DisplayName("Add worker Id to a shift that the worker id doesnt exists")
    void test7() {
        shiftController.createShift("NIGHT", 2, "", "2020-12-25");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Edit worker salary")
    void test8() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        shiftController.getWorkerController().setNewSalaryBySn(1, 200);
        assertEquals(200, shiftController.getWorkerController().getWorkerList().get(1).getWorkerSalary());
    }

    @Test
    @DisplayName("Edit constrains")
    void test9() {
        workerController.addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        workerController.addConstrainsToWorkerByWorkerSn(1, "SUNDAY", "MORNING");
        workerController.setWorkerConstrainsBySn(1);
        workerController.addConstrainsToWorkerByWorkerSn(1, "MONDAY", "MORNING");
        HashMap<Pair<enums, enums>, Boolean> constrains = new HashMap<>();
        constrains.put(new Pair<>(enums.MONDAY, enums.MORNING), false);
        assertEquals(constrains, workerController.getWorkerList().get(1).getWorkerConstrains());
    }


    @Test
    @DisplayName("Create shift with invalid manager id")
    void test10() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", 101, 124, 2500, "2020-04-15", "Manager");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", 102, 125, 10000, "2020-04-15", "Storekeeper");
        shiftController.createShift("MORNING", 4, "1,3", "2020-12-19");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Create shift with date and shift type that already exists")
    void test11() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", 101, 124, 2500, "2020-04-15", "Manager");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", 102, 125, 10000, "2020-04-15", "Storekeeper");
        shiftController.createShift("MORNING", 2, "1,3", "2020-12-26");
        shiftController.createShift("MORNING", 2, "1,3", "2020-12-26");
        assertEquals(1, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Add worker to a shift before he starts to work")
    void test12() {

        shiftController.getWorkerController().addWorker(100, "Andrey Palman", 100, 123, 100, "2020-012-15", "Cashier");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", 101, 124, 2500, "2020-04-15", "Manager");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", 102, 125, 10000, "2020-04-15", "Storekeeper");
        shiftController.createShift("MORNING", 4, "1,3", "2020-12-26");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Create shift with date that already passed")
    void test13() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", 101, 124, 2500, "2020-04-15", "Manager");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", 102, 125, 10000, "2020-04-15", "Storekeeper");
        shiftController.createShift("MORNING", 4, "1,3", "2020-03-26");
        assertEquals(0, shiftController.getShiftHistory().size());
    }

    @Test
    @DisplayName("Add new Shift")
    void test14() {
        shiftController.getWorkerController().addWorker(100, "Andrey Palman", 100, 123, 100, "2020-04-15", "Cashier");
        shiftController.getWorkerController().addWorker(101, "Hadar Korny", 101, 124, 2500, "2020-04-15", "Manager");
        shiftController.getWorkerController().addWorker(102, "Tomer Hacham", 102, 125, 10000, "2020-04-15", "Storekeeper");
        shiftController.createShift("MORNING", 2, "1,3", "2020-12-26");
        assertEquals(1, shiftController.getShiftHistory().values().size());
    }
}
