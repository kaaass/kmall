package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, String> {

    Optional<UserAuthEntity> findByPhone(String phone);
}
