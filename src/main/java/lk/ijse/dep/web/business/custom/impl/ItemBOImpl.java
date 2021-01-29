package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.ItemBO;
import lk.ijse.dep.web.dao.DAOFactory;
import lk.ijse.dep.web.dao.DAOTypes;
import lk.ijse.dep.web.dao.custom.ItemDAO;
import lk.ijse.dep.web.dto.ItemDTO;
import lk.ijse.dep.web.entity.Item;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class ItemBOImpl implements ItemBO {

    private ItemDAO itemDAO;
    private Session session;

    public ItemBOImpl() {
        itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    }

    @Override
    public void setSession(Session session) throws Exception {
        this.session = session;
        itemDAO.setSession(session);
    }

    @Override
    public void saveItem(ItemDTO dto) throws Exception {
        itemDAO.save(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
    }

    @Override
    public void updateItem(ItemDTO dto) throws Exception {
        itemDAO.update(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
    }

    @Override
    public void deleteItem(String code) throws Exception {
        itemDAO.delete(code);
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        return itemDAO.getAll().stream().
                map(i -> new ItemDTO(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand())).collect(Collectors.toList());
    }
}
