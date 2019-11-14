package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.CartEntity;
import net.kaaass.kmall.dao.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, String> {

    List<CartEntity> findAllByUidOrderByCreateTimeDesc(String uid, Pageable pageable);

    Optional<CartEntity> findByProductAndUid(ProductEntity product, String uid);
}
