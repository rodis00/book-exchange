package com.book.exchange.backend.book;

import com.book.exchange.backend.entity.book.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("select b from BookEntity b where b.isAvailable = :isAvailable")
    Page<BookEntity> findByAvailable(boolean isAvailable, Pageable pageable);

    Optional<BookEntity> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
