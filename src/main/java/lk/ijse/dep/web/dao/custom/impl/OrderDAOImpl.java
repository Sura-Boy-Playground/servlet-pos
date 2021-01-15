package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.CrudUtil;
import lk.ijse.dep.web.dao.custom.OrderDAO;
import lk.ijse.dep.web.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public boolean save(Order order) throws Exception {
        return CrudUtil.execute(connection, "INSERT INTO `order` VALUES (?,?,?)", order.getId(), order.getDate(), order.getCustomerId());
    }

    @Override
    public boolean update(Order order) throws Exception {
        return CrudUtil.execute(connection, "UPDATE `order` SET date=?, customer=? WHERE id=?", order.getDate(), order.getCustomerId(), order.getId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.execute(connection, "DELETE FROM `order` WHERE id=?", id);
    }

    @Override
    public List<Order> getAll() throws Exception {
        List<Order> orders = new ArrayList<>();
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM `order`");
        while (rst.next()) {
            orders.add(new Order(rst.getString("id"),
                    rst.getDate("date"),
                    rst.getString("customer_id")));
        }
        return orders;
    }

    @Override
    public Order get(String id) throws Exception {
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM `order` WHERE id=?", id);
        if (rst.next()) {
            return new Order(rst.getString("id"),
                    rst.getDate("date"),
                    rst.getString("customer_id"));
        } else {
            return null;
        }
    }


}
