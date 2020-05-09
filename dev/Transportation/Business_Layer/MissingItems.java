package Transportation.Business_Layer;

import java.util.HashMap;

public class MissingItems {

    private static int idcounter=1;

    private int id;
    private int supplier_id;
    private int store_id;
    private HashMap<String,Integer> items_list;


    public MissingItems(int store_id,int supplier_id, HashMap<String,Integer> items_list)
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
    public HashMap<String,Integer> getItems_list()
    {
        return this.items_list;
    }
}
