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

    private DataAccess dataAccess;

    public AppWideBO(Connection connection){
        this.dataAccess = new DataAccess(connection);
    }

    public boolean saveCustomer(CustomerDTO dto) throws Exception{
        Customer customer = new Customer(dto.getId(), dto.getName(), dto.getAddress());
        return dataAccess.saveCustomer(customer);
    }

    public boolean updateCustomer(CustomerDTO dto) throws Exception{
        Customer customer = new Customer(dto.getId(), dto.getName(), dto.getAddress());
        return dataAccess.updateCustomer(customer);
    }

    public boolean deleteCustomer(String id) throws Exception{
        return dataAccess.deleteCustomer(id);
    }

    public List<CustomerDTO> getAllCustomers() throws Exception{
        return dataAccess.getAllCustomers().stream().
                map(c -> new CustomerDTO(c.getId(), c.getName(), c.getAddress()))
                .collect(Collectors.toList());
    }

    //-----------------

    public boolean saveItem(ItemDTO dto) throws Exception{
        Item item = new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand());
        return dataAccess.saveItem(item);
    }

    public boolean updateItem(ItemDTO dto) throws Exception{
        Item item = new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand());
        return dataAccess.updateItem(item);
    }

    public boolean deleteItem(String code) throws Exception{
        return dataAccess.deleteItem(code);
    }

    public List<ItemDTO> getAllItems() throws Exception{
        return dataAccess.getAllItems().stream().
                map(i -> new ItemDTO(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand()))
                .collect(Collectors.toList());
    }

    // --------------------------------------

    public boolean saveOrder(OrderDTO dto)throws Exception{
        Order order = new Order(dto.getOrderId(), Date.valueOf(dto.getOrderDate()), dto.getCustomerId());
        List<OrderDetail> orderDetails = dto.getOrderDetails().stream()
                .map(detailDto -> new OrderDetail(detailDto.getOrderId(),
                        detailDto.getItemCode(), detailDto.getQty(), detailDto.getUnitPrice()))
                .collect(Collectors.toList());
        return dataAccess.saveOrder(order, orderDetails);
    }
}
