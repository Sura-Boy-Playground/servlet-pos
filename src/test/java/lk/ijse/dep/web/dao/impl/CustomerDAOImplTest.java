package lk.ijse.dep.web.dao.impl;

import lk.ijse.dep.web.ConnectionPool;
import lk.ijse.dep.web.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dep.web.entity.Customer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class CustomerDAOImplTest {

    private static BasicDataSource pool;
    private Connection connection;
    private CustomerDAOImpl dao;

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
        dao = new CustomerDAOImpl();
        dao.setConnection(connection);
    }

    @After
    public void finalizeAfterTest() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    public void saveCustomer() throws Exception {
        assertTrue(dao.save(new Customer("C007", "Tharanga", "Matara")));
        assertNotNull(dao.get("C007"));
    }

    @Test
    public void updateCustomer() throws Exception {
        dao.save(new Customer("C007","Tharanga","Matara"));
        assertTrue(dao.update(new Customer("C007", "Tharanga+","Matara+")));
        assertEquals(dao.get("C007").getName(),"Tharanga+");
        assertEquals(dao.get("C007").getAddress(),"Matara+");
    }

    @Test
    public void deleteCustomer() throws Exception {
        dao.save(new Customer("C007","Tharanga","Matara"));
        assertTrue(dao.delete("C007"));
        assertNull(dao.get("C007"));
    }

    @Test
    public void getAllCustomers() throws Exception {
        dao.save(new Customer("C007","Tharanga","Matara"));
        dao.save(new Customer("C008","Kasun","Matara"));
        assertTrue(dao.getAll().size() > 0);
    }

    @Test
    public void getCustomer() throws Exception {
        dao.save(new Customer("C007","Tharanga","Matara"));
        assertNotNull(dao.get("C007"));
        assertEquals(dao.get("C007").getName(), "Tharanga");
        assertEquals(dao.get("C007").getAddress(), "Matara");
    }
}
