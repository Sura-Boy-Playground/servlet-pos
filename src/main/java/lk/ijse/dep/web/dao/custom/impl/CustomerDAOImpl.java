package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.custom.CustomerDAO;
import lk.ijse.dep.web.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public boolean save(Customer customer) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?)");
        pstm.setString(1, customer.getId());
        pstm.setString(2, customer.getName());
        pstm.setString(3, customer.getAddress());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=?");
        pstm.setString(3, customer.getId());
        pstm.setString(1, customer.getName());
        pstm.setString(2, customer.getAddress());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String key) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setString(1, key);
        return pstm.executeUpdate() > 0;
    }

    @Override
    public List<Customer> getAll() throws Exception {
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

    @Override
    public Customer get(String key) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
        pstm.setString(1, key);
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
