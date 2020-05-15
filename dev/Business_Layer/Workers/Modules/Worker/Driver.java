package Business_Layer.Workers.Modules.Worker;

import Business_Layer.Modules.License;
import Business_Layer.Transportations.Modules.Transportation;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

    public class Driver extends Worker{

    private List<String> licenses;
    private List<Transportation> transportations;

    public Driver(int id, String name, String phoneNumber, int bankAccount, int salary, Date date, String jobTitle, int Sn, int storeSN,String licenses) {
        super(id, name, phoneNumber, bankAccount, salary, date, jobTitle, Sn, storeSN);
        licenses = licenses.replaceAll(" ", "");
        this.licenses = new LinkedList<>();
        this.licenses.addAll(Arrays.asList(licenses.split(",")));
        this.licenses.removeIf(license -> !( license.equals("C") || license.equals("C1") ));
        transportations = new LinkedList<>();
    }


    public List<String> getLicenses()
    {
        return this.licenses;
    }

    public void addDate(Transportation transportation)
    {
        this.transportations.add(transportation);
    }

    public void Remove_date(Integer transportationID){
        transportations.removeIf(transportation1 -> transportationID == transportation1.getId());
    }

    public List<Transportation> getTransportations(){
        return transportations;
    }
        public boolean checkLicense(List<License> license_list) {
            boolean output = false;
            for (String license : this.licenses) {
                for(License license1:license_list){
                    if(license.equals(license1.getLicenseType())){
                        output = true;
                        break;
                    }
                }
            }
            return output;
        }
}
