package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.OrderItemEntity;
import net.kaaass.kmall.dao.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, String> {

    @Query("select sum(e.count) from OrderItemEntity e where e.product = ?1 and e.createTime >= ?2 and e.createTime < ?3")
    Optional<Integer> sumCountByIdBetween(ProductEntity productEntity, Timestamp st, Timestamp ed);
}
