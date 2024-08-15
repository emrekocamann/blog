package com.emre.blog.controller;


import com.emre.blog.dto.ArticleDto;
import com.emre.blog.dto.CreateArticleRequest;
import com.emre.blog.dto.UpdateArticleRequest;
import com.emre.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/v1/article")
@Tag(name = "Article API", description = "Manage articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/getById/{id}")
    @Operation(summary = "Get article by id",description = "Get article by id")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleDtoById(id));
    }

    @GetMapping("/getByAuthorName/{authorName}")
    @Operation(summary = "Get articles by author name",description = "Get articles by author name")
    public ResponseEntity<Set<ArticleDto>> getArticleByAuthorName(@PathVariable String authorName) {
        return ResponseEntity.ok(articleService.getArticlesDtoByAuthorName(authorName));
    }

    @GetMapping("/getByTitle/{title}")
    @Operation(summary = "Get articles by title",description = "Get articles by title")
    public ResponseEntity<Set<ArticleDto>> getArticleByTitle(@PathVariable String title) {
        return ResponseEntity.ok(articleService.getArticleDtoByTitle(title));
    }

//    @GetMapping("/getAll")
//    @Operation(summary = "Get all articles",description = "Get all articles")
//    public ResponseEntity<Set<ArticleDto>> getAllArticles() {
//       return ResponseEntity.ok(articleService.getAllArticlesDto());
//    }
//

    @GetMapping("/getAll")
    @Operation(summary = "Get all articles",description = "Get all articles")
    public ResponseEntity<Set<ArticleDto>> getAllArticles(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(articleService.getAllArticlesDto(page,size));
    }

    @GetMapping("/getByDate")
    @Operation(summary = "Get articles by date",description = "Get articles by date")
    public ResponseEntity<Set<ArticleDto>> getArticleByDate(@RequestParam("startDate")
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
                                                                LocalDateTime startDate,
                                                            @RequestParam("endDate")
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
                                                                LocalDateTime endDate) {
        return ResponseEntity.ok(articleService.getArticlesByDateRange(startDate, endDate));
    }

    @PostMapping("/create")
    @Operation(summary = "Create Article", description = "Create new article")
    public ResponseEntity<ArticleDto> save(@Valid @RequestBody CreateArticleRequest request) {
        return ResponseEntity.ok( articleService.save(request));
    }


    @PutMapping( "/update/{id}")
    @Operation(summary = "Update Article", description = "Update article by id")
    public ResponseEntity<ArticleDto> update(@PathVariable Long id,@RequestBody @Valid UpdateArticleRequest request) {
        return ResponseEntity.ok( articleService.update(id,request));
    }

  //  @PreAuthorize("#username == authentication?.principal.username or hasRole('ADMIN')")
    @DeleteMapping( "/delete/{id}")
    @Operation(summary = "Delete Article", description = "Delete article by id")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok("Article deleted successfully");
    }
}
