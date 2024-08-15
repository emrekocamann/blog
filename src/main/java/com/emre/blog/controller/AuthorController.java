package com.emre.blog.controller;

import com.emre.blog.dto.AuthRequest;
import com.emre.blog.dto.AuthorDto;
import com.emre.blog.dto.CreateUserRequest;
import com.emre.blog.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "Author API", description = "Manage author operations")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @Operation(summary = "Get author", description = "Get author by username")
    public ResponseEntity<AuthorDto> getAuthor(@RequestParam String username){
        return ResponseEntity.ok(authorService.findAuthorDtoByUsername(username));
    }


    @PostMapping("/register")
    @Operation(summary = "Create Author", description = "Create new author")
    public ResponseEntity<AuthorDto> addAuthor(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(authorService.save(request));
    }


    @PostMapping("/login")
    @Operation(summary = "Login Author", description = "Login with username and password")
    public ResponseEntity<String> generateToken(@Valid @RequestBody AuthRequest request){
        return ResponseEntity.ok(authorService.generateToken(request));
    }

    @PutMapping("/update/{id}")
    @Operation(summary="Update author",description = "Update author by id")
    public ResponseEntity<AuthorDto> update(@PathVariable Long id, @RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(authorService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete author", description = "Delete author by id")
    public ResponseEntity<String> delete(@PathVariable Long id){
        authorService.delete(id);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
