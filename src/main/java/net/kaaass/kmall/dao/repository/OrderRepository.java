package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.OrderEntity;
import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.service.OrderType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    boolean existsByRequestId(String requestId);

    Optional<OrderEntity> findByRequestId(String requestId);

    @Query("SELECT distinct(c) FROM OrderItemEntity u JOIN u.order c where u.product = ?1 order by c.createTime desc")
    List<OrderEntity> findAllByProduct(ProductEntity productEntity, Pageable page);

    List<OrderEntity> findAllByTypeIsNotOrderByCreateTimeDesc(OrderType type, Pageable pageable);

    List<OrderEntity> findAllByUidAndTypeIsNotOrderByCreateTimeDesc(String uid, OrderType type, Pageable pageable);

    Optional<OrderEntity> findFirstByCreateTimeBetweenOrderByCreateTimeDesc(Timestamp start, Timestamp end);

    Optional<Integer> countAllByUidAndType(String uid, OrderType type);

    List<OrderEntity> findAllByTypeOrderByCreateTimeDesc(OrderType type, Pageable pageable);

    List<OrderEntity> findAllByUidAndTypeOrderByCreateTimeDesc(String uid, OrderType type, Pageable pageable);
}
