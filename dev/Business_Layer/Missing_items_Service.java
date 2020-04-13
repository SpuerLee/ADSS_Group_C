package Business_Layer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    public String getMissingItemsStores()
    {
        List<Integer> id_stores_list = new LinkedList<>();
        String output = "";
        for (MissingItems missingItems: service.getMissing())
        {
            Integer storeId=missingItems.getStoreId();
            if(!id_stores_list.contains(storeId))
            {
                id_stores_list.add(storeId);
                String store = service.getSitesMap().get(storeId).getName();
                output = output +storeId+". "+ store+"\n";
            }

        }
        return output;
    }
}
