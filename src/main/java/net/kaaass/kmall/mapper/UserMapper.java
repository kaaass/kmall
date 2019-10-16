package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.UserAddressEntity;
import net.kaaass.kmall.dao.entity.UserAuthEntity;
import net.kaaass.kmall.dao.entity.UserMetadataEntity;
import net.kaaass.kmall.dto.UserAddressDto;
import net.kaaass.kmall.dto.UserAuthDto;
import net.kaaass.kmall.dto.UserMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserMetadataDto userMetadataEntityToDto(UserMetadataEntity metadataEntity);

    UserAuthDto userAuthEntityToDto(UserAuthEntity authEntity);

    UserAddressDto userAddressEntityToDto(UserAddressEntity addressEntity);

    UserAddressEntity userAddressDtoToEntity(UserAddressDto addressEntity);
}
