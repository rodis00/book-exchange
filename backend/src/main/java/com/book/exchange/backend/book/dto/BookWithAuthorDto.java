package com.book.exchange.backend.book.dto;

import com.book.exchange.backend.author.dto.AuthorInfoDto;
import com.book.exchange.backend.entity.book.BookEntity;
import com.book.exchange.backend.entity.book.Category;
import com.book.exchange.backend.entity.book.Condition;

import java.time.LocalDate;

public record BookWithAuthorDto(

        String slug,

        String title,

        Category category,

        LocalDate releaseDate,

        int price,

        String imageUrl,

        AuthorInfoDto authorInfoDto,

        boolean isAvailable,

        Condition condition
) {
    public static BookWithAuthorDto from(BookEntity bookEntity) {
        return new BookWithAuthorDto(
                bookEntity.getSlug(),
                bookEntity.getTitle(),
                bookEntity.getCategory(),
                bookEntity.getReleaseDate(),
                bookEntity.getPrice(),
                bookEntity.getImageUrl(),
                AuthorInfoDto.from(bookEntity.getAuthor()),
                bookEntity.isAvailable(),
                bookEntity.getCondition()
        );
    }
}
