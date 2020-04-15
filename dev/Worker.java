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
    private HashMap<Pair<HR.Day,HR.ShiftType>,Boolean> constrains;

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
            Iterator<Pair<HR.Day,HR.ShiftType>> it = this.constrains.keySet().iterator();
            Pair<HR.Day,HR.ShiftType> pair = it.next();
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public HashMap<Pair<HR.Day, HR.ShiftType>,Boolean> getConstrains() {
        return constrains;
    }

    public void addConstrains(HR.Day _day,HR.ShiftType _shiftType){
        this.constrains.put( new Pair<>(_day,_shiftType),false);
    }

    public void setConstrains() {
        System.out.println("All constrains has been removed");
        this.constrains = new HashMap<>();
    }

    public void printWorker(){
        System.out.println(this.sn + ". ID: " + this.id + " Name: " + this.name+" Job Title: "+this.jobTitle);
    }

}
