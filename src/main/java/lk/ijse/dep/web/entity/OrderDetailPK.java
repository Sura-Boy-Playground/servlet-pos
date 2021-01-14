package lk.ijse.dep.web.entity;

import java.io.Serializable;

public class OrderDetailPK implements Serializable {
    private String orderId;
    private String itemCode;

    public OrderDetailPK() {
    }

    public OrderDetailPK(String orderId, String itemCode) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public String toString() {
        return "OrderDetailPK{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                '}';
    }
}
