package com.emre.blog.dto;

import com.emre.blog.model.Author;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ArticleDto(
    String title,
    String content,
    Author author,
    LocalDateTime creationDate
) {
}
