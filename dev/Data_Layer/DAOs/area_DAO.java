package Data_Layer.DAOs;

import Data_Layer.Connection;
import Data_Layer.Dummy_objects.dummy_Area;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class area_DAO {
    public List<dummy_Area> selectAll(){
        List<dummy_Area> output = new LinkedList<>();
        String query="SELECT * FROM Area";
        try {
            Statement stmt2 = Connection.getInstance().getConn().createStatement();
            ResultSet rs2  = stmt2.executeQuery(query);
            while (rs2.next()) {
                dummy_Area dummy_area = new dummy_Area(rs2.getInt("SN"),rs2.getString("AreaName"));
                output.add(dummy_area);
            }
            return output;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public void insert(){

    }

    public void delete(){

    }

    public void update(){

    }
}
