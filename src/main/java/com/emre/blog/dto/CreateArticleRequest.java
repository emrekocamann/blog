package com.emre.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateArticleRequest(
        Long authorId,
        @NotBlank
        String title,
        @NotBlank
        String content
) {
}
