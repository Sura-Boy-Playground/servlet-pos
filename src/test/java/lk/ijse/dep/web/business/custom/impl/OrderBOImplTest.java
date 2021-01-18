package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.ConnectionPool;
import lk.ijse.dep.web.business.BOFactory;
import lk.ijse.dep.web.business.BOTypes;
import lk.ijse.dep.web.business.custom.CustomerBO;
import lk.ijse.dep.web.business.custom.ItemBO;
import lk.ijse.dep.web.business.custom.OrderBO;
import lk.ijse.dep.web.dto.OrderDTO;
import lk.ijse.dep.web.dto.OrderDetailDTO;
import lk.ijse.dep.web.entity.OrderDetail;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderBOImplTest {

    private static BasicDataSource pool;
    private Connection connection;
    private OrderBO orderBO;
    private ItemBO itemBO;

    @BeforeClass
    public static void executeClassBefore(){
        pool = ConnectionPool.getInstance().getPool();
    }

    @Before
    public void prepareBeforeTest() throws Exception {
        connection = pool.getConnection();
        //connection.setAutoCommit(false);
        orderBO = BOFactory.getInstance().getBO(BOTypes.ORDER);
        itemBO = BOFactory.getInstance().getBO(BOTypes.ITEM);
        orderBO.setConnection(connection);
        itemBO.setConnection(connection);
    }

    @After
    public void finalizeAfterTest() throws SQLException {
//        connection.rollback();
//        connection.setAutoCommit(true);
        connection.close();
    }

    @AfterClass
    public static void executeClassAfter(){
        try {
            pool.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void placeOrder() throws Exception {
        List<OrderDetailDTO> orderDetailsDTOs = new ArrayList<>();
        int i001Qty = getQty("I001");
        int i002Qty = getQty("I002");
        OrderDetailDTO orderDetail1 = new
                OrderDetailDTO("OD009", "I001", 2, new BigDecimal("500"));
        orderDetailsDTOs.add(orderDetail1);
        OrderDetailDTO orderDetail2 = new
                OrderDetailDTO("OD009", "I002", 2, new BigDecimal("900"));
        orderDetailsDTOs.add(orderDetail2);
        assertTrue(orderBO.placeOrder
                (new OrderDTO("OD009", LocalDate.now(),"C001",orderDetailsDTOs)));
        assertEquals(i001Qty, orderDetail1.getQty() + getQty(orderDetail1.getItemCode()));
        assertEquals(i002Qty, orderDetail2.getQty() + getQty(orderDetail2.getItemCode()));
    }

    private int getQty(String itemCode) throws Exception {
        return itemBO.findAllItems().stream().
                filter(dto -> dto.getCode().equals(itemCode)).findFirst().get().getQtyOnHand();
    }
}
