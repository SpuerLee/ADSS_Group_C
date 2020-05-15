package Business_Layer.Controllers;

import Business_Layer.Modules.*;
import Business_Layer.Service;
import Business_Layer.Transportations.Utils.Buisness_Exception;
import Business_Layer.Transportations.Modules.*;

import java.util.LinkedList;
import java.util.List;

public class Site_Controller {


    private static class SingletonService {
        private static Site_Controller instance = new Site_Controller();
    }

    private Site_Controller() {

    }

    public static Site_Controller getInstance() {
        return SingletonService.instance;
    }

    public boolean addsupplier(String name, String city, String street, String number,
                               String name_of_contact, String phone, String supplier_area) {
        Service service = Service.getInstance();
            Supplier supplier = new Supplier(name, phone, name_of_contact,
                    new Address(city, street, Integer.parseInt(number)), new Area(supplier_area));
            service.getSuppliersMap().put(supplier.getId(), supplier);
            return true;
    }


    public boolean addsite(String site_type, String name, String city,
                           String street, String number, String name_of_contact,
                           String phone, String site_area) {
        Service service = Service.getInstance();
        if (site_type.equals("store")) {
            Store store = new Store(name, phone, name_of_contact, new Address(city,
                    street, Integer.parseInt(number)), new Area(site_area));
            service.getHashStoresMap().put(store.getId(), store);
            return true;

        } else if (site_type.equals("supplier")) {
            Supplier supplier = new Supplier(name, phone, name_of_contact,
                    new Address(city, street, Integer.parseInt(number)), new Area(site_area));
            service.getSuppliersMap().put(supplier.getId(), supplier);
            return true;
        } else {
            return false;
        }
    }

    public List<String> Show_supplier() throws Buisness_Exception {
        Service service = Service.getInstance();
        if (service.getSuppliersMap().size() == 0)
            throw new Buisness_Exception("There are no sites in the system" + "\n");
        else {
            List<String> result = new LinkedList<>();
            for (Supplier supplier : service.getSuppliersMap().values())
            {
                String line = supplier.getId()+ " .Name :" + supplier.getName() + ", Type: Supplier" + ".";
                result.add(line);
            }
            return result;
        }
    }

    public List<String> getSuppliersbyarea(String area) {
        Service service = Service.getInstance();
        List<Site> sites = new LinkedList<Site>();
        List<String> output = new LinkedList<String>();
        for (Site site : service.getSuppliersMap().values()) {
            if ((!sites.contains(site) & (site.getArea().toString().equals(area)))) {
                String line = site.getId() + "." + "Name: " + site.getName() + ".";
                output.add(line);
                sites.add(site);
            }
        }
        output.sort(String.CASE_INSENSITIVE_ORDER);
        return output;
    }

    //print store and id
    public List<String> get_Stores_By_specific_area(String area) {
        Service service = Service.getInstance();
        List<String> output = new LinkedList<String>();
        for (Store sites : service.getHashStoresMap().values()) {
            if (sites.getArea().toString().equals(area)) {
                String line = sites.getId() + ". " + sites.getName() + ", ";
                output.add(line);
            }
        }
        output.sort(String.CASE_INSENSITIVE_ORDER);
        return output;
    }

    public List<String> getSupplierByStoreArea(int storeId, String area) {
        Service service = Service.getInstance();
        List<String> output = new LinkedList<String>();
        for (MissingItems missingItems : service.getMissing().values()) {
            int supplierId = missingItems.getSupplierId();
            Area supplierArea = service.getSuppliersMap().get(supplierId).getArea();
            if (storeId == missingItems.getStoreId() && area.equals(supplierArea.toString())) {
                String line = supplierId + ". " + service.getSuppliersMap().get(supplierId).getName() + ".";
                output.add(line);
            }
        }
        output.sort(String.CASE_INSENSITIVE_ORDER);
        return output;
    }


    public List<String> getSupplierAreaByStore(int storeId) {
        Service service = Service.getInstance();
        List<Area> area_list = new LinkedList<Area>();
        List<String> output = new LinkedList<String>();
        for (MissingItems missingItems : service.getMissing().values()) {
            if (storeId == missingItems.getStoreId()) {
                int supplierId = missingItems.getSupplierId();
                Area area = service.getSuppliersMap().get(supplierId).getArea();
                if (!area_list.contains(area)) {
                    area_list.add(area);
                    output.add(area.toString());
                }
            }
        }

        output.sort(String.CASE_INSENSITIVE_ORDER);
        return output;
    }

    public String get_Store_id(String site) {
        Service service = Service.getInstance();
        String output = "";
        for (Site sites : service.getHashStoresMap().values()) {
            if (sites.getName().equals(site)) {
                output = Integer.toString(sites.getId());
            }
        }
        return output;
    }


}
