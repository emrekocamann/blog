package com.emre.blog.controller;

import com.emre.blog.dto.ArticleDto;
import com.emre.blog.dto.CreateArticleRequest;
import com.emre.blog.model.Article;
import com.emre.blog.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Article> save(@RequestBody CreateArticleRequest request) {
        return ResponseEntity.ok( articleService.save(request));
    }

    @PutMapping( "{id}")
    public ResponseEntity<ArticleDto> update(@PathVariable Long id,@RequestBody CreateArticleRequest request) {
        return ResponseEntity.ok( articleService.update(id,request));
    }

    @DeleteMapping( "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
