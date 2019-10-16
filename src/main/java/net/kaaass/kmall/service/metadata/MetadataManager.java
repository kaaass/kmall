package net.kaaass.kmall.service.metadata;

import lombok.AllArgsConstructor;
import net.kaaass.kmall.dao.entity.MetadataEntity;
import net.kaaass.kmall.dao.entity.UserMetadataEntity;
import net.kaaass.kmall.dao.repository.MetadataRepository;
import net.kaaass.kmall.dao.repository.UserMetadataRepository;
import net.kaaass.kmall.dto.UserAuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MetadataManager {

    private MetadataRepository metadataRepository;

    public String get(String key, String defValue) {
        return metadataRepository.findByKey(key)
                .map(MetadataEntity::getValue)
                .orElse(defValue);
    }

    public String get(String key) {
        return get(key, "");
    }

    public Map<String, String> getAll() {
        return metadataRepository.findAll()
                .stream()
                .collect(Collectors.toMap(MetadataEntity::getKey, MetadataEntity::getValue));
    }

    public void set(String key, String value) {
        var metadata = metadataRepository.findByKey(key)
                .orElseGet(() -> {
                    var newEntity = new MetadataEntity();
                    newEntity.setKey(key);
                    return newEntity;
                });
        metadata.setValue(value);
        metadata.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        metadataRepository.save(metadata);
    }
}
