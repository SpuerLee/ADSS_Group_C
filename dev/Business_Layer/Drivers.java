package Business_Layer;

import java.util.List;

    public class Drivers {


     Service service=Service.getInstance();
    private int id;
    private String name;
    private List<License> licenses;


    public Drivers(int id, String name, List<License> licenses)
    {
        this.id=id;
        this.name=name;
        this.licenses=licenses;
    }

}
