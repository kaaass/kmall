package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
}
