package net.kaaass.kmall.service.metadata;

import lombok.AllArgsConstructor;
import net.kaaass.kmall.dao.entity.UserMetadataEntity;
import net.kaaass.kmall.dao.repository.UserMetadataRepository;
import net.kaaass.kmall.dto.UserAuthDto;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserMetadataManager {

    private UserAuthDto userAuthDto;

    private UserMetadataRepository metadataRepository;

    public String get(String key, String defValue) {
        return metadataRepository.findByUidAndKey(userAuthDto.getId(), key)
                .map(UserMetadataEntity::getValue)
                .orElse(defValue);
    }

    public String get(String key) {
        return get(key, "");
    }

    public Map<String, String> getAll() {
        return metadataRepository.findAllByUid(userAuthDto.getId())
                .stream()
                .collect(Collectors.toMap(UserMetadataEntity::getKey, UserMetadataEntity::getValue));
    }

    public void set(String key, String value) {
        var metadata = metadataRepository.findByUidAndKey(userAuthDto.getId(), key)
                .orElseGet(() -> {
                    var newEntity = new UserMetadataEntity();
                    newEntity.setUid(userAuthDto.getId());
                    newEntity.setKey(key);
                    return newEntity;
                });
        metadata.setValue(value);
        metadataRepository.save(metadata);
    }
}
