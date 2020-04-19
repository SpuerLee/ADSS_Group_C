package Business_Layer;

public class Store extends Site {

    public Store(int id, String name,String phone, String contact_name, Address address, Area area){
        super(id,name,phone,contact_name, address, area);
    }

    public Store(String name, String phone, String contact_name, Address address, Area area){
        super(name,phone,contact_name,address ,area);
    }
}
