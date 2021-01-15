package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.custom.QueryDAO;

import java.sql.Connection;

public class QueryDAOImpl implements QueryDAO {

    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }
}
