package lk.ijse.dep.web;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionPoolTest {

    @Test
    public void getInstance() {
        assertNotNull(ConnectionPool.getInstance());
        assertSame(ConnectionPool.getInstance(), ConnectionPool.getInstance());
    }

    @Test
    public void getPool() {
        assertNotNull(ConnectionPool.getInstance().getPool());
    }
}
