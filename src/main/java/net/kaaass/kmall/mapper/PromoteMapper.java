package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.CartEntity;
import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.promote.PromoteItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PromoteMapper {
    PromoteMapper INSTANCE = Mappers.getMapper(PromoteMapper.class);

    PromoteItem cartEntityToPromoteItem(CartEntity cartEntity);
}
