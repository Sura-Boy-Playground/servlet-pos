package lk.ijse.dep.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetail implements SuperEntity {

    @EmbeddedId
    private OrderDetailPK orderDetailPK;
    private int qty;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @ManyToOne
    @JoinColumn(name="order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name="item_code", referencedColumnName = "code", insertable = false, updatable = false)
    private Item item;

    public OrderDetail(String orderId, String itemCode, int qty, BigDecimal unitPrice) {
        this.orderDetailPK = new OrderDetailPK(orderId, itemCode);
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(OrderDetailPK orderDetailPK, int qty, BigDecimal unitPrice) {
        this.orderDetailPK = orderDetailPK;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}
