package Business_Layer;

import java.util.HashMap;
import java.util.List;

public class License {

    private enum type{
        A,
        B
    };
    private List<String> license_list;
    type license;

    public License(String type){
        switch (type){
            case "A":
              this.license= License.type.A;
            case "B":
                this.license=License.type.B;
        }
    }
}
