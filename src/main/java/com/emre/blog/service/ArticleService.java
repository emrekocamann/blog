package com.emre.blog.service;

import com.emre.blog.dto.ArticleDto;
import com.emre.blog.dto.AuthorDto;
import com.emre.blog.dto.CreateArticleRequest;
import com.emre.blog.model.Article;
import com.emre.blog.model.Author;
import com.emre.blog.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

//import java.time.Clock;
//import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;
  //  private final Clock clock;

    public ArticleService(ArticleRepository articleRepository,
                          AuthorService authorService,
                          ModelMapper modelMapper
            //,   Clock clock
    ) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
        this.modelMapper = modelMapper;
//        this.clock = clock;
    }


    public Article save(CreateArticleRequest request){
       Author author=authorService.findAuthorById(request.authorId());

       Article article = Article.builder()
               .title(request.title())
               .content(request.content())
               .author(author)
               .creationDate(LocalDateTime.now())
               .build();
       articleRepository.save(article);
       return article;
    }


    public ArticleDto update(Long id, CreateArticleRequest request){
        Article article = articleRepository.findById(id).orElseThrow();
        article.setTitle(request.title());
        article.setContent(request.content());
        article.setCreationDate(LocalDateTime.now());
        articleRepository.save(article);
        return modelMapper.map(article, ArticleDto.class);
    }


    public void delete(Long id){
        Article article = articleRepository.findById(id).orElseThrow();
        articleRepository.delete(article);
    }

//    private LocalDateTime getLocalDateTimeNow() {
//        Instant instant = clock.instant();
//        return LocalDateTime.ofInstant(
//                instant,
//                Clock.systemDefaultZone().getZone());
//    }
}
