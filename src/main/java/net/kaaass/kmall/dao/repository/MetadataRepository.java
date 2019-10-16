package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.MetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetadataRepository extends JpaRepository<MetadataEntity, String> {

    Optional<MetadataEntity> findByKey(String key);
}
