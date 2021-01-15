package lk.ijse.dep.web.dao.impl;

import lk.ijse.dep.web.ConnectionPool;
import lk.ijse.dep.web.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dep.web.dao.custom.impl.OrderDAOImpl;
import lk.ijse.dep.web.entity.Customer;
import lk.ijse.dep.web.entity.Order;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class OrderDAOImplTest {

    private static BasicDataSource pool;
    private Connection connection;
    private OrderDAOImpl orderDAOImpl;
    private CustomerDAOImpl customerDAOImpl;

    @BeforeClass
    public static void executeClassBefore(){
        pool = ConnectionPool.getInstance().getPool();
    }

    @AfterClass
    public static void executeClassAfter(){
        try {
            pool.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Before
    public void prepareBeforeTest() throws Exception {
        connection = pool.getConnection();
        connection.setAutoCommit(false);
        orderDAOImpl = new OrderDAOImpl();
        customerDAOImpl = new CustomerDAOImpl();
        orderDAOImpl.setConnection(connection);
        customerDAOImpl.setConnection(connection);
    }

    @After
    public void finalizeAfterTest() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    public void saveOrder() throws Exception {
        customerDAOImpl.save(new Customer("C007","Kasun","Galle"));
        assertTrue(orderDAOImpl.save
                (new Order("OD005",new Date(System.currentTimeMillis()),"C007")));
        assertNotNull(orderDAOImpl.get("OD005"));
    }
}
