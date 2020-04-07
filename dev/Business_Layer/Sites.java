package Business_Layer;

import java.io.FileWriter;
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
    private type site_type;
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
    private area site_area;

    public Sites(int id,type site_type, String name, String city, String street, int number, String name_of_contact, String phone, area site_area)
    {
        this.id=id;
        this.site_type=site_type;
        this.name=name;
        this.city=city;
        this.street=street;
        this.number=number;
        this.name_of_contact=name_of_contact;
        this.phone=phone;
        this.site_area=site_area;
    }

    private void create_Site(int id, type site_type, String name, String city, String street, int number, String name_of_contact, String phone, area site_area)
    {
        HashSites.put(id,new Sites(id,site_type,name,city,street,number,name_of_contact,phone,site_area));
    }

    private void delete_Driver(int id, String name, List<License> licenses)
    {
        HashSites.remove(id);
    }
}
