package net.kaaass.kmall.service.metadata;

import lombok.AllArgsConstructor;
import net.kaaass.kmall.dao.entity.MetadataEntity;
import net.kaaass.kmall.dao.entity.ProductMetadataEntity;
import net.kaaass.kmall.dao.entity.UserMetadataEntity;
import net.kaaass.kmall.dao.repository.MetadataRepository;
import net.kaaass.kmall.dao.repository.ProductMetadataRepository;
import net.kaaass.kmall.dao.repository.ProductRepository;
import net.kaaass.kmall.dao.repository.UserMetadataRepository;
import net.kaaass.kmall.dto.UserAuthDto;
import net.kaaass.kmall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MetadataManager {

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private ProductMetadataRepository productMetadataRepository;

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

    /*
     * Product
     */

    public String getForProduct(String productId, String key, String defValue) {
        return productMetadataRepository.findByProductIdAndKey(productId, key)
                .map(ProductMetadataEntity::getValue)
                .orElse(defValue);
    }

    public String getForProduct(String productId, String key) {
        return getForProduct(productId, key, "");
    }

    public Map<String, String> getAllForProduct(String productId) {
        return productMetadataRepository.findAllByProductId(productId)
                .stream()
                .collect(Collectors.toMap(ProductMetadataEntity::getKey, ProductMetadataEntity::getValue));
    }

    public void setForProduct(String productId, String key, String value) {
        var metadata = productMetadataRepository.findByProductIdAndKey(productId, key)
                .orElseGet(() -> {
                    var newEntity = new ProductMetadataEntity();
                    newEntity.setProductId(productId);
                    newEntity.setKey(key);
                    return newEntity;
                });
        metadata.setValue(value);
        metadata.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        productMetadataRepository.save(metadata);
    }
}
