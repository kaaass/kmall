package net.kaaass.kmall.service.metadata;

import net.kaaass.kmall.dao.entity.MediaEntity;
import net.kaaass.kmall.dao.repository.MediaRepository;
import net.kaaass.kmall.dto.MediaDto;
import net.kaaass.kmall.mapper.CommenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResourceManager {
    @Autowired
    private MediaRepository mediaRepository;

    public Optional<MediaEntity> getEntity(String id) {
        return mediaRepository.findById(id);
    }

    public Optional<MediaDto> getResource(String id) {
        return getEntity(id).map(CommenMapper.INSTANCE::mediaEntityToDto);
    }
}
