package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.ConnectionPool;
import lk.ijse.dep.web.business.BOFactory;
import lk.ijse.dep.web.business.BOTypes;
import lk.ijse.dep.web.business.custom.CustomerBO;
import lk.ijse.dep.web.dao.DAOFactory;
import lk.ijse.dep.web.dao.DAOTypes;
import lk.ijse.dep.web.dto.CustomerDTO;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class CustomerBOImplTest {

    private static BasicDataSource pool;
    private Connection connection;
    private CustomerBO customerBO;

    @BeforeClass
    public static void executeClassBefore(){
        pool = ConnectionPool.getInstance().getPool();
    }

    @Before
    public void prepareBeforeTest() throws Exception {
        connection = pool.getConnection();
        connection.setAutoCommit(false);
        customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
        customerBO.setConnection(connection);
    }

    @After
    public void finalizeAfterTest() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
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
    public void saveCustomer() throws Exception {
        assertTrue(customerBO.saveCustomer(new CustomerDTO("C007","Nipun","Galle")));
    }
}
