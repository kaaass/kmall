package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    boolean existsByRequestId(String requestId);

    Optional<OrderEntity> findByRequestId(String requestId);

    List<OrderEntity> findAllByUidOrderByCreateTimeDesc(String uid, Pageable pageable);

    Optional<OrderEntity> findFirstByCreateTimeBetweenOrderByCreateTimeDesc(Timestamp start, Timestamp end);
}
