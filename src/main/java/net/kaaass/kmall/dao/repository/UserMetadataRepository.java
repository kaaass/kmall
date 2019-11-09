package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.UserMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserMetadataRepository extends JpaRepository<UserMetadataEntity, String> {

    Optional<UserMetadataEntity> findByUidAndKey(String uid, String key);

    List<UserMetadataEntity> findAllByUid(String uid);

    @Transactional
    void deleteAllByUid(String uid);
}
