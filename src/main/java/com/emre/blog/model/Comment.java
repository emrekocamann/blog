package com.emre.blog.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @JoinColumn(name="author_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @JoinColumn(name="article_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

}
