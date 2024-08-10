package com.emre.blog.dto;

public record AuthRequest(
        String username,
        String password
) {
}
