import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Shift {
    private HR.ShiftType shiftType;
    private Worker manager;
    private List<Worker> shiftWorkers;
    private Date date;
    private int sn;

    public Shift(Date date, HR.ShiftType shiftType, Worker manager, List<Worker> shiftWorkers,int sn) {
        this.date = date;
        this.shiftType = shiftType;
        this.manager = manager;
        this.shiftWorkers = shiftWorkers;
        this.sn = sn;
    }

    public void printShift() {

        System.out.println("Shift SN. " + this.sn);
        System.out.println("    Manager SN: " + this.manager.getName() + " Manager Name: " + this.manager.getName());
        System.out.println("    Date: " + this.date);
        System.out.println("    Type: " + this.shiftType);
    }

    public int getSn() { return this.sn; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HR.ShiftType getStype() {
        return shiftType;
    }

    public void setStype(HR.ShiftType stype) {
        this.shiftType = stype;
    }

    public Worker getManager() {
        return manager;
    }

    public void setManager(Worker manager) {
        this.manager = manager;
    }

    public List<Worker> getShiftWorker() {
        return shiftWorkers;
    }

    public void setShiftWorker(List<Worker> shiftWorker) {
        this.shiftWorkers = shiftWorker;
    }

}
