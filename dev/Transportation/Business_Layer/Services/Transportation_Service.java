package Transportation.Business_Layer.Services;

import Transportation.Business_Layer.*;

import java.time.LocalTime;
import java.util.*;

public class Transportation_Service {

    private static class Singelton_Transport {
        private static Transportation_Service instance = new Transportation_Service();
    }

    private Transportation_Service() {
        // initialization code..
    }

    public static Transportation_Service getInstance() {
        return Singelton_Transport.instance;
    }

    List<ItemsFile> current = new LinkedList<ItemsFile>();

    private Service service = Service.getInstance();

    public String RemoveSites(int transportationID,String[] storesToRemove,String[] suppliersToRemove)
    {
        Transportation transportation = service.getHashTransportation().get(transportationID);
        List<Supplier> suppliers = transportation.getSuppliers();
        List<Store> stores= transportation.getStores();
        if(suppliers.size()==suppliersToRemove.length)
        {
            return "cant remove all the suppliers";
        }
        else if(stores.size()==storesToRemove.length)
        {
            return "cant remove all the stores";
        }
        else {
            List<ItemsFile> itemsFiles= transportation.getItemsFiles();
            for (String s:storesToRemove)
            {
                int id = Integer.parseInt(s);
                Store store= service.getHashStoresMap().get(id);
                stores.remove(stores.indexOf(store));
                for (ItemsFile itemsFile:itemsFiles)
                {
                    if(itemsFile.getStore().getId()==id)
                    {
                        itemsFiles.remove(itemsFiles.indexOf(itemsFile));
                    }
                }
            }
            for (String s : suppliersToRemove)
            {
                int id = Integer.parseInt(s);
                Supplier supplier= service.getSuppliersMap().get(id);
                suppliers.remove(suppliers.indexOf(supplier));
                for (ItemsFile itemsFile:itemsFiles)
                {
                    if(itemsFile.getSupplier().getId()==id)
                    {
                        itemsFiles.remove(itemsFiles.indexOf(itemsFile));
                    }
                }
            }
            return "Ok";
        }
    }


    public String store_and_supplier_list(int transportationID)
    {
        String output="";
        Transportation transportation = service.getHashTransportation().get(transportationID);
        output+="Stores:\n";
        for(Store store:transportation.getStores())
        {
            output+=store.getId()+". "+store.getName()+"\n";
        }
        output+="Suppliers:";
        for(Supplier supplier:transportation.getSuppliers())
        {
            output+="\n"+supplier.getId()+". "+supplier.getName();
        }
        return output;
    }


    public Date Free_truck_and_driver(int transportationID) {
        try {
            Transportation transportation = service.getHashTransportation().get(transportationID);
            int driverID = transportation.getDriveId();
            int truckID = transportation.getTruck().getId();
            service.getDrivers().get(driverID).Remove_date(transportationID);
            service.getHashTrucks().get(truckID).Remove_date(transportationID);
            return transportation.getDate();
        }
        catch (Exception e){return null;}
    }

    public Boolean SetTruckWeight(String transportationIdSTR,String truckWeightSTR)
    {
        try {
            int transportationId= Integer.parseInt(transportationIdSTR);
            int truckWeight = Integer.parseInt(truckWeightSTR);
            Transportation transportation = service.getHashTransportation().get(transportationId);
            if(transportation.getTruck().getMax_weight()>truckWeight)
            {
                transportation.setWeight_truck(truckWeight);
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e)
        {
            return null;
        }

    }


    public void Change_truck_and_driver(int transportationID,int driver_id, int truck_id) {
        Transportation transportation = service.getHashTransportation().get(transportationID);
        int oldDriver = transportation.getDriveId();
        int oldTruck = transportation.getTruck().getId();
        try {

            transportation.setTruck(service.getHashTrucks().get(truck_id));
            transportation.setDriver(service.getDrivers().get(driver_id));
            service.getDrivers().get(driver_id).addDate(transportation);
            service.getHashTrucks().get(truck_id).addDate(transportation);
        }
        catch (Exception e)
        {
            transportation.setTruck(service.getHashTrucks().get(oldTruck));
            transportation.setDriver(service.getDrivers().get(oldDriver));
            service.getDrivers().get(transportation.getDriveId()).addDate(transportation);
            service.getHashTrucks().get(transportation.getTruck().getId()).addDate(transportation);
        }

    }
    public boolean Change_Back_truck_and_driver(int transportationID) {
        try {
            Transportation transportation = service.getHashTransportation().get(transportationID);
            service.getDrivers().get(transportation.getDriveId()).addDate(transportation);
            service.getHashTrucks().get(transportation.getTruck().getId()).addDate(transportation);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }


    public boolean createTransportation(Date date, LocalTime leaving_time, int driver_id,
                                        int truck_id, List<Integer> suppliers, List<Integer> stores) {
        List<Supplier> suppliers1 = new LinkedList<Supplier>();
        List<Store> stores1 = new LinkedList<Store>();
        for (Supplier site : service.getSuppliersMap().values()) {
            if (suppliers.contains(site.getId()))
                suppliers1.add(site);
        }

        for (Store site : service.getHashStoresMap().values()) {
            if (stores.contains(site.getId()))
                stores1.add(site);
        }


        Transportation transportation =
                new Transportation(date, leaving_time, service.getDrivers().get(driver_id), service.getHashTrucks().get(truck_id), suppliers1, stores1);
        service.getHashTransportation().put(transportation.getId(), transportation);
        List<Integer> id_to_delete = new LinkedList<Integer>();
        for (MissingItems missingItems : service.getMissing().values()) {
            if (stores.contains(missingItems.getStoreId()) && suppliers.contains(missingItems.getSupplierId())) {
                ItemsFile itemFile = new ItemsFile(missingItems.getItems_list(), service.getHashStoresMap().get(missingItems.getStoreId()), service.getSuppliersMap().get(missingItems.getSupplierId()));
                itemFile.setTransportationID(transportation.getId());
                itemFile.setFrom_missing_items();
                service.getItemsFile().add(itemFile);
                id_to_delete.add(missingItems.getID());
                transportation.addItemFile(itemFile);
            }
        }
        service.getDrivers().get(driver_id).addDate(transportation);
        service.getHashTrucks().get(truck_id).addDate(transportation);
        for (Integer id : id_to_delete) {
            service.getMissing().remove(id);
        }
        return true;
    }

    public void createRegularTransportation(Date date, LocalTime leaving_time, int driver_id,
                                            int truck_license_number, List<Integer> suppliers, List<Integer> stores) {
        List<Supplier> suppliers1 = new LinkedList<Supplier>();
        List<Store> stores1 = new LinkedList<Store>();
        for (Supplier site : service.getSuppliersMap().values()) {
            if (suppliers.contains(site.getId()))
                suppliers1.add(site);
        }

        for (Store site : service.getHashStoresMap().values()) {
            if (stores.contains(site.getId()))
                stores1.add(site);
        }

        Transportation transportation =
                new Transportation(date, leaving_time, service.getDrivers().get(driver_id), service.getHashTrucks().get(truck_license_number), suppliers1, stores1);
        service.getHashTransportation().put(transportation.getId(), transportation);
        for (ItemsFile itemsFile : current) {
            itemsFile.setTransportationID(transportation.getId());
            transportation.addItemFile(itemsFile);
            service.getItemsFile().add(itemsFile);
        }
        this.current = new LinkedList<>();
        service.getDrivers().get(driver_id).addDate(transportation);
        service.getHashTrucks().get(truck_license_number).addDate(transportation);
    }


    public void delete_Transport(String id) throws Buisness_Exception {
        if (service.getHashTransportation().size() == 0) {
            throw new Buisness_Exception("There are no transportations to delete");
        }

        int transport_id = Integer.parseInt(id);

        if (!service.getHashTransportation().containsKey(transport_id)) {
            throw new Buisness_Exception("The transport id doesnt exist");
        } else {
            for (Map.Entry<Integer, Transportation> transport : service.getHashTransportation().entrySet()) {
                if (transport.getValue().getId() == transport_id) {
                    int driver = transport.getValue().getDriveId();
                    int truck = transport.getValue().getId();
                    try {
                        service.getDrivers().get(driver).Remove_date(transport_id);
                    /*for (Map.Entry me : service.getHashTrucks().entrySet()) {
                        System.out.println(me.getKey()+"-"+me.getValue());
                    }*/
                        service.getHashTrucks().get(truck).Remove_date(transport_id);
                    } catch (Exception e) {}
                    service.getHashTransportation().remove(transport.getKey());
                }
            }
        }
    }

    public String getTransport_id() throws Buisness_Exception {
        if (service.getHashTransportation().size() == 0) {
            throw new Buisness_Exception("There are no transportations to delete" + "\n");
        } else {
            String result = "";
            for (Transportation transportation : service.getHashTransportation().values()) {
                result = result + transportation.getId() + " ,";
            }
            return result;
        }
    }

    public void addItemFiletotransport(HashMap<String, Integer> items, int store, int supplier) {
        ItemsFile itemsFile = new ItemsFile(items, service.getHashStoresMap().get(store), service.getSuppliersMap().get(supplier));
        current.add(itemsFile);
    }

    public String Show_transports() {
        String output = "";
        for (Transportation transportation : service.getHashTransportation().values()) {
            output = output + transportation.toString() + '\n';

        }
        return output;
    }

    public String get_area_for_suppliers() throws Buisness_Exception {
        List<String> areas = new LinkedList<String>();
        String output = "";
        if (service.getSuppliersMap().size() == 0) {
            throw new Buisness_Exception("There are no suppliers to make a transport");
        }
        for (Supplier supplier : service.getSuppliersMap().values()) {
            if (!areas.contains(supplier.getArea().toString())) {
                areas.add(supplier.getArea().toString());
            }
        }
        for (String area : areas) {
            output = output + area + " ,";
        }
        output = output.substring(0, output.length() - 2);
        return output;
    }

    public String get_area_for_stores() throws Buisness_Exception {
        List<String> areas = new LinkedList<>();
        String output = "";
        if (service.getHashStoresMap().size() == 0) {
            throw new Buisness_Exception("There are no stores to supply to");
        } else {
            for (Store store : service.getHashStoresMap().values()) {
                if (!areas.contains(store.getArea().toString())) {
                    areas.add(store.getArea().toString());
                }
            }
            for (String area : areas) {
                output = output + area + " ,";
            }
            return output;
        }
    }

}
