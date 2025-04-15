package io.github.alberes.guest.spring.security.jwt.services;

import io.github.alberes.guest.spring.security.jwt.controllers.dto.TokenDto;
import io.github.alberes.guest.spring.security.jwt.repositories.UserRepository;
import io.github.alberes.guest.spring.security.jwt.domains.User;
import io.github.alberes.guest.spring.security.jwt.services.exception.AuthorizationException;
import io.github.alberes.guest.spring.security.jwt.services.exception.DuplicateRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register(User user){
        User userDB = this.findByUsername(user.getUsername());
        if(userDB != null){
            throw new DuplicateRecordException("Registration with " + user.getUsername() + " has already been registered!");
        }
        user.setPassword(this.encoder.encode(user.getPassword()));
        return this.repository.save(user);
    }

    public TokenDto verify(User user){
        Authentication authentication =
                this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return this.jwtService.generateToken(user.getUsername());
        }else{
            throw new AuthorizationException("Authorization failure");
        }
    }

    private User findByUsername(String username){
        return this.repository.findByUsername(username);
    }
}