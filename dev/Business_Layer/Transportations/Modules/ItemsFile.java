package Business_Layer.Transportations.Modules;

import Business_Layer.Modules.Store;
import Business_Layer.Modules.Supplier;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ItemsFile {

    private static int idcounter=1;
    private int transportationID=-1;
//    private HashMap<String,Integer> items_list;
    private List<Pair<String,Integer>> items_list;
    private Store store;
    private Supplier supplier;
    boolean from_missing_items=false;
    private int id;

    public ItemsFile(List<Pair<String,Integer>> items_list, Store store,Supplier supplier)
    {
        id=idcounter++;
        this.items_list=items_list;
        this.store=store;
        this.supplier=supplier;
    }

    public List<Pair<String,Integer>> getItems_list() {
        return items_list;
    }



    public int getId(){
        return id;
    }
    public Supplier getSupplier() {return this.supplier;}
    public Store getStore(){return this.store;}
    public void setTransportationID(int id)
    {
        this.transportationID=id;
    }
    public void setFrom_missing_items()
    {
        this.from_missing_items=true;
    }


}
