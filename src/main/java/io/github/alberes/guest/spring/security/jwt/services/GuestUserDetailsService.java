package io.github.alberes.guest.spring.security.jwt.services;

import io.github.alberes.guest.spring.security.jwt.domains.User;
import io.github.alberes.guest.spring.security.jwt.domains.UserPrincipal;
import io.github.alberes.guest.spring.security.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GuestUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository respository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.respository.findByUsername(username);
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
