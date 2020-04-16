package Workers.BusinessLayer.Utils;

import Workers.InterfaceLayer.HR;
import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Worker {

    private int sn;
    private int id;
    private String name;
    private int phoneNumber;
    private int bankAccount;
    private int salary;
    private Date date;
    private String jobTitle;
    private HashMap<Pair<enums,enums>,Boolean> constrains;

    @Override
    public String toString() {
        SimpleDateFormat daty = new SimpleDateFormat("dd/MM/yyyy");
        String dat = daty.format(date);
        return "sn. " + sn + "\n" +
                "id: " + id + "\n" +
                "name: '" + name + '\'' + "\n" +
                "phoneNumber: " + phoneNumber + "\n" +
                "bankAccount: " + bankAccount + "\n" +
                "salary: " + salary + "\n" +
                "date: " + dat + "\n" +
                "jobTitle: '" + jobTitle + '\'' + "\n" +
                "constrains: " + printConstrains()  + "\n" ;
    }

    public Worker(int id, String name, int phoneNumber, int bankAccount, int salary, Date date, String jobTitle, int sn) {
        this.sn = sn;
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankAccount = bankAccount;
        this.salary = salary;
        this.date = date;
        this.jobTitle = jobTitle;
        this.constrains = new HashMap<>();
    }

    public String printConstrains(){
        String constrains="";
        if(this.constrains.keySet().isEmpty()){
            constrains = "No Constrains";
        } else {
            Iterator<Pair<enums,enums>> it = this.constrains.keySet().iterator();
            Pair<enums,enums> pair = it.next();
            constrains = constrains + pair.getKey() +" "+ pair.getValue();
            while (it.hasNext()) {
                pair = it.next();
                constrains =  pair.getKey() +" "+ pair.getValue() +", "+ constrains ;
            }
        }
        return  constrains;
    }

    public int getSn() { return this.sn; }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public HashMap<Pair<enums, enums>,Boolean> getConstrains() {
        return constrains;
    }

    public void addConstrains(enums day,enums shiftType){
        this.constrains.put( new Pair<>(day,shiftType),false);
    }

    public void setConstrains() {
        System.out.println("All constrains has been removed");
        this.constrains = new HashMap<>();
    }

    public void printWorker(){
        System.out.println(this.sn + ". ID: " + this.id + " Name: " + this.name+" Job Title: "+this.jobTitle);
    }

}
