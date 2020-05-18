package Data_Layer.Dummy_objects;
public class dummy_Address {

    private int SN;
    private String city;
    private String street;
    private int number;

    public dummy_Address(int sn,String city, String street, int number) {
        this.SN=sn;
        this.city = city;
        this.street = street;
        this.number = number;
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

    public int getSN(){ return this.SN;}

}
