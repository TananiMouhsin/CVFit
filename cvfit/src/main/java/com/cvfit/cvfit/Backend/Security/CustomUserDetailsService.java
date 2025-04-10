package com.cvfit.cvfit.Backend.Security;


import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserEmail(email);

        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException(" User not found with email: " + email)
        );

        System.out.println(" User found: " + user.getUserEmail()); // Debugging log

        return new org.springframework.security.core.userdetails.User(
                user.getUserEmail(),  // Use email as the username
                user.getPassword(),   // Make sure password is hashed
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Assign default role
        );
    }
}
