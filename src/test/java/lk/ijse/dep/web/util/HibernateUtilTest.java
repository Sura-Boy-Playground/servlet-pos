package lk.ijse.dep.web.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class HibernateUtilTest {

    @Test
    public void getSessionFactory() {
        assertNotNull(HibernateUtil.getSessionFactory());
        assertNotNull(HibernateUtil.getSessionFactory().openSession());
    }
}
