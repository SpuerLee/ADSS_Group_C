package Business_Layer;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

    public class Drivers {

     private static int idcounter=0;
     private int id;
    private String name;
    private List<String> licenses;
    private List<Date> do_transportation_days = new LinkedList<>();


    public Drivers(String name, List<String> licenses)
    {
        this.id=idcounter++;
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

    public void addDate(Date date)
    {
        this.do_transportation_days.add(date);
    }

    public boolean checkIfFree(Date date)
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

    public void Remove_date(Date date){
        for(Date date1:do_transportation_days){
            if (date1.equals(date)){
                do_transportation_days.remove(date);
            }
        }
    }

    public List<Date> getDo_transportation_days(){
        return do_transportation_days;
    }

}
