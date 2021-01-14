package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.OrderDetail;
import lk.ijse.dep.web.entity.OrderDetailPK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    private Connection connection;

    public OrderDetailDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean saveOrderDetail(OrderDetail orderDetail) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO order_detail VALUES (?,?,?,?)");
        pstm.setString(1, orderDetail.getOrderDetailPK().getOrderId());
        pstm.setString(2, orderDetail.getOrderDetailPK().getItemCode());
        pstm.setInt(3, orderDetail.getQty());
        pstm.setBigDecimal(4, orderDetail.getUnitPrice());
        return pstm.executeUpdate() > 0;
    }

    public boolean updateOrderDetail(OrderDetail orderDetail) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("UPDATE order_detail SET qty=?, unit_price=? WHERE order_id=? AND item_code=?");
        pstm.setString(3, orderDetail.getOrderDetailPK().getOrderId());
        pstm.setString(4, orderDetail.getOrderDetailPK().getItemCode());
        pstm.setInt(1, orderDetail.getQty());
        pstm.setBigDecimal(2, orderDetail.getUnitPrice());
        return pstm.executeUpdate() > 0;
    }

    public boolean deleteOrderDetail(OrderDetailPK pk) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM orderDetail WHERE order_id=? AND item_code=?");
        pstm.setString(1, pk.getOrderId());
        pstm.setString(2, pk.getItemCode());
        return pstm.executeUpdate() > 0;
    }

    public List<OrderDetail> getAllOrderDetails() throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orderDetail");
        List<OrderDetail> orderDetails = new ArrayList<>();
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            orderDetails.add(new OrderDetail(rst.getString("order_id"),
                    rst.getString("item_code"),
                    rst.getInt("qty"),
                    rst.getBigDecimal("unit_price")));
        }
        return orderDetails;
    }

    public OrderDetail getOrderDetail(OrderDetailPK pk) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM order_detail WHERE order_id=? AND item_code=?");
        pstm.setString(1, pk.getOrderId());
        pstm.setString(2, pk.getItemCode());
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new OrderDetail(rst.getString("order_id"),
                    rst.getString("item_code"),
                    rst.getInt("qty"),
                    rst.getBigDecimal("unit_price"));
        } else {
            return null;
        }
    }
    
}
