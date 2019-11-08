package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.CategoryEntity;
import net.kaaass.kmall.dao.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    List<ProductEntity> findAllByCategoryIn(Collection<CategoryEntity> category, Pageable pageable);

    List<ProductEntity> findAllByIndexOrderGreaterThanEqualOrderByIndexOrderAscCreateTimeDesc(int indexOrder);

    List<ProductEntity> findAllByNameIsLikeOrderByIndexOrderAscCreateTimeDesc(String name, Pageable pageable);

    List<ProductEntity> findAllByStartSellTimeGreaterThanOrderByIndexOrderAscCreateTimeDesc(Timestamp startSellTime);
}
