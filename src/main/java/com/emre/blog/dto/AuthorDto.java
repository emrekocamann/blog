package com.emre.blog.dto;

import lombok.Builder;

@Builder
public record AuthorDto(
        Long id,
        String name,
        String username
) {

}
