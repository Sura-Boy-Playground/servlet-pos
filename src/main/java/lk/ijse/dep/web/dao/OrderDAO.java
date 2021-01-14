package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean saveOrder(Order order) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO `order` VALUES (?,?,?)");
        pstm.setString(1, order.getId());
        pstm.setDate(2, order.getDate());
        pstm.setString(3, order.getCustomerId());
        return pstm.executeUpdate() > 0;
    }

    public boolean updateOrder(Order order) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("UPDATE `order` SET date=?, customer=? WHERE id=?");
        pstm.setString(3, order.getId());
        pstm.setDate(1, order.getDate());
        pstm.setString(2, order.getCustomerId());
        return pstm.executeUpdate() > 0;
    }

    public boolean deleteOrder(String id) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM `order` WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public List<Order> getAllOrders() throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM `order`");
        List<Order> orders = new ArrayList<>();
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            orders.add(new Order(rst.getString("id"),
                    rst.getDate("date"),
                    rst.getString("customer_id")));
        }
        return orders;
    }

    public Order getOrder(String id) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM `order` WHERE id=?");
        pstm.setString(1, id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Order(rst.getString("id"),
                    rst.getDate("date"),
                    rst.getString("customer_id"));
        } else {
            return null;
        }
    }


}
