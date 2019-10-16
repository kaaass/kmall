package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.MediaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<MediaEntity, String> {
    List<MediaEntity> findAllByOrderByUploadTimeDesc(Pageable pageable);
}
