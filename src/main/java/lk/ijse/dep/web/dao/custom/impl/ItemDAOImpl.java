package lk.ijse.dep.web.dao.custom.impl;

import lk.ijse.dep.web.dao.CrudDAOImpl;
import lk.ijse.dep.web.dao.custom.ItemDAO;
import lk.ijse.dep.web.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDAOImpl extends CrudDAOImpl<Item, String> implements ItemDAO {

}
