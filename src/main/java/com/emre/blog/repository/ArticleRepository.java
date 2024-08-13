package com.emre.blog.repository;

import com.emre.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    Optional<Set<Article>> findByTitle(String title);

    Optional<Set<Article>> findByAuthorName(String name);

    Optional<Set<Article>> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
