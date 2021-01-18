package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.OrderBO;
import lk.ijse.dep.web.dao.DAOFactory;
import lk.ijse.dep.web.dao.DAOTypes;
import lk.ijse.dep.web.dao.custom.ItemDAO;
import lk.ijse.dep.web.dao.custom.OrderDAO;
import lk.ijse.dep.web.dao.custom.OrderDetailDAO;
import lk.ijse.dep.web.dto.OrderDTO;
import lk.ijse.dep.web.entity.Item;
import lk.ijse.dep.web.entity.Order;
import lk.ijse.dep.web.entity.OrderDetail;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderBOImpl implements OrderBO {

    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;
    private ItemDAO itemDAO;
    private Connection connection;

    public OrderBOImpl() {
        orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);
        orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
        itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    }

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
        orderDAO.setConnection(connection);
        orderDetailDAO.setConnection(connection);
        itemDAO.setConnection(connection);
    }

    @Override
    public boolean placeOrder(OrderDTO dto) throws Exception {
        connection.setAutoCommit(false);
        try {
            boolean result = false;

            /* 1. Saving the order */
            result = orderDAO.save(new Order(dto.getOrderId(), Date.valueOf(dto.getOrderDate()), dto.getCustomerId()));

            if (!result){
                throw new RuntimeException("Failed to complete the transaction");
            }

            /* 2. Saving Order Details -> Updating the stock */
            List<OrderDetail> orderDetails = dto.getOrderDetails().stream().
                    map(detail -> new OrderDetail(dto.getOrderId(), detail.getItemCode(), detail.getQty(), detail.getUnitPrice()))
                    .collect(Collectors.toList());
            for (OrderDetail orderDetail : orderDetails) {
                result = orderDetailDAO.save(orderDetail);

                if (!result){
                    throw new RuntimeException("Failed to complete the transaction");
                }

                /* 3. Let's update the stock */
                Item item = itemDAO.get(orderDetail.getOrderDetailPK().getItemCode());
                if (item.getQtyOnHand() - orderDetail.getQty() < 0){
                    throw new RuntimeException("Invalid stock");
                }
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
                result = itemDAO.update(item);

                if (!result){
                    throw new RuntimeException("Failed to complete the transaction");
                }
            }

            connection.commit();
            return true;

        }catch (Throwable t){
            connection.rollback();
            throw t;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<OrderDTO> searchOrdersByCustomerName(String name) throws Exception {
        return null;
    }
}
