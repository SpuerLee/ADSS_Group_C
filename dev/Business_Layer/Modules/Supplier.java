package Business_Layer.Modules;

public class Supplier extends Site {


    public Supplier(String name,String phone, String contact_name, Address address, Area area){
        super(name,phone,contact_name, address, area);
    }

    public Supplier(int id, String name,String phone, String contact_name, Address address, Area area){
        super(id, name,phone,contact_name, address, area);
    }

}
