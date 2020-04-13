package Business_Layer;

import java.util.concurrent.ConcurrentHashMap;

public abstract class Site {

    private String name;
    private String phone;
    private String contact_name;
    private Address address;
    private Area area;
    Site (String name, String phone, String contact_name, Address address,Area area){
        this.name=name;
        this.contact_name=contact_name;
        this.phone=phone;
        this.address=address;
        this.area=area;
    }


    public String getPhone(){ return phone;}
    public String getContact_name(){return  contact_name;}

    private ConcurrentHashMap<Integer,Site> HashSites= new ConcurrentHashMap<>();


    public Address getAddress() {
        return address;
    }

    public Area getArea() {
        return area;
    }

    public String getName() {
        return name;
    }
}
