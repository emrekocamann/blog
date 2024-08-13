package com.emre.blog.controller;

import com.emre.blog.dto.AuthRequest;
import com.emre.blog.dto.AuthorDto;
import com.emre.blog.dto.CreateUserRequest;
import com.emre.blog.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthorDto> addAuthor(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(authorService.save(request));
    }


    @PostMapping("/login")
    public ResponseEntity<String> generateToken(@Valid @RequestBody AuthRequest request){
        return ResponseEntity.ok(authorService.generateToken(request));
    }
}
