package Business_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class Sites {

    public static HashMap<Integer,Sites> HashSites= new HashMap<>();

    private int id;
    private String name;
    private enum type{
        supplier,
        tore
    };
    private String city;
    private String street;
    private int number;
    private String name_of_contact;
    private String phone;
    private enum area{
        A,
        B,
        C,
        D
    };

}
