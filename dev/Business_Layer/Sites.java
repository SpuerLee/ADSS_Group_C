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

    private area site_area;

    public Sites(int id, String site_type, String name, String city, String street, int number, String name_of_contact, String phone, String site_area) {
        this.id = id;
        switch (site_type){
            case "store":
                this.site_type = type.store;
            case "supplier":
                this.site_type = type.supplier;
        }
        this.name = name;
        this.city = city;
        this.street = street;
        this.number = number;
        this.name_of_contact = name_of_contact;
        this.phone = phone;
        switch (site_area){
            case "A":
                this.site_area = area.A;
            case "B":
                this.site_area = area.B;
            case "C":
                this.site_area = area.C;
            case "D":
                this.site_area = area.D;
        }
    }
}


