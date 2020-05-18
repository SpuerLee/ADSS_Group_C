package Data_Layer.Dummy_objects;
import Data_Layer.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class dummy_Address {

    private String city;
    private String street;
    private int number;

    public int getSN() {
        return SN;
    }

    private int SN;

    public dummy_Address(String city, String street, int number, int SN) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.SN = SN;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
