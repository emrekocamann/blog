package com.emre.blog.dto;


import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ArticleDto(
    Long id,
    String title,
    String content,
    String authorId,
    LocalDateTime creationDate,
    LocalDateTime updateDate,
    Set<CommentDto> comments
) {


}
