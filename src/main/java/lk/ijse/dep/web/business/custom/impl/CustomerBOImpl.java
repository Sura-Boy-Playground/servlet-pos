package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.CustomerBO;
import lk.ijse.dep.web.business.custom.ItemBO;
import lk.ijse.dep.web.business.util.EntityDTOMapper;
import lk.ijse.dep.web.dao.custom.CustomerDAO;
import lk.ijse.dep.web.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private EntityDTOMapper mapper;

    @Autowired
    private ItemBO itemBO;

    @Override
    public void saveCustomer(CustomerDTO dto) throws Exception {
        customerDAO.save(mapper.getCustomer(dto));
    }

    @Override
    public void updateCustomer(CustomerDTO dto) throws Exception {
        customerDAO.update(mapper.getCustomer(dto));
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        customerDAO.delete(customerId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        return mapper.getCustomerDTOs(customerDAO.getAll());
    }

    @Override
    public CustomerDTO findCustomer(String id) throws Exception {
        return mapper.getCustomerDTO(customerDAO.get(id));
    }
}
