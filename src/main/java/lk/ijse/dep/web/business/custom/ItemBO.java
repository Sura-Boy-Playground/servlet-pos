package lk.ijse.dep.web.business.custom;

import lk.ijse.dep.web.business.SuperBO;
import lk.ijse.dep.web.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO {

    boolean saveItem(ItemDTO dto) throws Exception;

    boolean updateItem(ItemDTO dto) throws Exception;

    boolean deleteItem(String code) throws Exception;

    List<ItemDTO> findAllItems() throws Exception;
}
