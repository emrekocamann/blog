package com.emre.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateArticleRequest(
        @NotBlank
        String authorId,
        @NotBlank
        String title,
        @NotBlank
        String content
) {
}
