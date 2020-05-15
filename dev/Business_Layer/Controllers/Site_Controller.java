package Business_Layer.Controllers;

import Business_Layer.Modules.*;
import Business_Layer.Service;
import Business_Layer.Transportations.Buisness_Exception;
import Business_Layer.Transportations.Modules.*;

import java.util.LinkedList;
import java.util.List;

public class Site_Controller {

    public boolean printAllSites() {
        if(service.getHashStoresMap().isEmpty()){
            return false;
        }
        for(Store storeToPrint : service.getHashStoresMap().values()){
            System.out.println(storeToPrint);
        }
        return true;
    }

    public boolean isStoreExcites(int currentStore) {
        for(int storeSN : service.getHashStoresMap().keySet()){
            if(currentStore == storeSN){
                return true;
            }
        }
        return false;
    }

    private static class SingletonService {
        private static Site_Controller instance = new Site_Controller();
    }

    private Site_Controller() {
        // initialization code..
    }

    Service service = Service.getInstance();

    public static Site_Controller getInstance() {
        return SingletonService.instance;
    }


    public boolean addsite(String site_type, String name, String city,
                           String street, String number, String name_of_contact,
                           String phone, String site_area) {
        if (site_type.equals("store")) {
            addStore(name,city,street,number,name_of_contact,phone,site_area);
            return true;
        } else if (site_type.equals("supplier")) {
            addSupplier(name,city,street,number,name_of_contact,phone,site_area);
            return true;
        } else {
            return false;
        }
    }

    public void addStore(String name, String city, String street, String number, String name_of_contact, String phone, String site_area){
        Store store = new Store(name, phone, name_of_contact, new Address(city, street, Integer.parseInt(number)), new Area(site_area));
        service.getHashStoresMap().put(store.getId(), store);
    }

    public void addSupplier(String name, String city, String street, String number, String name_of_contact, String phone, String site_area){
        Supplier supplier = new Supplier(name, phone, name_of_contact, new Address(city, street, Integer.parseInt(number)), new Area(site_area));
        service.getSuppliersMap().put(supplier.getId(), supplier);
    }

    public String showsite() throws Buisness_Exception {
        System.out.println("");
        if (service.getHashStoresMap().size() + service.getSuppliersMap().size() == 0)
            throw new Buisness_Exception("There are no sites in the system" + "\n");
        else {
            String result = "";
            for (Store sites : service.getHashStoresMap().values())
                result = result +sites.getId()+ " .Name :" + sites.getName() + " ,Type: Store" + "\n";
            for (Supplier sites : service.getSuppliersMap().values())
                result = result +sites.getId()+ " .Name :" + sites.getName() + " ,Type: Supplier" + "\n";
            return result;
        }
    }

    public String getSuppliersbyarea(String area) {
        List<Site> sites = new LinkedList<Site>();
        String output = "";
        for (Site site : service.getSuppliersMap().values()) {
            if ((!sites.contains(site) & (site.getArea().toString().equals(area)))) {
                output = output + site.getId() + "." + "Name: " + site.getName() + "\n";
                sites.add(site);
            }
        }
        return output;
    }

    //print store and id
    public String get_Stores_By_specific_area(String area) {
        String output = "";
        for (Store sites : service.getHashStoresMap().values()) {
            if (sites.getArea().toString().equals(area)) {
                output = output + sites.getId() + ". " + sites.getName() + ", ";
            }
        }
        return output;
    }

    public String getSupplierByStoreArea(String id, String area) {
        int storeId = Integer.parseInt(id);
        String output = "";
        for (MissingItems missingItems : service.getMissing().values()) {
            int supplierId = missingItems.getSupplierId();
            Area supplierArea = service.getSuppliersMap().get(supplierId).getArea();
            if (storeId == missingItems.getStoreId() && area.equals(supplierArea.toString())) {
                output = output + supplierId + ". " + service.getSuppliersMap().get(supplierId).getName() + "\n";
            }
        }
        return output;
    }


    public String getSupplierAreaByStore(String id) {
        int storeId = Integer.parseInt(id);
        List<Area> area_list = new LinkedList<Area>();
        String output = "";
        for (MissingItems missingItems : service.getMissing().values()) {
            if (storeId == missingItems.getStoreId()) {
                int supplierId = missingItems.getSupplierId();
                Area area = service.getSuppliersMap().get(supplierId).getArea();
                if (!area_list.contains(area)) {
                    area_list.add(area);
                    output = output + area.toString() + ", ";
                }
            }
        }
        output = output.substring(0, output.length() - 2);
        return output;
    }

    public String get_Store_id(String site) {
        String output = "";
        for (Site sites : service.getHashStoresMap().values()) {
            if (sites.getName().equals(site)) {
                output = Integer.toString(sites.getId());
            }
        }
        return output;
    }


}
