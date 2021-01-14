package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.Customer;
import lk.ijse.dep.web.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private Connection connection;

    public ItemDAO(Connection connection) {
        this.connection = connection;
    }

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

    public Item getItem(String code) throws Exception {
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE code=?");
        pstm.setString(1, code);
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
