package Transportation.Business_Layer.Services;

import Transportation.Business_Layer.Buisness_Exception;
import Transportation.Business_Layer.ItemsFile;
import Transportation.Business_Layer.MissingItems;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Missing_items_Service {

    private static class Singelton_Missing {
        private static Missing_items_Service instance = new Missing_items_Service();
    }
    private Missing_items_Service() {
        // initialization code..
    }
    public static Missing_items_Service getInstance() {
        return Singelton_Missing.instance;
    }

  //  private ConcurrentHashMap<Integer,MissingItems> HashMissingItems= new ConcurrentHashMap<>();
    Service service=Service.getInstance();

    public String getMissingItemsStores() throws Buisness_Exception
    {
        if(service.getMissing().size()==0){
            throw new Buisness_Exception("The are no missing items in any store");
        }
        else {
            List<Integer> id_stores_list = new LinkedList<Integer>();
            String output = "";
            for (MissingItems missingItems : service.getMissing().values()) {
                Integer storeId = missingItems.getStoreId();
                if (!id_stores_list.contains(storeId)) {
                    id_stores_list.add(storeId);
                    String store = service.getHashStoresMap().get(storeId).getName();
                    String area = service.getHashStoresMap().get(storeId).getArea().toString();
                    output = output + storeId + ". " + store + ", area: " + area + "\n";
                }

            }
            return output;
        }
    }

    public void add_to_items_file(HashMap<String,Integer> itemsList,Integer store,Integer supplier){
        service.getItemsFile().add(new ItemsFile(itemsList,service.getHashStoresMap().get(store),service.getSuppliersMap().get(supplier)));
    }
}
