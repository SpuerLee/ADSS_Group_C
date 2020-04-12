package Business_Layer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

    public class Drivers {

    private int id;
    private String name;
    private List<String> licenses;
    private List<LocalDate> do_transportation_days = new LinkedList<>();


    public Drivers(int id, String name, List<String> licenses)
    {
        this.id=id;
        this.name=name;
        this.licenses=licenses;
    }

    public Integer getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }
    public List<String> getLicenses()
    {
        return this.licenses;
    }

    public void addDate(LocalDate date)
    {
        this.do_transportation_days.add(date);
    }

    public boolean checkIfFree(LocalDate date)
    {
        if(do_transportation_days.contains(date))
        {
            return false;
        }
        else
        {
            //do_transportation_days.add(date);
            return true;
        }
    }

}
