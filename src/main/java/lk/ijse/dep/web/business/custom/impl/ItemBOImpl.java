package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.ItemBO;
import lk.ijse.dep.web.business.util.EntityDTOMapper;
import lk.ijse.dep.web.dao.custom.ItemDAO;
import lk.ijse.dep.web.dto.ItemDTO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemBOImpl implements ItemBO {

    @Autowired
    private ItemDAO itemDAO;
    private Session session;
    private final EntityDTOMapper mapper = EntityDTOMapper.instance;

    public ItemBOImpl() {

    }

    @Override
    public void setSession(Session session) throws Exception {
        this.session = session;
        itemDAO.setSession(session);
    }

    @Override
    public void saveItem(ItemDTO dto) throws Exception {
        session.beginTransaction();
        itemDAO.save(mapper.getItem(dto));
        session.getTransaction().commit();
    }

    @Override
    public void updateItem(ItemDTO dto) throws Exception {
        session.beginTransaction();
        itemDAO.update(mapper.getItem(dto));
        session.getTransaction().commit();
    }

    @Override
    public void deleteItem(String code) throws Exception {
        session.beginTransaction();
        itemDAO.delete(code);
        session.getTransaction().commit();
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        session.beginTransaction();
        List<ItemDTO> collect = mapper.getItemDTOs(itemDAO.getAll());
        session.getTransaction().commit();
        return collect;
    }
}
