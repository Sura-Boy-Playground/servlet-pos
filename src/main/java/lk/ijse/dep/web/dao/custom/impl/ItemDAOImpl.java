package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.custom.ItemDAO;
import lk.ijse.dep.web.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public boolean save(Item item) throws Exception{
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
        pstm.setString(1,item.getCode());
        pstm.setString(2,item.getDescription());
        pstm.setBigDecimal(3,item.getUnitPrice());
        pstm.setInt(4,item.getQtyOnHand());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(Item item) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?, unit_price=?, qty_on_hand=? WHERE code=?");
        pstm.setString(4,item.getCode());
        pstm.setString(1,item.getDescription());
        pstm.setBigDecimal(2,item.getUnitPrice());
        pstm.setInt(3,item.getQtyOnHand());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String code) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code=?");
        pstm.setString(1,code);
        return pstm.executeUpdate() > 0;
    }

    @Override
    public List<Item> getAll() throws Exception {
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

    @Override
    public Item get(String code) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE code=?");
        pstm.setString(1, code.toString());
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Item(rst.getString("code"),
                    rst.getString("description"),
                    rst.getBigDecimal("unit_price"),
                    rst.getInt("qty_on_hand"));
        } else {
            return null;
        }
    }

}
