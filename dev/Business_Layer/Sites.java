package Business_Layer;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class Sites {

    private int id;
    private String name;

    private enum type {
        supplier,
        store
    }
    private type site_type;
    private String city;
    private String street;
    private int number;
    private String name_of_contact;
    private String phone;

    private enum area {
        A,
        B,
        C,
        D
    }

    ;
    private area site_area;

    public Sites(int id, type site_type, String name, String city, String street, int number, String name_of_contact, String phone, area site_area) {
        this.id = id;
        this.site_type = site_type;
        this.name = name;
        this.city = city;
        this.street = street;
        this.number = number;
        this.name_of_contact = name_of_contact;
        this.phone = phone;
        this.site_area = site_area;
    }
}


