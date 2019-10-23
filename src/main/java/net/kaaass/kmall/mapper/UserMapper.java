package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.*;
import net.kaaass.kmall.dto.*;
import net.kaaass.kmall.vo.CommentVo;
import net.kaaass.kmall.vo.UserAuthVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserMetadataDto userMetadataEntityToDto(UserMetadataEntity metadataEntity);

    UserAuthDto userAuthEntityToDto(UserAuthEntity authEntity);

    UserAuthVo userAuthDtoToVo(UserAuthDto authDto);

    UserAddressDto userAddressEntityToDto(UserAddressEntity addressEntity);

    UserAddressEntity userAddressDtoToEntity(UserAddressDto addressEntity);

    UserInfoDto userInfoEntityToDto(UserInfoEntity userInfoEntity);

    CommentDto commentEntityToDto(CommentEntity commentEntity);

    CommentVo commentEntityToVo(CommentEntity commentEntity);
}
