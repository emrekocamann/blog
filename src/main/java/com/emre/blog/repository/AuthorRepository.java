package com.emre.blog.repository;

import com.emre.blog.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,String> {
    Optional<Author> findByUsername(String username);
}
