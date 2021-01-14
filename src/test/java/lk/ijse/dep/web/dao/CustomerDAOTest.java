package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.ConnectionPool;
import lk.ijse.dep.web.entity.Customer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class CustomerDAOTest {

    private static BasicDataSource pool;
    private Connection connection;
    private CustomerDAO dao;

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
        dao = new CustomerDAO(connection);
    }

    @After
    public void finalizeAfterTest() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    public void saveCustomer() throws Exception {
        assertTrue(dao.saveCustomer(new Customer("C007", "Tharanga", "Matara")));
        assertNotNull(dao.getCustomer("C007"));
    }

    @Test
    public void updateCustomer() throws Exception {
        dao.saveCustomer(new Customer("C007","Tharanga","Matara"));
        assertTrue(dao.updateCustomer(new Customer("C007", "Tharanga+","Matara+")));
        assertEquals(dao.getCustomer("C007").getName(),"Tharanga+");
        assertEquals(dao.getCustomer("C007").getAddress(),"Matara+");
    }

    @Test
    public void deleteCustomer() throws Exception {
        dao.saveCustomer(new Customer("C007","Tharanga","Matara"));
        assertTrue(dao.deleteCustomer("C007"));
        assertNull(dao.getCustomer("C007"));
    }

    @Test
    public void getAllCustomers() throws Exception {
        dao.saveCustomer(new Customer("C007","Tharanga","Matara"));
        dao.saveCustomer(new Customer("C008","Kasun","Matara"));
        assertTrue(dao.getAllCustomers().size() > 0);
    }

    @Test
    public void getCustomer() throws Exception {
        dao.saveCustomer(new Customer("C007","Tharanga","Matara"));
        assertNotNull(dao.getCustomer("C007"));
        assertEquals(dao.getCustomer("C007").getName(), "Tharanga");
        assertEquals(dao.getCustomer("C007").getAddress(), "Matara");
    }
}
