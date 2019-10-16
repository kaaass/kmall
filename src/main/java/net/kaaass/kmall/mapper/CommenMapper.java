package net.kaaass.kmall.mapper;

import net.kaaass.kmall.dao.entity.MediaEntity;
import net.kaaass.kmall.dao.entity.MetadataEntity;
import net.kaaass.kmall.dto.MediaDto;
import net.kaaass.kmall.dto.MetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommenMapper {
    CommenMapper INSTANCE = Mappers.getMapper(CommenMapper.class);

    MetadataDto metadataEntityToDto(MetadataEntity metadataEntity);

    MediaDto mediaEntityToDto(MediaEntity metadataEntity);
}
