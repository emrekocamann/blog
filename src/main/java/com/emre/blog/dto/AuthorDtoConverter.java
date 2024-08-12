package com.emre.blog.dto;

import com.emre.blog.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorDtoConverter {

    public AuthorDto convert(Author from) {
        return AuthorDto.builder()
                .id(from.getId())
                .name(from.getName())
                .username(from.getUsername())
                .build();
    }
}
