package net.kaaass.kmall.security;

import net.kaaass.kmall.dao.entity.UserAuthEntity;
import net.kaaass.kmall.dao.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAuthRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAuthEntity> result = repository.findById(username);
        UserAuthEntity authEntity = result
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with id '%s'.", username)));
        return JwtUserFactory.create(authEntity);
    }
}
