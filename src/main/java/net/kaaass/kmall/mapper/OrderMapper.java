package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.OrderEntity;
import net.kaaass.kmall.dao.entity.OrderItemEntity;
import net.kaaass.kmall.dto.OrderDto;
import net.kaaass.kmall.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto orderEntityToDto(OrderEntity orderEntity);

    OrderItemDto orderItemEntityToDto(OrderItemEntity orderItemEntity);

    OrderItemEntity orderItemDtoToEntity(OrderItemDto orderItemDto);
}
