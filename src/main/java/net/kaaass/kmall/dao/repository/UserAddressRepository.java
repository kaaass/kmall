package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, String> {

    List<UserAddressEntity> findAllByUid(String uid);

    Optional<UserAddressEntity> findByUidAndDefaultAddressTrue(String uid);
}
