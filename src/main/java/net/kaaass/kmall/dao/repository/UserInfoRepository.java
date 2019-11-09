package net.kaaass.kmall.dao.repository;

import net.kaaass.kmall.dao.entity.UserAuthEntity;
import net.kaaass.kmall.dao.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {

    UserInfoEntity findByAuth(UserAuthEntity auth);

    @Transactional
    void deleteAllByAuth(UserAuthEntity auth);
}
