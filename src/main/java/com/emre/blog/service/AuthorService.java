package com.emre.blog.service;

import com.emre.blog.dto.AuthorDto;
import com.emre.blog.dto.CreateUserRequest;
import com.emre.blog.model.Author;
import com.emre.blog.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements UserDetailsService {
    private final AuthorRepository authorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository authorRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    public Author findAuthorById(String id){
        return authorRepository.findById(id).orElseThrow(()->new RuntimeException("author not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return authorRepository.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found"));
    }



    public Author createAuthor(CreateUserRequest request) {
        Author user = Author.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .authorities(request.authorities())
                .isEnabled(true)
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .build();
        return authorRepository.save(user);
    }


}
