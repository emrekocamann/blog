package com.emre.blog.controller;


import com.emre.blog.dto.ArticleDto;
import com.emre.blog.dto.CreateArticleRequest;
import com.emre.blog.dto.UpdateArticleRequest;
import com.emre.blog.model.Article;
import com.emre.blog.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/article")
public class ArticleController {

    private final ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @PostMapping("create")
    public ResponseEntity<ArticleDto> save(@RequestBody CreateArticleRequest request) {
        return ResponseEntity.ok( articleService.save(request));
    }
   // @PreAuthorize("#username? == authentication?.username or hasRole('ADMIN') ")
    @PutMapping( "/update/{id}")
    public ResponseEntity<ArticleDto> update(@PathVariable Long id,@RequestBody UpdateArticleRequest request) {
        return ResponseEntity.ok( articleService.update(id,request));
    }

  //  @PreAuthorize("#username == authentication?.principal.username or hasRole('ADMIN')")
    @DeleteMapping( "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
