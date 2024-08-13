package com.emre.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest (

        Long articleId,
        @NotBlank
        String content
){

}
