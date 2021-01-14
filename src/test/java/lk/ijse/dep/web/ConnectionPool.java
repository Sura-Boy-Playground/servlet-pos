package lk.ijse.dep.web;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.util.Properties;

public class ConnectionPool {

    private static ConnectionPool connectionPool;
    private BasicDataSource bds;

    private ConnectionPool(){
        Properties dbProp = new Properties();
        System.out.println("Connection pool is being initialized...!");
        try {
            dbProp.load(this.getClass().getResourceAsStream("/application.properties"));
            bds = new BasicDataSource();
            bds.setUsername(dbProp.getProperty("mysql.username"));
            bds.setPassword(dbProp.getProperty("mysql.password"));
            bds.setUrl(dbProp.getProperty("mysql.url"));
            bds.setDriverClassName(dbProp.getProperty("mysql.driver_classname"));
            bds.setInitialSize(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance(){
        return (connectionPool == null)? (connectionPool = new ConnectionPool()): connectionPool;
    }

    public BasicDataSource getPool(){
        return bds;
    }

}
