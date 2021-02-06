package lk.ijse.dep.web.business.util;


import lk.ijse.dep.web.dto.CustomerDTO;
import lk.ijse.dep.web.dto.ItemDTO;
import lk.ijse.dep.web.entity.Customer;
import lk.ijse.dep.web.entity.Item;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityDTOMapper {

//    default EntityDTOMapper getInstance(){
//        return Mappers.getMapper(EntityDTOMapper.class);
//    }

    Customer getCustomer(CustomerDTO dto);

    CustomerDTO getCustomerDTO(Customer customer);

    List<CustomerDTO> getCustomerDTOs(List<Customer> customers);

    Item getItem(ItemDTO dto);

    ItemDTO getItemDTO(Item item);

    List<ItemDTO> getItemDTOs(List<Item> item);

//    Order getOrder(OrderDTO dto);
}

@Qualifier
@Target(ElementType.METHOD)
@interface Address {

}
