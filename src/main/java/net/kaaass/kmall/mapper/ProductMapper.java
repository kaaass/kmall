package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.*;
import net.kaaass.kmall.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto productEntityToDto(ProductEntity productEntity);

    CategoryDto categoryEntityToDto(CategoryEntity categoryEntity);

    ProductMetadataDto productMetadataEntityToDto(ProductMetadataEntity productMetadataEntity);

    ProductStorageDto productStorageEntityToDto(ProductStorageEntity productStorageEntity);

    CartDto cartEntityToDto(CartEntity cartEntity);
}
