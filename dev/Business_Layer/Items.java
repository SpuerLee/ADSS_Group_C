package Business_Layer;

import java.util.HashMap;
import java.util.List;

public class Items {

    public static HashMap<Integer,Items> HashItems= new HashMap<>();

    private int id;
    private int transportation_id;
    private int site_id;
    private List<HashMap<String,Integer>> items_list;

    public Items(int id, int transportation_id, int site_id, List<HashMap<String,Integer>> items_list)
    {
        this.id=id;
        this.transportation_id=transportation_id;
        this.site_id=site_id;
        this.items_list=items_list;
    }

    private void create_transport(int id, int transport_id,  int site_id, List<HashMap<String,Integer>> items_list)
    {
        HashItems.put(id,new Items(id,transportation_id,site_id,items_list));
    }

    private void delete_transport(int id)
    {
        HashItems.remove(id);
    }
}
