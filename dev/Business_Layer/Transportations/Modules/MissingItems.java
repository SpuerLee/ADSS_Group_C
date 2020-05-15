package Business_Layer.Transportations.Modules;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class MissingItems {

    private static int idcounter=1;

    private int id;
    private int supplier_id;
    private int store_id;
    private List<Pair<String,Integer>> items_list;


    public MissingItems(int store_id,int supplier_id, List<Pair<String,Integer>> items_list)
    {
        this.id=idcounter++;
        this.store_id=store_id;
        this.supplier_id=supplier_id;
        this.items_list=items_list;
    }

    public Integer getID()
    {
        return this.id;
    }
    public Integer getStoreId() { return this.store_id;}
    public Integer getSupplierId()
    {
        return this.supplier_id;
    }
    public List<Pair<String,Integer>> getItems_list()
    {
        return this.items_list;
    }
}
