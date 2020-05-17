package Suppliers.Service;

import Suppliers.Supplier.Order.AllDetailsOfProductInOrder;
import Suppliers.Supplier.Order.PeriodicalOrderData;
import Suppliers.Supplier.Supplier;

import java.util.List;

public class OrderShipDetails {
    public int orderId;
    public int shopNumber;
    public String deliveryDate;
    public List<AllDetailsOfProductInOrder> details;
    public SupplierDetailsDTO supplier;
    public PeriodicalOrderData periodicalOrderData;

    public OrderShipDetails(int orderId, int shopNumber, String deliveryDate, SupplierDetailsDTO supplier,
                            List<AllDetailsOfProductInOrder> details, PeriodicalOrderData periodicalOrderData){
        this.orderId = orderId;
        this.shopNumber = shopNumber;
        this.supplier = supplier;
        this.details = details;
        this.deliveryDate = deliveryDate;
        this.periodicalOrderData = periodicalOrderData;
    }
}
