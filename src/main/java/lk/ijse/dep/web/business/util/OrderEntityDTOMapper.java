package lk.ijse.dep.web.business.util;


import lk.ijse.dep.web.WebAppInitializer;
import lk.ijse.dep.web.dao.custom.CustomerDAO;
import lk.ijse.dep.web.dto.OrderDTO;
import lk.ijse.dep.web.dto.OrderDetailDTO;
import lk.ijse.dep.web.entity.Customer;
import lk.ijse.dep.web.entity.Order;
import lk.ijse.dep.web.entity.OrderDetail;
import lk.ijse.dep.web.entity.OrderDetailPK;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderEntityDTOMapper {

    @Autowired
    private CustomerDAO customerDAO;

    @Mapping(source = "orderId", target = "id")
    @Mapping(source = ".", target = "date")
    @Mapping(source = ".", target = "customer")
    public abstract Order getOrder(OrderDTO dto);

    public Date toDate(OrderDTO dto) {
        return Date.valueOf(dto.getOrderDate());
    }

    public Customer getCustomer(OrderDTO dto) throws Exception {
        return customerDAO.get(dto.getCustomerId());
    }

    @Mapping(source = ".", target = "orderDetailPK", qualifiedByName = "pk")
    public abstract OrderDetail getOrderDetail(OrderDetailDTO dto);

    public abstract List<OrderDetail> getOrderDetails(List<OrderDetailDTO> dtos);

    @Named("pk")
    public OrderDetailPK toOrderDetailPK(OrderDetailDTO dto) {
        return new OrderDetailPK(dto.getOrderId(), dto.getItemCode());
    }
}
