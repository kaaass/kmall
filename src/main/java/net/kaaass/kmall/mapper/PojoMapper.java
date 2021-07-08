package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.*;
import net.kaaass.kmall.dto.*;
import net.kaaass.kmall.vo.CommentVo;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;
import net.kaaass.kmall.vo.UserAuthVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * POJO 映射
 *
 * @author kaaass
 */
@Mapper(componentModel = "spring", uses = CommonTransform.class)
public interface PojoMapper {

    MediaDto entityToDto(MediaEntity metadataEntity);

    PluginDto entityToDto(PluginEntity pluginEntity);

    OrderDto entityToDto(OrderEntity orderEntity);

    PromoteStrategyDto entityToDto(PromoteStrategyEntity promoteStrategyEntity);

    PromoteStrategyInfoVo dtoToVo(PromoteStrategyDto promoteStrategyDto);

    ProductDto entityToDto(ProductEntity productEntity);

    @Mapping(target = "templateId", source = "template", qualifiedByName = "getTemplateId")
    CategoryDto entityToDto(CategoryEntity categoryEntity);

    ProductStorageDto entityToDto(ProductStorageEntity productStorageEntity);

    UserAuthDto entityToDto(UserAuthEntity authEntity);

    UserAuthVo dtoToVo(UserAuthDto authDto);

    UserAddressDto entityToDto(UserAddressEntity addressEntity);

    UserInfoDto entityToDto(UserInfoEntity userInfoEntity);

    @Mapping(target = "avatar", source = "user", qualifiedByName = "getAvatarFromAuth")
    CommentVo entityToVo(CommentEntity commentEntity);

    @Mapping(target = "schema", source = "schema", qualifiedByName = "deserializeSchema")
    ProductTemplateDto entityToDto(ProductTemplateEntity entity);
}
