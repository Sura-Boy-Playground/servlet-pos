package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.CustomerBO;
import lk.ijse.dep.web.business.util.EntityDTOMapper;
import lk.ijse.dep.web.dao.DAOFactory;
import lk.ijse.dep.web.dao.DAOTypes;
import lk.ijse.dep.web.dao.custom.CustomerDAO;
import lk.ijse.dep.web.dto.CustomerDTO;
import org.hibernate.Session;

import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO;
    private final EntityDTOMapper mapper = EntityDTOMapper.instance;
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
        customerDAO.save(mapper.getCustomer(dto));
        session.getTransaction().commit();
    }

    @Override
    public void updateCustomer(CustomerDTO dto) throws Exception {
        session.beginTransaction();
        customerDAO.update(mapper.getCustomer(dto));
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
        List<CustomerDTO> collect = mapper.getCustomerDTOs(customerDAO.getAll());
        session.getTransaction().commit();
        return collect;
    }
}
