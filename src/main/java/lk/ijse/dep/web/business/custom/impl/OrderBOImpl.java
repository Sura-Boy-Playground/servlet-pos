package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.OrderBO;
import lk.ijse.dep.web.business.util.OrderEntityDTOMapper;
import lk.ijse.dep.web.dao.custom.CustomerDAO;
import lk.ijse.dep.web.dao.custom.ItemDAO;
import lk.ijse.dep.web.dao.custom.OrderDAO;
import lk.ijse.dep.web.dao.custom.OrderDetailDAO;
import lk.ijse.dep.web.dto.OrderDTO;
import lk.ijse.dep.web.entity.Item;
import lk.ijse.dep.web.entity.OrderDetail;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderBOImpl implements OrderBO {

    private final OrderEntityDTOMapper mapper = OrderEntityDTOMapper.instance;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private CustomerDAO customerDAO;
    private Session session;

    public OrderBOImpl() {
    }

    @Override
    public void setSession(Session session) throws Exception {
        this.session = session;
        orderDAO.setSession(session);
        itemDAO.setSession(session);
        orderDAO.setSession(session);
        orderDetailDAO.setSession(session);
        customerDAO.setSession(session);
    }

    @Override
    public void placeOrder(OrderDTO dto) throws Exception {
        session.beginTransaction();
        try {
            boolean result = false;

            /* 1. Saving the order */
            orderDAO.save(mapper.getOrder(dto));

            /* 2. Saving Order Details -> Updating the stock */
            List<OrderDetail> orderDetails = mapper.getOrderDetails(dto.getOrderDetails());
            for (OrderDetail orderDetail : orderDetails) {
                orderDetailDAO.save(orderDetail);

                /* 3. Let's update the stock */
                Item item = itemDAO.get(orderDetail.getOrderDetailPK().getItemCode());
                if (item.getQtyOnHand() - orderDetail.getQty() < 0) {
                    throw new RuntimeException("Invalid stock");
                }
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
                itemDAO.update(item);

            }

            session.getTransaction().commit();

        } catch (Throwable t) {
            session.getTransaction().rollback();
            throw t;
        }
    }

    @Override
    public List<OrderDTO> searchOrdersByCustomerName(String name) throws Exception {
        return null;
    }
}
