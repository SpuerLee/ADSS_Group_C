package Business_Layer.Workers.Modules.Worker;

import java.util.Date;

public class Driver extends Worker{

    private String licenseType;

    public Driver(int id, String name, String phoneNumber, int bankAccount, int salary, Date date, int Sn) {
        super(id, name, phoneNumber, bankAccount, salary, date, "Driver", Sn);

    }

}
