package lk.ijse.dep.web.business.custom.impl;

import lk.ijse.dep.web.business.custom.ItemBO;
import lk.ijse.dep.web.business.util.EntityDTOMapper;
import lk.ijse.dep.web.dao.custom.ItemDAO;
import lk.ijse.dep.web.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemBOImpl implements ItemBO {

    @Autowired
    private EntityDTOMapper mapper;
    @Autowired
    private ItemDAO itemDAO;

    public ItemBOImpl() {

    }

    @Override
    public void saveItem(ItemDTO dto) throws Exception {
        itemDAO.save(mapper.getItem(dto));
    }

    @Override
    public void updateItem(ItemDTO dto) throws Exception {
        itemDAO.update(mapper.getItem(dto));
    }

    @Override
    public void deleteItem(String code) throws Exception {
        itemDAO.delete(code);
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        return mapper.getItemDTOs(itemDAO.getAll());
    }
}
