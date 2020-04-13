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
        return Site_Service.getInstance();
    }


    public boolean addsite(String site_type, String name, String city, String street, String number, String name_of_contact, String phone, String site_area){
        boolean result=true;
        for(Site sites:service.getSitesMap().values()){
            if(sites.getName().equals(name)){
                result=false;
            }
        }
        if(result){
            if(site_type.equals("store")){
                Site store=new Store(name,phone,name_of_contact, new Address(city,street,Integer.parseInt(number)), new Area(site_area));
                service.getSitesMap().put(name,store);

            }
            else if(site_type.equals("supplier")){
                Site supplier=new Supplier(name,phone,name_of_contact, new Address(city,street,Integer.parseInt(number)), new Area(site_area));
                service.getSitesMap().put(name,supplier);

            }
        }
        return result;
    }

    public String showsite(){
        String result="";
        for(Site sites: service.getSitesMap().values()){
            if(sites instanceof Store)
                result=result+"Name :"+sites.getName()+" ,Type : Store"+"\n";
            else
                result=result+"Name :"+sites.getName()+" ,Type : Supplier"+"\n";
        }
        return result;
    }

    public boolean removeSite(String name){
        boolean result=false;
        for(Site sites:service.getSitesMap().values()){
            if ((sites.getName().equals(name))){
                result=true;
                for(Transportation transportation:service.getHashTransportation().values()){
                    if ((transportation.getStores().contains((sites)))||(transportation.getSuppliers().contains(sites))){
                        result=false;
                    }
                }
                if(result){
                    service.getSitesMap().remove(sites.getName());
                }
            }
        }
        return result;
    }

    public String getSuppliers()
    {
        List<Site> sites=new LinkedList<>();
        String output = "";
        for (Site site: service.getSitesMap().values())
        {
            if((site instanceof Supplier & (!sites.contains(site))))
            {
                output = output +"name: "+site.getName();
                sites.add(site);
            }
        }
        return output;
    }

    //print store and id
    public String get_Stores_By_specific_area(String area){
        String output = "";
        for (Site sites: service.getSitesMap().values())
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
        for (MissingItems missingItems: service.getMissing())
        {
            int supplierId = missingItems.getSupplierId();
            Area supplierArea = service.getSitesMap().get(supplierId).getArea();
            if(storeId==missingItems.getStoreId()&&area.equals(supplierArea.toString()))
            {
                output = output + supplierId+". "+ service.getSitesMap().get(supplierId).getName()+"\n";
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
            for (Site sites : service.getSitesMap().values()) {
                if (sites instanceof Store & (sites.getArea().equals(area))) {
                    output = output + sites.getName() + " ,";
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
        for (MissingItems missingItems: service.getMissing())
        {
            if(storeId==missingItems.getStoreId())
            {
                int supplierId = missingItems.getSupplierId();
                Area area = service.getSitesMap().get(supplierId).getArea();
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



}
