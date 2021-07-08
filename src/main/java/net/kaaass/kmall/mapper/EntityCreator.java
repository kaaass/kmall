package net.kaaass.kmall.mapper;

import net.kaaass.kmall.controller.request.UserAddressRequest;
import net.kaaass.kmall.dao.entity.OrderItemEntity;
import net.kaaass.kmall.dao.entity.PromoteStrategyEntity;
import net.kaaass.kmall.dao.entity.UserAddressEntity;
import net.kaaass.kmall.dto.CartDto;
import net.kaaass.kmall.dto.OrderItemDto;
import net.kaaass.kmall.dto.PromoteStrategyDto;
import net.kaaass.kmall.promote.PromoteItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 从 POJO 创建实体
 *
 * @author kaaass
 */
@Mapper(componentModel = "spring", uses = CommonTransform.class)
public interface EntityCreator {
    EntityCreator INSTANCE = Mappers.getMapper(EntityCreator.class);

    @Mappings({
            @Mapping(target = "product", expression = "java(null)"),
            @Mapping(target = "user", expression = "java(null)"),
            @Mapping(target = "order", expression = "java(null)"),
            @Mapping(target = "createTime", expression = "java(null)"),
    })
    OrderItemEntity createOrderItemEntity(OrderItemDto orderItemDto);

    PromoteStrategyEntity createPromoteStrategyEntity(PromoteStrategyDto promoteStrategyDto);

    @Mapping(target = "price", expression = "java(0)")
    PromoteItem createPromoteItem(CartDto cartDto);

    @Mapping(target = "id", expression = "java(null)")
    OrderItemDto createOrderItemDto(PromoteItem promoteItem);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "user", expression = "java(null)"),
            @Mapping(target = "defaultAddress", expression = "java(false)"),
            @Mapping(target = "lastUpdateTime", expression = "java(null)")
    })
    UserAddressEntity createUserAddressEntity(UserAddressRequest request);
}
