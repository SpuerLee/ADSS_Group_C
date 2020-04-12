package Business_Layer;

import java.util.HashMap;
import java.util.List;

public class ItemsFile {


    private static int idcounter=0;

    private int id;
    private int transportation_id;
    private int source_site_id;
    private int destination_site_id;
    private HashMap<String,Integer> items_list;


    public ItemsFile(int transportation_id, int source_site_id, int destination_site_id, HashMap<String,Integer> items_list)
    {
        this.id=idcounter++;
        this.transportation_id=transportation_id;
        this.source_site_id=source_site_id;
        this.destination_site_id=destination_site_id;
        this.items_list=items_list;
    }
    public Integer getID()
    {
        return this.id;
    }

}
