package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.custom.QueryDAO;
import lk.ijse.dep.web.entity.CustomEntity;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    private Session session;

    @Override
    public void setSession(Session session) throws Exception {
        this.session = session;
    }

    @Override
    public List<CustomEntity> getOrderInfo(String customerId) throws Exception {

        List<Object[]> rows = session.createNativeQuery("SELECT c.id AS customer_id, c.name AS customer_name, o.id AS order_id, o.date AS order_date,\n" +
                "       SUM(od.qty * od.unit_price) as order_detail\n" +
                "FROM customer c\n" +
                "INNER JOIN `order` o on c.id = o.customer_id\n" +
                "INNER JOIN order_detail od on o.id = od.order_id\n" +
                "WHERE c.id=? GROUP BY o.id;").list();

        List<CustomEntity> orders = new ArrayList<>();
        for (Object[] row : rows) {
            orders.add(new CustomEntity(row[0].toString(),
                    row[1].toString(),
                    row[2].toString(),
                    (Date) row[3],
                    (BigDecimal) row[4]));
        }

        return orders;

//        ResultSet rst = CrudUtil.execute(null, "SELECT c.id AS customer_id, c.name AS customer_name, o.id AS order_id, o.date AS order_date,\n" +
//                "       SUM(od.qty * od.unit_price) as order_detail\n" +
//                "FROM customer c\n" +
//                "INNER JOIN `order` o on c.id = o.customer_id\n" +
//                "INNER JOIN order_detail od on o.id = od.order_id\n" +
//                "WHERE c.id=? GROUP BY o.id;", customerId);
//        List<CustomEntity> orders = new ArrayList<>();
//        while (rst.next()){
//            orders.add(new CustomEntity(rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDate(4),
//                    rst.getBigDecimal(5)));
//        }
//        return orders;
    }
}
