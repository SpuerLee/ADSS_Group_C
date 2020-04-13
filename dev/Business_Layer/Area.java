package Business_Layer;

public class Area {

    public area getAreaName() {
        return AreaName;
    }

    public String toString(){
        if(AreaName.equals(area.A))
            return "A";
        else if(AreaName.equals(area.B))
            return "B";
        else if(AreaName.equals(area.C))
            return "C";
        else
            return "D";
    }

    private enum area{
        A,
        B,
        C,
        D
    };

    private area AreaName;

    public Area(String type){
        if(type.equals("A")){
            this.AreaName= area.A;
        }
        else if(type.equals("B")){
            this.AreaName= area.B;
        }

        else if(type.equals("C")){
            this.AreaName= area.C;
        }

        else if(type.equals("D")){
            this.AreaName= area.D;
        }
    }
}

