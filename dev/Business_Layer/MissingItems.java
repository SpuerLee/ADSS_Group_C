package Business_Layer;

import java.util.HashMap;
import java.util.List;

public class MissingItems {

    private int id;
    private int transportation_id;
    private int site_id;
    private List<HashMap<String,Integer>> items_list;


    public MissingItems(int id, int transportation_id, int site_id, List<HashMap<String,Integer>> items_list)
    {
        this.id=id;
        this.transportation_id=transportation_id;
        this.site_id=site_id;
        this.items_list=items_list;
    }
}
