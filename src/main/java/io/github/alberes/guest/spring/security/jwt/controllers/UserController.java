package io.github.alberes.guest.spring.security.jwt.controllers;

import io.github.alberes.guest.spring.security.jwt.controllers.dto.TokenDto;
import io.github.alberes.guest.spring.security.jwt.controllers.dto.UserDto;
import io.github.alberes.guest.spring.security.jwt.domains.User;
import io.github.alberes.guest.spring.security.jwt.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController implements GenericController{

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserDto dto){
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        User register = this.service.register(user);
        return ResponseEntity.created(this.createURI("/{id}", register.getId().toString()))
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDto dto){
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        TokenDto token = this.service.verify(user);
        return ResponseEntity.ok(token);
    }

}