package Business_Layer;

public class License {

    private enum type{
        one,
        two
    };
    type license;

    public License(String type){
        switch (type){
            case "A":
              this.license= License.type.one;
            case "B":
                this.license=License.type.two;
        }
    }
}
