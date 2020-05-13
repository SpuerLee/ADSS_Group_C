package Business_Layer.Modules;

public class License {

    private enum type{
        C,
        C1
    };
 //   private List<String> license_list;
    private type license;

    public License(String type){
        if(type.equals("C")){
            this.license=License.type.C;
        }
        else if(type.equals("C1")){
            this.license=License.type.C1;
        }
        }

        public type getType()
        {
            return this.license;
        }
    }

