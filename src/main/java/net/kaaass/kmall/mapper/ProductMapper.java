package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.CategoryEntity;
import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.dto.CategoryDto;
import net.kaaass.kmall.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto productEntityToDto(ProductEntity productEntity);

    CategoryDto categoryEntityToDto(CategoryEntity categoryEntity);
}
