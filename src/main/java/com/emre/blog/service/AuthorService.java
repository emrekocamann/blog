package com.emre.blog.service;


import com.emre.blog.dto.AuthRequest;
import com.emre.blog.dto.AuthorDto;
import com.emre.blog.dto.AuthorDtoConverter;
import com.emre.blog.dto.CreateUserRequest;
import com.emre.blog.exception.UserNotFoundException;
import com.emre.blog.exception.UsernameAlreadyExistsException;
import com.emre.blog.model.Author;
import com.emre.blog.repository.AuthorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthorService implements UserDetailsService {
    private final AuthorRepository authorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthorDtoConverter authorDtoConverter;

    public AuthorService(AuthorRepository authorRepository,
                         BCryptPasswordEncoder passwordEncoder,
                         JwtService jwtService,
                         AuthorDtoConverter authorDtoConverter) {
        this.authorRepository = authorRepository;
   //     this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authorDtoConverter = authorDtoConverter;
    }


    public Author findAuthorById(String id){
        return authorRepository.findById(id)
                .orElseThrow(()->
                        new UserNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        return authorRepository.findByUsername(username)
                .orElseThrow(()->
                        new UserNotFoundException("User not found"));
    }



    public AuthorDto save(CreateUserRequest request) {
        Author existingAuthor = findAuthorByUsername(request.username());
        if (existingAuthor != null){
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        Author author = Author.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .authorities(request.authorities())
                .isEnabled(true)
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .build();
        authorRepository.save(author);
        return authorDtoConverter.convert(author);
    }

    public String generateToken( AuthRequest request){
        loadUserByUsername(request.username());
        return jwtService.generateToken(request.username());
    }

    private Author findAuthorByUsername(String username){
       return authorRepository.findByUsername(username).orElse(null);
    }
}
