package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.ConnectionPool;
import lk.ijse.dep.web.dao.DAOFactory;
import lk.ijse.dep.web.dao.DAOTypes;
import lk.ijse.dep.web.dao.custom.QueryDAO;
import lk.ijse.dep.web.entity.CustomEntity;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class QueryDAOImplTest {

    private static BasicDataSource pool;
    private Connection connection;
    private QueryDAO dao;

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
        dao = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);
        dao.setConnection(connection);
    }

    @After
    public void finalizeAfterTest() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    public void getOrderInfo() throws Exception {
        List<CustomEntity> orders = dao.getOrderInfo("C001");
        orders.forEach(System.out::println);
        assertTrue(orders.size() > 0);
    }
}
