package Business_Layer.Modules;

public class Address {

    private static int idcounter=0;
    private int id;
    private String city;
    private String street;
    private int number;

    public Address(int id, String city, String street,int number){
        this.id=id;
        this.city=city;
        this.street=street;
        this.number=number;
        if(id>idcounter)
            idcounter= id+1;
    }

    public Address(String city, String street,int number){
        this.id = idcounter++;
        this.city=city;
        this.street=street;
        this.number=number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static int getIdCounter(){
        return idcounter;
    }
    public static void setIdCounter(int id){
        idcounter = id;
    }


}
