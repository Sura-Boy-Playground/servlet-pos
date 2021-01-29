package lk.ijse.dep.web.business.custom;

import lk.ijse.dep.web.business.SuperBO;
import lk.ijse.dep.web.dto.OrderDTO;

import java.util.List;

public interface OrderBO extends SuperBO {

    public void placeOrder(OrderDTO dto) throws Exception;

    List<OrderDTO> searchOrdersByCustomerName(String name) throws Exception;
}
