package com.emre.blog.dto;

public record CommentRequest (
        Long articleId,
        String content
){

}
