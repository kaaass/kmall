package net.kaaass.kmall.security;

import net.kaaass.kmall.dao.entity.UserAuthEntity;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(UserAuthEntity authEntity) {
        return new JwtUser(
                authEntity.getId(),
                authEntity.getPhone(),
                authEntity.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(authEntity.getRoles()),
                authEntity.isEnable(),
                authEntity.isValidate()
        );
    }
}
