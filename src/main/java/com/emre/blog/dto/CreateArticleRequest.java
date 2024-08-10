package com.emre.blog.dto;

public record CreateArticleRequest(
        String authorId,
        String title,
        String content
) {
}
