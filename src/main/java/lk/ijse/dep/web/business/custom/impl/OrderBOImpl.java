package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.OrderBO;
import lk.ijse.dep.web.business.util.OrderEntityDTOMapper;
import lk.ijse.dep.web.dao.custom.ItemDAO;
import lk.ijse.dep.web.dao.custom.OrderDAO;
import lk.ijse.dep.web.dao.custom.OrderDetailDAO;
import lk.ijse.dep.web.dto.OrderDTO;
import lk.ijse.dep.web.entity.Item;
import lk.ijse.dep.web.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderBOImpl implements OrderBO {

    @Autowired
    private OrderEntityDTOMapper mapper;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;

    public OrderBOImpl() {
    }

    @Override
    public void placeOrder(OrderDTO dto) throws Exception {
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
    }

    @Override
    public List<OrderDTO> searchOrdersByCustomerName(String name) throws Exception {
        return null;
    }
}
