package com.emre.blog.dto;

import com.emre.blog.model.Article;
import com.emre.blog.service.AuthorService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ArticleDtoConverter {

    private final AuthorService authorService;
    private final CommentDtoConverter commentDtoConverter;

    public ArticleDtoConverter(AuthorService authorService, @Lazy CommentDtoConverter commentDtoConverter) {
        this.authorService = authorService;
        this.commentDtoConverter = commentDtoConverter;
    }

    public ArticleDto convert(Article from) {
        return ArticleDto.builder()
                .id(from.getId())
                .title(from.getTitle())
                .content(from.getContent())
                .authorId(from.getAuthor().getId())
                .creationDate(from.getCreationDate())
                .updateDate(from.getUpdateDate())
                .comments(Optional.ofNullable(from.getComments())
                        .map(comments -> comments.stream()
                                .map(commentDtoConverter::convert)
                                .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet()))
                .build();
    }

//    public ArticleDto convert(Article from) {
//        return ArticleDto.builder()
//                .id(from.getId())
//                .title(from.getTitle())
//                .content(from.getContent())
//                .authorId(from.getAuthor().getId())
//                .creationDate(from.getCreationDate())
//                .updateDate(from.getUpdateDate())
//                .comments(from.getComments()
//                        .stream()
//                        .map(commentDtoConverter::convert)
//                        .collect(Collectors.toSet()))
//                .build();
//    }


    public Article convert(ArticleDto from){
        return Article.builder()
                .id(from.id())
                .title(from.title())
                .content(from.content())
                .author(authorService.findAuthorById(from.authorId()))
                .creationDate(from.creationDate())
                .updateDate(from.updateDate())
                .comments(Optional.ofNullable(from.comments())
                        .map(comments -> comments.stream()
                                .map(commentDtoConverter::convert)
                                .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet()))
                .build();
    }
//        public Article convert(ArticleDto from){
//        return Article.builder()
//                .id(from.id())
//                .title(from.title())
//                .content(from.content())
//                .author(authorService.findAuthorById(from.authorId()))
//                .creationDate(from.creationDate())
//                .updateDate(from.updateDate())
//                .comments(from.comments()
//                        .stream()
//                        .map(commentDtoConverter::convert)
//                        .collect(Collectors.toSet()))
//                .build();
//    }
}
