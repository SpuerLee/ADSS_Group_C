package Business_Layer;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ItemsFile {

    private HashMap<String,Integer> items_list;

    public ItemsFile(HashMap<String,Integer> items_list)
    {
        this.items_list=items_list;
    }

    public HashMap<String, Integer> getItems_list() {
        return items_list;
    }

    private ConcurrentHashMap<Integer,ItemsFile> HashItemsFile= new ConcurrentHashMap<>();

}
