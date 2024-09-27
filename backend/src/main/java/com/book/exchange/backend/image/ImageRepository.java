package com.book.exchange.backend.image;

import com.book.exchange.backend.entity.image.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Optional<ImageEntity> findBySlug(String slug);

    void deleteBySlug(String slug);

    boolean existsBySlug(String slug);
}
