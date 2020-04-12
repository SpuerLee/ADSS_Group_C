package Business_Layer;

import java.util.HashMap;
import java.util.List;

public class ItemsFile {


    private static int idcounter=0;

    private int id;
    private int transportation_id;
    private int store_id;
    private int supplier_id;
    private HashMap<String,Integer> items_list;


    public ItemsFile(int transportation_id, int store_id, int supplier_id, HashMap<String,Integer> items_list)
    {
        this.id=idcounter++;
        this.transportation_id=transportation_id;
        this.store_id=store_id;
        this.supplier_id=supplier_id;
        this.items_list=items_list;
    }
    public Integer getID()
    {
        return this.id;
    }

}
