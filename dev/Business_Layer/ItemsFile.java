package Business_Layer;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ItemsFile {

    private static int idcounter=0;
    private HashMap<String,Integer> items_list;
    private Store store;
    private Supplier supplier;
    private int id;

    public ItemsFile(HashMap<String,Integer> items_list, Store store,Supplier supplier)
    {
        id=idcounter++;
        this.items_list=items_list;
        this.store=store;
        this.supplier=supplier;
    }

    public HashMap<String, Integer> getItems_list() {
        return items_list;
    }

    private ConcurrentHashMap<Integer,ItemsFile> HashItemsFile= new ConcurrentHashMap<>();

    public int getId(){
        return id;
    }

}
