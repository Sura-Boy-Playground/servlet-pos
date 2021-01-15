package lk.ijse.dep.web.dao;

import java.sql.Connection;

public interface SuperDAO {

    public abstract void setConnection(Connection connection) throws Exception;

}
