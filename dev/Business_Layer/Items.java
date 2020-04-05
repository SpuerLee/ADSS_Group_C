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

    }
}
