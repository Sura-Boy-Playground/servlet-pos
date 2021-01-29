package lk.ijse.dep.web.dao.custom;

import lk.ijse.dep.web.dao.CrudDAO;
import lk.ijse.dep.web.entity.Customer;

import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer, String> {

    List<Customer> searchCustomersByName(String name) throws Exception;
}
