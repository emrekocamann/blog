package com.emre.blog.controller;


import com.emre.blog.dto.ArticleDto;
import com.emre.blog.dto.CreateArticleRequest;
import com.emre.blog.dto.UpdateArticleRequest;
import com.emre.blog.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleDtoById(id));
    }

    @GetMapping("/getByAuthorName/{authorName}")
    public ResponseEntity<Set<ArticleDto>> getArticleByAuthorName(@PathVariable String authorName) {
        return ResponseEntity.ok(articleService.getArticlesDtoByAuthorName(authorName));
    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<Set<ArticleDto>> getArticleByTitle(@PathVariable String title) {
        return ResponseEntity.ok(articleService.getArticleDtoByTitle(title));
    }


    @GetMapping("/getByDate")
    public ResponseEntity<Set<ArticleDto>> getArticleByDate(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate) {
        return ResponseEntity.ok(articleService.getArticlesByDateRange(startDate, endDate));
    }

    @PostMapping("/create")
    public ResponseEntity<ArticleDto> save(@Valid @RequestBody CreateArticleRequest request) {
        return ResponseEntity.ok( articleService.save(request));
    }

   // @PreAuthorize("#username? == authentication?.username or hasRole('ADMIN') ")
    @PutMapping( "/update/{id}")
    public ResponseEntity<ArticleDto> update(@PathVariable Long id,@RequestBody @Valid UpdateArticleRequest request) {
        return ResponseEntity.ok( articleService.update(id,request));
    }

  //  @PreAuthorize("#username == authentication?.principal.username or hasRole('ADMIN')")
    @DeleteMapping( "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok("Article deleted successfully");
    }
}
