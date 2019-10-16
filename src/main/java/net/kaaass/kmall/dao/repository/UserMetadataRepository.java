package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.UserMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMetadataRepository extends JpaRepository<UserMetadataEntity, String> {

    Optional<UserMetadataEntity> findByUidAndKey(String uid, String key);

    List<UserMetadataEntity> findAllByUid(String uid);
}
