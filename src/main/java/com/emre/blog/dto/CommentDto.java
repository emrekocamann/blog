package com.emre.blog.dto;



import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record CommentDto(
        Long id,
        String content,
        String authorId,
        Long articleId,
        LocalDateTime creationDate
) {



}
