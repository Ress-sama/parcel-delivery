package com.guavapay.userservice.service;

import com.guavapay.userservice.repository.UserRepository;
import com.guavapay.userservice.entity.User;
import com.guavapay.userservice.model.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(username));
        Set<SimpleGrantedAuthority> roles = new HashSet<>();
        return new UserDetails(user.getUsername(), user.getPassword(), roles, user.getFullName());
    }

}

