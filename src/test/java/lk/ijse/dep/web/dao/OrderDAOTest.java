package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.ConnectionPool;
import lk.ijse.dep.web.entity.Customer;
import lk.ijse.dep.web.entity.Order;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class OrderDAOTest {

    private static BasicDataSource pool;
    private Connection connection;
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;

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
    public void prepareBeforeTest() throws SQLException {
        connection = pool.getConnection();
        connection.setAutoCommit(false);
        orderDAO = new OrderDAO(connection);
        customerDAO = new CustomerDAO(connection);
    }

    @After
    public void finalizeAfterTest() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    public void saveOrder() throws Exception {
        customerDAO.saveCustomer(new Customer("C007","Kasun","Galle"));
        assertTrue(orderDAO.saveOrder
                (new Order("OD005",new Date(System.currentTimeMillis()),"C007")));
        assertNotNull(orderDAO.getOrder("OD005"));
    }
}
