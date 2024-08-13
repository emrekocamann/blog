package com.emre.blog.dto;

import com.emre.blog.model.Comment;
import com.emre.blog.service.ArticleService;
import com.emre.blog.service.AuthorService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter {

    private final AuthorService authorService;
    private final ArticleService articleService;
    private final ArticleDtoConverter articleDtoConverter;

    public CommentDtoConverter(AuthorService authorService, @Lazy ArticleService articleService, ArticleDtoConverter articleDtoConverter) {

        this.authorService = authorService;
        this.articleService = articleService;
        this.articleDtoConverter = articleDtoConverter;
    }

    public CommentDto convert(Comment from) {
        return CommentDto.builder()
                .id(from.getId())
                .content(from.getContent())
                .authorId(from.getAuthor().getId())
                .authorUsername(from.getAuthor().getUsername())
                .articleId(from.getArticle().getId())
                .creationDate(from.getCreationDate())
                .build();
    }

    public Comment convert(CommentDto from){
        return Comment.builder()
                .id(from.id())
                .content(from.content())
                .author(authorService.findAuthorById(from.authorId()))
                .article(articleDtoConverter.convert(articleService.getArticleDtoById(from.articleId())))
                .creationDate(from.creationDate())
                .build();
    }
}
