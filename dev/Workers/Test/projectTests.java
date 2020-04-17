package Workers.Test;

import Workers.BusinessLayer.ShiftController;
import Workers.BusinessLayer.Utils.Worker;
import Workers.BusinessLayer.WorkerController;
import Workers.InterfaceLayer.SystemInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class projectTests {

    private static ShiftController shiftController;
    private static WorkerController workerController;


    public projectTests() {
    };

    @BeforeAll
    static void initAll() throws ParseException {
        shiftController = new ShiftController();
        workerController = new WorkerController();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-15");
        Worker w1 = new Worker(100,"Andrey Palman",100,123,100,date,"Cashier",1);
        Worker w2 = new Worker(101,"Hadar Kor",101,124,2500,date,"Manager",2);
        Worker w3 = new Worker(102,"Tomer Hacham",102,125,10000,date,"Storekeeper",3);
        workerController.getWorkerList().put(w1.getWorkerSn(),w1);
        workerController.getWorkerList().put(w2.getWorkerSn(),w2);
        workerController.getWorkerList().put(w3.getWorkerSn(),w3);
        shiftController.createShift("MORNING",2,"1,3","18-04-2020");
    }

    @Test
    @DisplayName("Add new Worker")
    void test1() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-15");
        Worker w4 = new Worker(105,"Andrey Palman2",100,123,100,date,"Cashier",4);
        workerController.getWorkerList().put(4,w4);
        assertEquals(4,workerController.getWorkerList().size());
    }

    @Test
    @DisplayName("Add new Shift")
    void test2() throws ParseException {
        shiftController.createShift("MORNING",2,"1,3","19-04-2020");
        assertEquals(2,shiftController.getShiftHistory().values().size());
    }

    @Test
    @DisplayName("Print worker by Sn")
    void test3(){
        assertEquals("sn. 1\n" +
                "id: 100\n" +
                "name: 'Andrey Palman'\n" +
                "phoneNumber: 100\n" +
                "bankAccount: 123\n" +
                "salary: 100\n" +
                "date: 15/04/2020\n" +
                "jobTitle: 'Cashier'\n" +
                "constrains: No Constrains\n",workerController.getWorkerList().get(1).toString());
    }

    @Test
    @DisplayName("")
    void test4(){

    }

    @Test
    @DisplayName("")
    void test5(){

    }

    @Test
    @DisplayName("")
    void test6(){

    }

    @Test
    @DisplayName("")
    void test7(){

    }

    @Test
    @DisplayName("")
    void test8(){

    }

    @Test
    @DisplayName("")
    void test9(){

    }

    @Test
    @DisplayName("")
    void test10(){

    }

    @Test
    @DisplayName("")
    void test11(){

    }

}
