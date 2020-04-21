package Workers.BusinessLayer.Modules;

import Workers.BusinessLayer.Modules.Worker;
import Workers.BusinessLayer.Utils.enums;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Shift {
    private enums shiftType;
    private Worker manager;
    private List<Worker> shiftWorkers;
    private Date date;
    private int sn;

    public Shift(Date date, enums shiftType, Worker manager, List<Worker> shiftWorkers, int sn) {
        this.date = date;
        this.shiftType = shiftType;
        this.manager = manager;
        this.shiftWorkers = shiftWorkers;
        this.sn = sn;
    }

    public void printShift() {
        SimpleDateFormat daty = new SimpleDateFormat("dd/MM/yyyy");
        String dat = daty.format(date);
        System.out.println("Shift SN. " + this.sn);
        System.out.println("Manager SN: " + this.manager.getWorkerSn() + " Manager Name: " + this.manager.getWorkerName());
        System.out.println("Date: " + dat);
        System.out.println("Type: " + this.shiftType);
    }

    public int getShiftSn() { return this.sn; }

    public Date getDate() {
        return date;
    }

    public enums getShiftType() {
        return shiftType;
    }

    public List<Worker> getShiftWorkers() {
        return shiftWorkers;
    }

    public Worker getManager() {
        return manager;
    }
}
