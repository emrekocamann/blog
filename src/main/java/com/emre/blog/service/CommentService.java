package com.emre.blog.service;

import com.emre.blog.dto.*;
import com.emre.blog.model.Article;
import com.emre.blog.model.Author;
import com.emre.blog.model.Comment;
import com.emre.blog.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;
    private final ArticleDtoConverter articleDtoConverter;
    private final CommentDtoConverter commentDtoConverter;

    public CommentService(CommentRepository commentRepository,
                          ArticleService articleService,
                          AuthorService authorService,
                          ModelMapper modelMapper,
                          ArticleDtoConverter articleDtoConverter, CommentDtoConverter commentDtoConverter) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
        this.authorService = authorService;
        this.modelMapper = modelMapper;
        this.articleDtoConverter = articleDtoConverter;
        this.commentDtoConverter = commentDtoConverter;
    }

    public CommentDto save(CommentRequest request){
        ArticleDto articleDto= articleService.getArticleById(request.articleId());
        Article article = articleDtoConverter.convert(articleDto);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author =(Author) authorService.loadUserByUsername(username);

        Comment comment=Comment.builder()
                .content(request.content())
                .article(article)
                .author(author)
                .creationDate(LocalDateTime.now())
                .build();

        return commentDtoConverter.convert(commentRepository.save(comment));
    }
}
