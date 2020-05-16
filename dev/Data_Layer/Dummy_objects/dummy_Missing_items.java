package Data_Layer.Dummy_objects;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.List;

public class dummy_Missing_items {

    private  int id;
    private int store_id;
    private int supplier_id;
    private List<Pair<String,Integer>> missing;
    public dummy_Missing_items(int id, int store_id, int supplier_id,List<Pair<String,Integer>> missing){
        this.id=id;
        this.store_id=store_id;
        this.supplier_id=supplier_id;
        this.missing=missing;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public List<Pair<String, Integer>> getMissing() {
        return missing;
    }

    public void setMissing(List<Pair<String, Integer>> missing) {
        this.missing = missing;
    }
}
