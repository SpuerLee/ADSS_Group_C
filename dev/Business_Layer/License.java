package Business_Layer;

import java.util.HashMap;
import java.util.List;

public class License {

    private enum type{
        C,
        C1
    };
    private List<String> license_list;
    type license;

    public License(String type){
        if(type.equals("C")){
            this.license=License.type.C;
        }
        else if(type.equals("C1")){
            this.license=License.type.C1;
        }
        }
    }

