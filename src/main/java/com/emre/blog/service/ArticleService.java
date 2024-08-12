package com.emre.blog.service;



import com.emre.blog.dto.ArticleDto;
import com.emre.blog.dto.ArticleDtoConverter;
import com.emre.blog.dto.CreateArticleRequest;
import com.emre.blog.dto.UpdateArticleRequest;
import com.emre.blog.model.Article;
import com.emre.blog.model.Author;
import com.emre.blog.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//import java.time.Clock;
//import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;
    private final ArticleDtoConverter articleDtoConverter;
  //  private final Clock clock;

    public ArticleService(ArticleRepository articleRepository,
                          AuthorService authorService,
                          ModelMapper modelMapper,
                          @Lazy ArticleDtoConverter articleDtoConverter
                          //,   Clock clock
    ) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
        this.modelMapper = modelMapper;
//        this.clock = clock;
        this.articleDtoConverter = articleDtoConverter;
    }

    public ArticleDto getArticleById(Long articleId) {
        Article article=articleRepository.findById(articleId).orElseThrow();
        return articleDtoConverter.convert(article);
    }



    public ArticleDto save(CreateArticleRequest request){
       Author author=authorService.findAuthorById(request.authorId());

       Article article = Article.builder()
               .title(request.title())
               .content(request.content())
               .author(author)
               .creationDate(LocalDateTime.now())
               .build();
       articleRepository.save(article);
       return articleDtoConverter.convert(article);
    }


    public ArticleDto update(Long id, UpdateArticleRequest request){
        Article article = articleRepository.findById(id).orElseThrow();

        checkPermission(article);

        article.setTitle(request.title());
        article.setContent(request.content());
        article.setUpdateDate(LocalDateTime.now());
        articleRepository.save(article);

        return articleDtoConverter.convert(article);
    }



    public void delete(Long id){
        Article article = articleRepository.findById(id).orElseThrow();
        checkPermission(article);
        articleRepository.delete(article);
    }


    private  void checkPermission(Article article){
        if (!article.getAuthor().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))){

            throw new RuntimeException("You do not have permission to delete this article.");
        }
    }


//    private LocalDateTime getLocalDateTimeNow() {
//        Instant instant = clock.instant();
//        return LocalDateTime.ofInstant(
//                instant,
//                Clock.systemDefaultZone().getZone());
//    }
}
