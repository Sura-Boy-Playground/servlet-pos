package lk.ijse.dep.web.business;

import lk.ijse.dep.web.dto.CustomerDTO;
import lk.ijse.dep.web.dto.ItemDTO;
import lk.ijse.dep.web.dto.OrderDTO;
import lk.ijse.dep.web.entity.Customer;
import lk.ijse.dep.web.entity.Item;
import lk.ijse.dep.web.entity.Order;
import lk.ijse.dep.web.entity.OrderDetail;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AppWideBO {


    public AppWideBO(Connection connection){

    }

    public boolean saveCustomer(CustomerDTO dto) throws Exception{
        return true;
    }

    public boolean updateCustomer(CustomerDTO dto) throws Exception{
        return true;
    }

    public boolean deleteCustomer(String id) throws Exception{
        return true;
    }

    public List<CustomerDTO> getAllCustomers() throws Exception{
        return null;
    }

    //-----------------

    public boolean saveItem(ItemDTO dto) throws Exception{
        return true;
    }

    public boolean updateItem(ItemDTO dto) throws Exception{
        return true;
    }

    public boolean deleteItem(String code) throws Exception{
        return true;
    }

    public List<ItemDTO> getAllItems() throws Exception{
        return null;
    }

    // --------------------------------------

    public boolean saveOrder(OrderDTO dto)throws Exception{
        return true;
    }
}
