package com.book.exchange.backend.author;

import com.book.exchange.backend.entity.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findBySlug(String slug);

    boolean existsBySlug(String slug);

    void deleteBySlug(String slug);
}
