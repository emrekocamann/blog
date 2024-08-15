package com.emre.blog.service;

import com.emre.blog.dto.ArticleDto;
import com.emre.blog.dto.ArticleDtoConverter;
import com.emre.blog.dto.CreateArticleRequest;
import com.emre.blog.dto.UpdateArticleRequest;
import com.emre.blog.exception.ArticleAlreadyExistsException;
import com.emre.blog.exception.ArticleNotFountException;
import com.emre.blog.exception.PermissionException;
import com.emre.blog.model.Article;
import com.emre.blog.model.Author;
import com.emre.blog.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AuthorService authorService;
 //   private final ModelMapper modelMapper;
    private final ArticleDtoConverter articleDtoConverter;
    private final Clock clock;

    public ArticleService(ArticleRepository articleRepository,
                          AuthorService authorService,
                          ModelMapper modelMapper,
                          @Lazy ArticleDtoConverter articleDtoConverter
                          ,   Clock clock
    ) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
  //      this.modelMapper = modelMapper;
        this.clock = clock;
        this.articleDtoConverter = articleDtoConverter;
    }

    public ArticleDto getArticleDtoById(Long articleId) {
        Article article=articleRepository.findById(articleId).orElseThrow(
                () -> new ArticleNotFountException("Article not found by id: "+articleId));
        return articleDtoConverter.convert(article);
    }

    public Set<ArticleDto> getArticleDtoByTitle(String title){
        Set<Article> articles = getArticleByTitle(title);

        if(articles==null || articles.isEmpty()){
            throw new ArticleNotFountException("Article not found by title: "+title);
        }
        return articles.stream().map(articleDtoConverter::convert).collect(Collectors.toSet());
    }

    public Set<ArticleDto> getArticlesDtoByAuthorName(String authorName){
        Set<Article>  articles = articleRepository.findByAuthorName(authorName).orElse(null);

        if(articles==null || articles.isEmpty()){
            throw new ArticleNotFountException("Article not found by author name: "+authorName);
        }

        return articles.stream().map(articleDtoConverter::convert).collect(Collectors.toSet());
    }


    public Set<ArticleDto> getArticlesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Set<Article> articles = articleRepository.findByCreationDateBetween(startDate, endDate).orElse(null);

        if (articles==null || articles.isEmpty()) {
            throw new ArticleNotFountException("Article not found by date range: " + startDate + " - " + endDate);
        }
        return articles.stream().map(articleDtoConverter::convert).collect(Collectors.toSet());
    }


    public Set<ArticleDto> getAllArticlesDto(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.stream().map(articleDtoConverter::convert).collect(Collectors.toSet());
    }


    public ArticleDto save(CreateArticleRequest request){
       Author author=authorService.findAuthorById(request.authorId());
       Set<Article> existingArticle= getArticleByTitle(request.title());

       if(existingArticle!=null && !(existingArticle.isEmpty())){
           throw new ArticleAlreadyExistsException("Article already exists");
       }

       Article article = Article.builder()
               .title(request.title())
               .content(request.content())
               .author(author)
               .creationDate(getLocalDateTimeNow())
               .build();
       articleRepository.save(article);
       return articleDtoConverter.convert(article);
    }


    public ArticleDto update(Long id, UpdateArticleRequest request){
        Article article = articleRepository.findById(id).orElseThrow(
                ()-> new ArticleNotFountException("Article not found by id: "+id));

        checkPermission(article);

        article.setTitle(request.title());
        article.setContent(request.content());
        article.setUpdateDate(getLocalDateTimeNow());
        articleRepository.save(article);

        return articleDtoConverter.convert(article);
    }

    public void delete(Long id){
        Article article = getArticleById(id);
        checkPermission(article);
        articleRepository.delete(article);
    }

    protected Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(
                ()-> new ArticleNotFountException("Article not found by id: "+id));
    }


    private Set<Article> getArticleByTitle(String title) {
        return articleRepository.findByTitle(title).orElse(null);
    }

    private void checkPermission(Article article){
        if (!article.getAuthor().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))){

            throw new PermissionException("You do not have permission for this action.");
        }
    }



    private LocalDateTime getLocalDateTimeNow() {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(
                instant,
                Clock.systemDefaultZone().getZone());
    }
}
