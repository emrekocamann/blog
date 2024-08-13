package com.emre.blog.service;

import com.emre.blog.dto.*;
import com.emre.blog.exception.CommentNotFoundException;
import com.emre.blog.exception.PermissionException;
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
    private final CommentDtoConverter commentDtoConverter;

    public CommentService(CommentRepository commentRepository,
                          ArticleService articleService,
                          AuthorService authorService,
                          CommentDtoConverter commentDtoConverter) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
        this.authorService = authorService;
        this.commentDtoConverter = commentDtoConverter;
    }

    public CommentDto save(CommentRequest request) {
        Article article = articleService.getArticleById(request.articleId());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorService.findAuthorByUsername(username);

        Comment comment = Comment.builder()
                .content(request.content())
                .article(article)
                .author(author)
                .creationDate(LocalDateTime.now())
                .build();

        return commentDtoConverter.convert(commentRepository.save(comment));
    }

    public void delete(Long commentId) {
        Comment comment = getCommentById(commentId);
        checkPermission(comment);
        commentRepository.delete(comment);
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("Comment not found by id: " + commentId));
    }

    private void checkPermission(Comment comment) {
        if (!comment.getAuthor().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            throw new PermissionException("You do not have permission to delete this comment.");

        }
    }

}