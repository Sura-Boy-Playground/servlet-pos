package lk.ijse.dep.web.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetail implements SuperEntity {

    private OrderDetailPK orderDetailPK;
    private int qty;
    private BigDecimal unitPrice;

    public OrderDetail() {
    }

    public OrderDetail(OrderDetailPK orderDetailPK, int qty, BigDecimal unitPrice) {
        this.orderDetailPK = orderDetailPK;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(String orderId, String itemCode, int qty, BigDecimal unitPrice) {
        this.orderDetailPK = new OrderDetailPK(orderId, itemCode);
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetailPK getOrderDetailPK() {
        return orderDetailPK;
    }

    public void setOrderDetailPK(OrderDetailPK orderDetailPK) {
        this.orderDetailPK = orderDetailPK;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailPK=" + orderDetailPK +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
