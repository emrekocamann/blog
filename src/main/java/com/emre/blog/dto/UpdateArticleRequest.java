package com.emre.blog.dto;


import jakarta.validation.constraints.NotBlank;

public record UpdateArticleRequest(
        @NotBlank
        String title,

        @NotBlank
        String content

) {

}
