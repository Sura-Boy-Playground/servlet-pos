package lk.ijse.dep.web.business.custom;

import lk.ijse.dep.web.business.SuperBO;
import lk.ijse.dep.web.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO {

    void saveItem(ItemDTO dto) throws Exception;

    void updateItem(ItemDTO dto) throws Exception;

    void deleteItem(String code) throws Exception;

    List<ItemDTO> findAllItems() throws Exception;
}
