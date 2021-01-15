package lk.ijse.dep.web.dao.custom;

import lk.ijse.dep.web.dao.SuperDAO;
import lk.ijse.dep.web.entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {

    List<CustomEntity> getOrderInfo(String customerId) throws Exception;

}
