package Data_Layer.Dummy_objects;

public class dummy_Truck {

    private int Id;
    private int license_number;
    private String model;
    private double weight;
    private double max_weight;
    private int license_type;

    public dummy_Truck(int Id,int license_number, String model, double weight, double max_weight, int license_type){
        this.Id=Id;
        this.license_number=license_number;
        this.model=model;
        this.weight=weight;
        this.max_weight=max_weight;
        this.license_type=license_type;
    }

    public int getLicense_number() {
        return license_number;
    }

    public void setLicense_number(int license_number) {
        this.license_number = license_number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(double max_weight) {
        this.max_weight = max_weight;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getLicense_type() {
        return license_type;
    }

    public void setLicense_type(int license_type) {
        this.license_type = license_type;
    }
}
