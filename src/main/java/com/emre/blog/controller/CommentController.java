package com.emre.blog.controller;

import com.emre.blog.dto.CommentDto;
import com.emre.blog.dto.CommentRequest;
import com.emre.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/article/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody @Valid CommentRequest request) {
        return ResponseEntity.ok(commentService.save(request));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        commentService.delete(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }





}
