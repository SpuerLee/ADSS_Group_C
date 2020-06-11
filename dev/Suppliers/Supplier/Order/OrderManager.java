package Suppliers.Supplier.Order;

import DataAccess.SupInvDBConn;
import Result.Result;
import Suppliers.DataAccess.PeriodicalOrderMapper;
import Suppliers.DataAccess.RegularOrderMapper;
import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;
import Suppliers.Structs.StructUtils;

import java.sql.Struct;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class OrderManager {

    private static OrderManager instance = null;

    private RegularOrderMapper regularOrderMapper;
    private PeriodicalOrderMapper periodicalOrderMapper;

    private OrderManager(){
        regularOrderMapper = new RegularOrderMapper(SupInvDBConn.getInstance());
        periodicalOrderMapper = new PeriodicalOrderMapper(SupInvDBConn.getInstance());
    }

    public static OrderManager getInstance(){
        if(instance == null){
            instance = new OrderManager();
        }
        return instance;
    }

    public RegularOrder getRegularOrder(int orderId){
        return regularOrderMapper.findById(orderId);
    }

    public PeriodicalOrder getPeriodicalOrder(int orderId){
        return periodicalOrderMapper.findById(orderId);
    }


    public void createRegularOrder(RegularOrder regularOrder){
        regularOrder.setOrderId(regularOrderMapper.insert(regularOrder));
    }

    public void createPeriodicalOrder(PeriodicalOrder periodicalOrder){
        periodicalOrder.setOrderId(periodicalOrderMapper.insert(periodicalOrder));
    }

    public boolean updateOrderDelivery(int orderId, Date date){
        Date dateNow = Calendar.getInstance().getTime();
        if(dateNow.compareTo(date) > 0){
            return false;
        }

        Order order = regularOrderMapper.loadBasicDetails(orderId);
        if(order == null || order.getStatus() == OrderStatus.Close){
            return false;
        }

        if(periodicalOrderMapper.isPeriodicalOrder(orderId)){
            return false;
        }

        return regularOrderMapper.updateDeliveryDate(orderId, date);
    }

    public boolean updateOrderStatus(int orderId, OrderStatus status){
        Order order = regularOrderMapper.loadBasicDetails(orderId);
        if(order == null || order.getStatus() == OrderStatus.Close){
            return false;
        }

        return regularOrderMapper.updateOrderStatus(orderId, status);
    }

    public List<Integer> getAllOpenOrderIdsByShop(int shopNumber){
        return regularOrderMapper.getAllOpenOrderIdsByShop(shopNumber);
    }

    public Order getOrderBasicDetails(int orderId) {
        return regularOrderMapper.loadBasicDetails(orderId);
    }

    public List<AllDetailsOfProductInOrder> getAllProductDetails(int orderId) {
        return regularOrderMapper.getAllProductDetails(orderId);
    }

    public boolean isPeriodicalOrder(int orderId){
        return periodicalOrderMapper.isPeriodicalOrder(orderId);
    }

    public List<Integer> addProductsToPeriodicalOrder(int orderId, List<ProductInOrder> productInOrders) {
        return periodicalOrderMapper.addProductsToPeriodicalOrder(orderId, productInOrders);
    }

    public int getTheSupplierOfOrder(int orderId) {
        return regularOrderMapper.getTheSupplierOfOrder(orderId);
    }

    public Result<List<String>> removeProductsFromOrder(int orderId, List<String> catalog) {
        List<String> catalogs = regularOrderMapper.getAllOrderCatalogs(orderId);
        if(catalogs == null){
            return Result.makeFailure("No such order id");
        }

        Set<String> depdupeCatalogs = new LinkedHashSet<>(catalog);
        catalog.clear();
        catalog.addAll(depdupeCatalogs);

        if(catalogs.size() == catalog.size()){
            return Result.makeFailure("Cant remove all the products");
        }

        return Result.makeOk("Remove products", regularOrderMapper.removeProductsFromOrder(orderId, catalog));
    }

    public Result<Order> orderArrived(int orderId) {
        Order order = getRegularOrder(orderId);
        if(order == null) {
            order = getPeriodicalOrder(orderId);
            if(order != null){

                //TODO need to use result and them too
                long diff = order.getDeliveryDay().getTime() - Calendar.getInstance().getTime().getTime();
                long day =  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                if(day > 1){
                    return Result.makeFailure("Cant recive periodical order if it is not the same date");
                }
                List<Days> days = periodicalOrderMapper.getDeliveryDays(orderId);
                int weekP = periodicalOrderMapper.getWeepPeriod(orderId);

                Date deliveryDate = StructUtils.getTheNearestDateWithWeekPeriod(days, weekP);
                //TODO to periodical order
                regularOrderMapper.updateDeliveryDate(orderId,deliveryDate);
            }
        } else {
            if(order.getStatus() == OrderStatus.Close){
                return Result.makeFailure("The order is alerady closed");
            }
            updateOrderStatus(orderId, OrderStatus.Close);
        }

        return Result.makeOk("Ok",order);
    }

    public List<Integer> getAllOpenOrders() {
        return regularOrderMapper.getAllOpenOrders();
    }

    public List<Integer> getAllOpenPeriodicalOrder() {
        return periodicalOrderMapper.getAllOpenOrders();
    }

    public PeriodicalOrderData getPeriodicalOrderData(int orderId) {
        if(isPeriodicalOrder(orderId)) {
            List<Days> days = periodicalOrderMapper.getDeliveryDays(orderId);
            int weekP = periodicalOrderMapper.getWeepPeriod(orderId);
            return new PeriodicalOrderData(days, weekP);
        }
        return null;
    }

    /**
     * Return all of the catalog number that exist is some order of the supplier
     * @param supplierId the supplier id
     * @return list of all of the catalog number that exist is some order of the supplier
     */
    public List<String> getPurchaseHistoryOfSupplier(int supplierId){
        return  regularOrderMapper.getPurchaseHistoryOfSupplier(supplierId);
    }
}