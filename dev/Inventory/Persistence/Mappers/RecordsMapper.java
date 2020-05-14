package Inventory.Persistence.Mappers;

import Inventory.Persistence.DTO.ItemDTO;
import Inventory.Persistence.DTO.RecordDTO;
import Suppliers.DataAccess.ProductMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecordsMapper extends AbstractMappers {

    private Connection conn;

    public RecordsMapper(Connection conn) {
        this.conn = conn;
    }

    public HashMap<String, List<RecordDTO>> load(String shopNum) throws SQLException {
        String query = "SELECT * " +
                        "FROM Records " +
                "WHERE shopNum = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, shopNum);
            ResultSet resultSet  = statement.executeQuery();
            return builtDTOfromRes(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<String, List<RecordDTO>> builtDTOfromRes(ResultSet res) throws SQLException {

        String itemId, recId, shopNum, tmpCost, tmpPrice;
        Double cost, price;
        LocalDate costChangeDate, priceChangeDate;

        RecordDTO currRec;
        HashMap<String, List<RecordDTO>> RecsDTO = new HashMap<>();


        while(res.next()){
            List<RecordDTO> tmpRecLst = new ArrayList<>();
            itemId = res.getString(res.findColumn("itemId"));
            recId = res.getString(res.findColumn("recId"));
            shopNum = res.getString(res.findColumn("shopNum"));
            cost = res.getDouble(res.findColumn("cost"));
            price = res.getDouble(res.findColumn("price"));
            tmpCost = res.getString(res.findColumn("costChangeDate"));
            tmpPrice = res.getString(res.findColumn("costChangeDate"));
            costChangeDate = LocalDate.parse(tmpCost);
            priceChangeDate = LocalDate.parse((tmpPrice));
            currRec = new RecordDTO(recId, itemId, shopNum, cost, costChangeDate, price ,priceChangeDate);

            if(RecsDTO.keySet().contains(itemId))
                RecsDTO.get(itemId).add(currRec);
            else{
                tmpRecLst.add(currRec);
                RecsDTO.put(currRec.getRecId(), tmpRecLst);
            }
        }
        return RecsDTO;
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public void insert() {
    }

    public void insert(RecordDTO recDTO){


        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Records" +
                " (recId, shopNum, itemId, cost, price, costChangeDate, priceChangeDate) " +
                "Values (?, ?, ?, ?, ?, ?, ?)")){
            pstmt.setString(1, recDTO.getRecId());
            pstmt.setString(2, recDTO.getShopNum());
            pstmt.setString(3, recDTO.getItemId());
            pstmt.setDouble(4, recDTO.getCost());
            pstmt.setDouble(5, recDTO.getPrice());
            //TODO deal with the dates and types!
            //pstmt.setDate(6, recDTO.getCostChangeDate());
            //pstmt.setDate(7, recDTO.getPriceChangeDate());
            pstmt.executeUpdate();

        } catch (java.sql.SQLException e) { }
    }
    @Override
    public void update() {

    }
    @Override
    public void delete() {

    }
}
