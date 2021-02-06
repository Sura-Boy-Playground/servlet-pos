package lk.ijse.dep.web.business.util;


import lk.ijse.dep.web.AppInitializer;
import lk.ijse.dep.web.dao.custom.CustomerDAO;
import lk.ijse.dep.web.dto.OrderDTO;
import lk.ijse.dep.web.dto.OrderDetailDTO;
import lk.ijse.dep.web.entity.Customer;
import lk.ijse.dep.web.entity.Order;
import lk.ijse.dep.web.entity.OrderDetail;
import lk.ijse.dep.web.entity.OrderDetailPK;
import lk.ijse.dep.web.util.HibernateUtil;
import org.hibernate.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.util.List;

@Mapper
public interface OrderEntityDTOMapper {

    OrderEntityDTOMapper instance = Mappers.getMapper(OrderEntityDTOMapper.class);

    @Mapping(source = "orderId", target = "id")
    @Mapping(source = ".", target = "date")
    @Mapping(source = ".", target = "customer")
    Order getOrder(OrderDTO dto);

    default Date toDate(OrderDTO dto){
        return Date.valueOf(dto.getOrderDate());
    }

    default Customer getCustomer(OrderDTO dto){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CustomerDAO customerDAO = AppInitializer.getContext().getBean(CustomerDAO.class);
            try {
                customerDAO.setSession(session);
                return customerDAO.get(dto.getCustomerId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Mapping(source = ".", target = "orderDetailPK", qualifiedByName = "pk")
    OrderDetail getOrderDetail(OrderDetailDTO dto);

    List<OrderDetail> getOrderDetails(List<OrderDetailDTO> dtos);

    @Named("pk")
    default OrderDetailPK toOrderDetailPK(OrderDetailDTO dto){
        return new OrderDetailPK(dto.getOrderId(), dto.getItemCode());
    }
}
