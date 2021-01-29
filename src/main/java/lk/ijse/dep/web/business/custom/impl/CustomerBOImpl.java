package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.CustomerBO;
import lk.ijse.dep.web.dao.DAOFactory;
import lk.ijse.dep.web.dao.DAOTypes;
import lk.ijse.dep.web.dao.custom.CustomerDAO;
import lk.ijse.dep.web.dto.CustomerDTO;
import lk.ijse.dep.web.entity.Customer;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO;
    private Session session;

    public CustomerBOImpl() {
        customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    }

    @Override
    public void setSession(Session session) throws Exception {
        this.session = session;
        customerDAO.setSession(session);
    }

    @Override
    public void saveCustomer(CustomerDTO dto) throws Exception {
        session.beginTransaction();
        customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress()));
        session.getTransaction().commit();
    }

    @Override
    public void updateCustomer(CustomerDTO dto) throws Exception {
        session.beginTransaction();
        customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress()));
        session.getTransaction().commit();
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        session.beginTransaction();
        customerDAO.delete(customerId);
        session.getTransaction().commit();
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        session.beginTransaction();
        List<CustomerDTO> collect = customerDAO.getAll().stream().
                map(c -> new CustomerDTO(c.getId(), c.getName(), c.getAddress())).collect(Collectors.toList());
        session.getTransaction().commit();
        return collect;
    }
}
