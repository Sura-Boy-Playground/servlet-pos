package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.CrudDAOImpl;
import lk.ijse.dep.web.dao.custom.OrderDAO;
import lk.ijse.dep.web.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDAOImpl extends CrudDAOImpl<Order, String> implements OrderDAO {

}
