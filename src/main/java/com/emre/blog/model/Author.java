package com.emre.blog.model;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="author")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Author implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private String id;
    private String name;
    private String username;
    private String password;


    private boolean accountNonExpired;
    private boolean isEnabled;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Article> articles;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Comment> comments;
}
