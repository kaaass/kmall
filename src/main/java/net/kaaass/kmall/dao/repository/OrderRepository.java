package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    boolean existsByRequestId(String requestId);

    List<OrderEntity> findAllByUidOrderByCreateTimeDesc(String uid, Pageable pageable);
}
