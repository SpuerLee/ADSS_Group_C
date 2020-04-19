package Business_Layer;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    List<ItemsFile> current=new LinkedList<>();

    private Service service=Service.getInstance();


    public boolean createTransportation(Date date, LocalTime leaving_time, int driver_id,
                                        int truck_license_number, List<Integer> suppliers, List<Integer> stores)
    {
        List<Supplier> suppliers1=new LinkedList<>();
        List<Store> stores1=new LinkedList<>();
        for(Supplier site:service.getSuppliersMap().values()){
            if(suppliers.contains(site.getId()))
                suppliers1.add(site);
        }

        for(Store site:service.getHashStoresMap().values()){
            if(suppliers.contains(site.getId()))
                stores1.add(site);
        }


        Transportation transportation =
                new Transportation(date,leaving_time,service.getDrivers().get(driver_id),service.getHashTrucks().get(truck_license_number),suppliers1,stores1);
        service.getHashTransportation().put(transportation.getId(),transportation);
        List<Integer> id_to_delete = new LinkedList<>();
        for (MissingItems missingItems: service.getMissing().values())
        {
            if(stores.contains(missingItems.getStoreId())&&suppliers.contains(missingItems.getSupplierId()))
            {
                ItemsFile itemFile = new ItemsFile(missingItems.getItems_list(),service.getHashStoresMap().get(missingItems.getStoreId()),service.getSuppliersMap().get(missingItems.getSupplierId()));
                service.getItemsFile().add(itemFile);
                id_to_delete.add(missingItems.getID());
                transportation.addItemFile(itemFile);
            }
        }
        service.getDrivers().get(driver_id).addDate(date);
        service.getHashTrucks().get(truck_license_number).addDate(date);
        for (Integer id: id_to_delete)
        {
            service.getMissing().remove(id);
        }
        return true;
    }

    public boolean createRegularTransportation(Date date, LocalTime leaving_time, int driver_id,
                                        int truck_license_number, List<Integer> suppliers, List<Integer> stores)
    {
        List<Supplier> suppliers1=new LinkedList<>();
        List<Store> stores1=new LinkedList<>();
        for(Supplier site:service.getSuppliersMap().values()){
            if(suppliers.contains(site.getId()))
                suppliers1.add(site);
        }

        for(Store site:service.getHashStoresMap().values()){
            if(suppliers.contains(site.getId()))
                stores1.add(site);
        }

        Transportation transportation =
                new Transportation(date,leaving_time,service.getDrivers().get(driver_id),service.getHashTrucks().get(truck_license_number),suppliers1,stores1);
        service.getHashTransportation().put(transportation.getId(),transportation);
        for (ItemsFile itemsFile: current)
        {
            transportation.addItemFile(itemsFile);
            current.remove(itemsFile);
        }
        service.getDrivers().get(driver_id).addDate(date);
        service.getHashTrucks().get(truck_license_number).addDate(date);
        return true;
    }


    public boolean delete_Transport(String id)
    {
        int transport_id = Integer.parseInt(id);
        for(Map.Entry<Integer, Transportation> transport:service.getHashTransportation().entrySet()){
            if (transport.getValue().getId()==transport_id){
                int driver=transport.getValue().getDriveId();
                int truck=transport.getValue().getTrucklicense();
                service.getDrivers().get(driver).Remove_date(transport.getValue().getDate());
                service.getHashTrucks().get(truck).Remove_date(transport.getValue().getDate());
                service.getHashTransportation().remove(transport.getKey());
            }
        }
        return true;
    }

    public String getTransport_id(){
        String result="";
        for(Transportation transportation: service.getHashTransportation().values()){
            result=result+transportation.getId()+" ,";
        }
        return result;
    }

    public void addItemFiletotransport(HashMap<String,Integer> items,int store,int supplier){
        ItemsFile itemsFile=new ItemsFile(items,service.getHashStoresMap().get(store),service.getSuppliersMap().get(supplier));
        current.add(itemsFile);
        service.getItemsFile().add(itemsFile);
    }

}
