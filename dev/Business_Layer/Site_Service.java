package Business_Layer;

import java.util.LinkedList;
import java.util.List;

public class Site_Service {

    private static class SingletonService {
        private static Site_Service instance = new Site_Service();
    }
    private Site_Service() {
        // initialization code..
    }

    Service service=Service.getInstance();

    public static Site_Service getInstance() {
        return SingletonService.instance;
    }


    public boolean addsite(String site_type, String name, String city, String street, String number, String name_of_contact, String phone, String site_area){
        boolean result=false;
        for(Site sites:service.getSuppliersMap().values()){
            if(sites.getName().equals(name)){
                result=true;
            }
        }
        for(Site sites:service.getHashStoresMap().values()){
            if(sites.getName().equals(name)){
                result=true;
            }
        }
        if(result){
            if(site_type.equals("store")){
                Store store=new Store(name,phone,name_of_contact, new Address(city,street,Integer.parseInt(number)), new Area(site_area));
                service.getHashStoresMap().put(store.getId(),store);

            }
            else if(site_type.equals("supplier")){
                Supplier supplier=new Supplier(name,phone,name_of_contact, new Address(city,street,Integer.parseInt(number)), new Area(site_area));
                service.getSuppliersMap().put(supplier.getId(),supplier);

            }
        }
        return result;
    }

    public String showsite(){
        String result="";
        for(Store sites: service.getHashStoresMap().values())
                result=result+"Name :"+sites.getName()+" ,Type : Store"+"\n";
        for(Supplier sites: service.getSuppliersMap().values())
                result=result+"Name :"+sites.getName()+" ,Type : Supplier"+"\n";
        return result;
    }

    public boolean removeSite(String name){
        boolean result=false;
        for(Site sites:service.getSuppliersMap().values()){
            if ((sites.getName().equals(name))){
                result=true;
                for(Transportation transportation:service.getHashTransportation().values()){
                    if ((transportation.getStores().contains((sites)))||(transportation.getSuppliers().contains(sites))){
                        result=false;
                    }
                }
                if(result){
                    service.getSuppliersMap().remove(sites.getName());
                }
            }
        }
        for(Site sites:service.getHashStoresMap().values()){
            if ((sites.getName().equals(name))){
                result=true;
                for(Transportation transportation:service.getHashTransportation().values()){
                    if ((transportation.getStores().contains((sites)))||(transportation.getSuppliers().contains(sites))){
                        result=false;
                    }
                }
                if(result){
                    service.getHashStoresMap().remove(sites.getName());
                }
            }
        }
        return result;
    }

    public String getSuppliers()
    {
        List<Site> sites=new LinkedList<>();
        String output = "";
        for (Site site: service.getSuppliersMap().values())
        {
            if((site instanceof Supplier & (!sites.contains(site))))
            {
                output = output +"area ,"+ site.getArea().toString()+ " name: "+site.getName()+ ", id "+site.getId()+"\n";
                sites.add(site);
            }
        }
        return output;
    }

    public String getSuppliersbyarea(String area)
    {
        List<Site> sites=new LinkedList<>();
        String output = "";
        for (Site site: service.getSuppliersMap().values())
        {
            if((!sites.contains(site)&(site.getArea().toString().equals(area))))
            {
                output = output+"name: "+site.getName()+ ", id "+site.getId()+"\n";
                sites.add(site);
            }
        }
        return output;
    }

    //print store and id
    public String get_Stores_By_specific_area(String area){
        String output = "";
        for (Site sites: service.getHashStoresMap().values())
        {
            if(sites.getArea().equals(area) && sites instanceof Store)
            {
                output = output + sites.getName();
            }
        }
        return output;
    }

    public String getSupplierByStoreArea(String id,String area)
    {
        int storeId = Integer.parseInt(id);
        String output = "";
        for (MissingItems missingItems: service.getMissing().values())
        {
            int supplierId = missingItems.getSupplierId();
            Area supplierArea = service.getSuppliersMap().get(supplierId).getArea();
            if(storeId==missingItems.getStoreId()&&area.equals(supplierArea.toString()))
            {
                output = output + supplierId+". "+ service.getSuppliersMap().get(supplierId).getName()+"\n";
            }
        }
        return output;
    }

    //print the area and the stores that are in the area
    public String getStoresByarea()
    {
        String result="";
        for(Area area: service.getArea_list()) {
            String output = "Area "+area+ ": [ ";
            for (Site sites : service.getHashStoresMap().values()) {
                if ((sites.getArea().toString().equals(area.toString()))) {
                    output = output + "id "+ sites.getId()+", name :"+sites.getName() + " ,";
                }
            }
            output = output +"]\n";
            result=result+output;
        }
        return result;
    }


    public String getSupplierAreaByStore(String id)
    {
        int storeId = Integer.parseInt(id);
        List<Area> area_list = new LinkedList<>();
        String output = "[ ";
        for (MissingItems missingItems: service.getMissing().values())
        {
            if(storeId==missingItems.getStoreId())
            {
                int supplierId = missingItems.getSupplierId();
                Area area = service.getSuppliersMap().get(supplierId).getArea();
                if(!area_list.contains(area))
                {
                    area_list.add(area);
                    output = output +area.toString()+", ";
                }
            }
        }
        output = output +"]";
        return output;
    }

     public String get_Store_id(String site){
        String output = "";
        for (Site sites: service.getHashStoresMap().values())
        {
            if(sites.getName().equals(site)) {
                output = Integer.toString(sites.getId());
            }
        }
        return output;
    }



}
