package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.CrudUtil;
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
        return CrudUtil.execute(connection,"INSERT INTO item VALUES (?,?,?,?)", item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public boolean update(Item item) throws Exception {
        return CrudUtil.execute(connection, "UPDATE item SET description=?, unit_price=?, qty_on_hand=? WHERE code=?", item.getDescription(), item.getUnitPrice(), item.getQtyOnHand(), item.getCode());
    }

    @Override
    public boolean delete(String code) throws Exception {
        return CrudUtil.execute(connection,"DELETE FROM item WHERE code=?", code );
    }

    @Override
    public List<Item> getAll() throws Exception {
        List<Item> items = new ArrayList<>();
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM item");
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
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM item WHERE code=?", code);
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
