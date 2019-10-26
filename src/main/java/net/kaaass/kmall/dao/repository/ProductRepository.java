package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.CategoryEntity;
import net.kaaass.kmall.dao.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    List<ProductEntity> findAllByCategoryIn(Collection<CategoryEntity> category, Pageable pageable);

    List<ProductEntity> findAllByIndexOrderGreaterThanEqualOrderByIndexOrderDescCreateTimeDesc(int indexOrder);

    List<ProductEntity> findAllByNameIsLikeOrderByIndexOrderDescCreateTimeDesc(String name, Pageable pageable);
}
