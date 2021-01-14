package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.Customer;
import lk.ijse.dep.web.entity.Item;
import lk.ijse.dep.web.entity.Order;
import lk.ijse.dep.web.entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataAccess {

    private Connection connection;

    public DataAccess(Connection connection) {
        this.connection = connection;
    }

    public boolean saveCustomer(Customer customer) throws Exception{
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?)");
        pstm.setString(1,customer.getId());
        pstm.setString(2,customer.getName());
        pstm.setString(3,customer.getAddress());
        return pstm.executeUpdate() > 0;
    }

    public boolean updateCustomer(Customer customer) throws  Exception {
        PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=?");
        pstm.setString(3,customer.getId());
        pstm.setString(1,customer.getName());
        pstm.setString(2,customer.getAddress());
        return pstm.executeUpdate() > 0;
    }

    public boolean deleteCustomer(String id) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setString(1,id);
        return pstm.executeUpdate() > 0;
    }

    public List<Customer> getAllCustomers() throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer");
        List<Customer> customers = new ArrayList<>();
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            customers.add(new Customer(rst.getString("id"),
                    rst.getString("name"),
                    rst.getString("address")));
        }
        return customers;
    }

    //----------------------------------------------------

    public boolean saveItem(Item item) throws Exception{
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
        pstm.setString(1,item.getCode());
        pstm.setString(2,item.getDescription());
        pstm.setBigDecimal(3,item.getUnitPrice());
        pstm.setInt(4,item.getQtyOnHand());
        return pstm.executeUpdate() > 0;
    }

    public boolean updateItem(Item item) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?, unit_price=?, qty_on_hand=? WHERE code=?");
        pstm.setString(4,item.getCode());
        pstm.setString(1,item.getDescription());
        pstm.setBigDecimal(2,item.getUnitPrice());
        pstm.setInt(3,item.getQtyOnHand());
        return pstm.executeUpdate() > 0;
    }

    public boolean deleteItem(String code) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code=?");
        pstm.setString(1,code);
        return pstm.executeUpdate() > 0;
    }

    public List<Item> getAllItems() throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item");
        List<Item> items = new ArrayList<>();
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            items.add(new Item(rst.getString("code"),
                    rst.getString("description"),
                    rst.getBigDecimal("unit_price"),
                    rst.getInt("qty_on_hand")));
        }
        return items;
    }

    //----------------------------------------------------

    public boolean saveOrder(Order order, List<OrderDetail> orderDetails) throws Exception {
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstm   = connection.prepareStatement("INSERT INTO `order` VALUES (?,?,?)");
            pstm.setString(1, order.getId());
            pstm.setDate(2, order.getDate());
            pstm.setString(3, order.getCustomerId());

            if (pstm.executeUpdate() > 0){
                pstm = connection.prepareStatement("INSERT INTO order_detail VALUES (?,?,?,?)");
                PreparedStatement pstm2 = connection.
                        prepareStatement("UPDATE item SET qty_on_hand = qty_on_hand - ? WHERE code=?");
                for (OrderDetail detail : orderDetails) {
                    pstm.setString(1, order.getId());
                    pstm.setString(2, detail.getItemCode());
                    pstm.setInt(3, detail.getQty());
                    pstm.setBigDecimal(4, detail.getUnitPrice());

                    pstm2.setInt(1, detail.getQty());
                    pstm2.setString(2, detail.getItemCode());

                    if (pstm.executeUpdate() == 0 || pstm2.executeUpdate() == 0){
                        throw new RuntimeException("Failed to complete the transaction");
                    }
                }
                connection.commit();
                return true;
            }else{
                throw new RuntimeException("Failed to complete the transaction");
            }
        }catch (Throwable t){
            connection.rollback();
            throw t;
        }finally {
            connection.setAutoCommit(true);
        }
    }

}
