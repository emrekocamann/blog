package com.emre.blog.dto;

import lombok.Builder;

@Builder
public record AuthorDto(
        String id,
        String name,
        String username
) {

}
