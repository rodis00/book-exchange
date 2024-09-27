package com.book.exchange.backend.book.dto;

import com.book.exchange.backend.author.dto.AuthorSummaryDto;
import com.book.exchange.backend.entity.book.BookEntity;
import com.book.exchange.backend.entity.book.Category;
import com.book.exchange.backend.entity.book.Condition;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookDto(

        String slug,

        String title,

        Category category,

        String description,

        LocalDate releaseDate,

        int price,

        String imageUrl,

        AuthorSummaryDto authorSummaryDto,

        boolean isAvailable,

        Condition condition,

        LocalDateTime updatedAt,

        LocalDateTime createdAt
) {
    public static BookDto from(BookEntity bookEntity) {
        return new BookDto(
                bookEntity.getSlug(),
                bookEntity.getTitle(),
                bookEntity.getCategory(),
                bookEntity.getDescription(),
                bookEntity.getReleaseDate(),
                bookEntity.getPrice(),
                bookEntity.getImageUrl(),
                AuthorSummaryDto.from(bookEntity.getAuthor()),
                bookEntity.isAvailable(),
                bookEntity.getCondition(),
                bookEntity.getUpdatedAt(),
                bookEntity.getCreatedAt());
    }
}
