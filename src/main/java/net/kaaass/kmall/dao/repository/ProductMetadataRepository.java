package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.dao.entity.ProductMetadataEntity;
import net.kaaass.kmall.dao.entity.ProductTemplateEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductMetadataRepository extends JpaRepository<ProductMetadataEntity, String> {

    Optional<ProductMetadataEntity> findByProductIdAndKey(String productId, String key);

    List<ProductMetadataEntity> findAllByProductId(String productId);

    List<ProductMetadataEntity> findAllByKeyAndValueLike(String key, String valueLike, Pageable pageable);

    @Query("select c from ProductMetadataEntity c join ProductEntity p on c.productId = p.id where c.key = :key and p.category.template = :template")
    List<ProductMetadataEntity> findAllByKeyAndTemplate(String key, ProductTemplateEntity template);
}
