package Business_Layer;

import java.util.HashMap;
import java.util.List;

public class MissingItems {

    private static int idcounter=0;

    private int id;
    private int source_site_id;
    private int destination_site_id;
    private HashMap<String,Integer> items_list;


    public MissingItems(int source_site_id,int destination_site_id, HashMap<String,Integer> items_list)
    {
        this.id=idcounter++;
        this.source_site_id=source_site_id;
        this.destination_site_id=destination_site_id;
        this.items_list=items_list;
    }

    public Integer getID()
    {
        return this.id;
    }

    public Integer getStoreId()
    {
        return this.destination_site_id;
    }

    public Integer getSupplierId()
    {
        return this.source_site_id;
    }
    public HashMap<String,Integer> getItems_list()
    {
        return this.items_list;
    }
}
