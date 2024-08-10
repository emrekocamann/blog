package com.emre.blog.dto;

import lombok.*;

@Data

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AuthorDto{
    private String id;
    private String name;
    private String username;
}
