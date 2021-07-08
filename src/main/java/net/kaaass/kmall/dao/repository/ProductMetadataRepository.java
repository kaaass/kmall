package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.dao.entity.ProductMetadataEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductMetadataRepository extends JpaRepository<ProductMetadataEntity, String> {

    Optional<ProductMetadataEntity> findByProductIdAndKey(String productId, String key);

    List<ProductMetadataEntity> findAllByProductId(String productId);

    List<ProductMetadataEntity> findAllByKeyAndValueLike(String key, String valueLike, Pageable pageable);
}
