package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.PluginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PluginRepository extends JpaRepository<PluginEntity, String> {

    Optional<PluginEntity> findFirstByFilename(String filename);

    List<PluginEntity> findAllByEnableTrue();
}
