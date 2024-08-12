package com.emre.blog.dto;

import com.emre.blog.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateUserRequest(
        @NotBlank
        String name,

        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotNull
        Set<Role> authorities
) {
}