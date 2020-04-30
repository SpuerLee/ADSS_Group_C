package Transportation.Business_Layer.Services;

import Transportation.Business_Layer.*;

import java.util.LinkedList;
import java.util.List;

public class Site_Service {

    private static class SingletonService {
        private static Site_Service instance = new Site_Service();
    }

    private Site_Service() {
        // initialization code..
    }

    Service service = Service.getInstance();

    public static Site_Service getInstance() {
        return SingletonService.instance;
    }


    public boolean addsite(String site_type, String name, String city,
                           String street, String number, String name_of_contact,
                           String phone, String site_area) {
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

    public String showsite() throws Buisness_Exception {
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
