package com.example.credit.security.service;

import com.example.credit.entities.UserEntity;
import com.example.credit.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** CustomUserDetailService */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo usRepo;

    /**
     * Loading user from database, find user details using email entered.
     * @param username getting user email as input
     * @return provides {@link SecurityUser} object
     * @throws UsernameNotFoundException 
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username entered by user is "+ username);
        Optional<UserEntity> user = usRepo.findByEmail(username);
        log.info(user.toString());
        try {
            var userEntity = user.orElseThrow();
            log.info(userEntity.toString());
            return new SecurityUser(userEntity);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not exist");
        }
    }
}