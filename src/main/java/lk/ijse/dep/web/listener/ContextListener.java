package lk.ijse.dep.web.listener;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties dbProp = new Properties();
        System.out.println("Connection pool is being initialized...!");
        try {
            dbProp.load(this.getClass().getResourceAsStream("/application.properties"));
            BasicDataSource bds = new BasicDataSource();
            bds.setUsername(dbProp.getProperty("mysql.username"));
            bds.setPassword(dbProp.getProperty("mysql.password"));
            bds.setUrl(dbProp.getProperty("mysql.url"));
            bds.setDriverClassName(dbProp.getProperty("mysql.driver_classname"));
            bds.setInitialSize(5);
            sce.getServletContext().setAttribute("cp", bds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        BasicDataSource bds = (BasicDataSource) sce.getServletContext().getAttribute("cp");
        try {
            bds.close();
            System.out.println("Connection pool is closed...!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
