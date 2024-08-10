package com.emre.blog.controller;

import com.emre.blog.dto.AuthRequest;
import com.emre.blog.dto.CreateUserRequest;
import com.emre.blog.model.Author;
import com.emre.blog.service.JwtService;
import com.emre.blog.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final AuthorService authorService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(AuthorService authorService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authorService = authorService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Author> addUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(authorService.createAuthor(request));
    }


    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(request.username());
        }
        log.info("Invalid username"+request.username());
        throw new UsernameNotFoundException("Invalid Credentials");
    }
}
