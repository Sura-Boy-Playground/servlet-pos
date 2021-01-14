package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean saveCustomer(Customer customer) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?)");
        pstm.setString(1, customer.getId());
        pstm.setString(2, customer.getName());
        pstm.setString(3, customer.getAddress());
        return pstm.executeUpdate() > 0;
    }

    public boolean updateCustomer(Customer customer) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=?");
        pstm.setString(3, customer.getId());
        pstm.setString(1, customer.getName());
        pstm.setString(2, customer.getAddress());
        return pstm.executeUpdate() > 0;
    }

    public boolean deleteCustomer(String id) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public List<Customer> getAllCustomers() throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer");
        List<Customer> customers = new ArrayList<>();
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            customers.add(new Customer(rst.getString("id"),
                    rst.getString("name"),
                    rst.getString("address")));
        }
        return customers;
    }

    public Customer getCustomer(String id) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
        pstm.setString(1, id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Customer(rst.getString("id"),
                    rst.getString("name"),
                    rst.getString("address"));
        } else {
            return null;
        }
    }

}
