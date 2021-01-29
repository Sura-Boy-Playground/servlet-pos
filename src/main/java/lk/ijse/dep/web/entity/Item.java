package lk.ijse.dep.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item implements SuperEntity {

    @Id
    private String code;
    private String description;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Column(name = "qty_on_hand")
    private int qtyOnHand;
}
