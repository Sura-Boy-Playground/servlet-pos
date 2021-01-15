package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.CrudUtil;
import lk.ijse.dep.web.dao.custom.OrderDetailDAO;
import lk.ijse.dep.web.entity.OrderDetail;
import lk.ijse.dep.web.entity.OrderDetailPK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public boolean save(OrderDetail orderDetail) throws Exception {
        OrderDetailPK pk = orderDetail.getOrderDetailPK();
        return CrudUtil.execute(connection, "INSERT INTO order_detail VALUES (?,?,?,?)", pk.getOrderId(), pk.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice() );
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws Exception {
        OrderDetailPK pk = orderDetail.getOrderDetailPK();
        return CrudUtil.execute(connection,"UPDATE order_detail SET qty=?, unit_price=? WHERE order_id=? AND item_code=?", orderDetail.getQty(), orderDetail.getUnitPrice(), pk.getOrderId(), pk.getItemCode());
    }

    @Override
    public boolean delete(OrderDetailPK pk) throws Exception {
        return CrudUtil.execute(connection, "DELETE FROM orderDetail WHERE order_id=? AND item_code=?", pk.getOrderId(), pk.getItemCode());
    }

    @Override
    public List<OrderDetail> getAll() throws Exception {
        List<OrderDetail> orderDetails = new ArrayList<>();
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM orderDetail");
        while (rst.next()) {
            orderDetails.add(new OrderDetail(rst.getString("order_id"),
                    rst.getString("item_code"),
                    rst.getInt("qty"),
                    rst.getBigDecimal("unit_price")));
        }
        return orderDetails;
    }

    @Override
    public OrderDetail get(OrderDetailPK pk) throws Exception {
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM order_detail WHERE order_id=? AND item_code=?", pk.getOrderId(), pk.getItemCode());
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
