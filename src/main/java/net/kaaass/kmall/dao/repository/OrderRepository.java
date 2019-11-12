package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.OrderEntity;
import net.kaaass.kmall.service.OrderType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    boolean existsByRequestId(String requestId);

    Optional<OrderEntity> findByRequestId(String requestId);

    List<OrderEntity> findAllByTypeIsNotOrderByCreateTimeDesc(OrderType type, Pageable pageable);

    List<OrderEntity> findAllByUidAndTypeIsNotOrderByCreateTimeDesc(String uid, OrderType type, Pageable pageable);

    Optional<OrderEntity> findFirstByCreateTimeBetweenOrderByCreateTimeDesc(Timestamp start, Timestamp end);

    Optional<Integer> countAllByUidAndType(String uid, OrderType type);

    List<OrderEntity> findAllByTypeOrderByCreateTimeDesc(OrderType type, Pageable pageable);

    List<OrderEntity> findAllByUidAndTypeOrderByCreateTimeDesc(String uid, OrderType type, Pageable pageable);
}
