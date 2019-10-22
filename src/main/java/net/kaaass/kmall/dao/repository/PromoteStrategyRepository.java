package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.PromoteStrategyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromoteStrategyRepository extends JpaRepository<PromoteStrategyEntity, String> {

    List<PromoteStrategyEntity> findAllByEnabledTrueOrderByOrderAscLastUpdateTimeDesc();
}
