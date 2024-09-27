package com.book.exchange.backend.book.dto;

import com.book.exchange.backend.author.dto.AuthorInfoDto;
import com.book.exchange.backend.entity.book.BookEntity;
import com.book.exchange.backend.entity.book.Category;

public record BookSummaryDto(

        String slug,

        String title,

        Category category,

        int price,

        String imageUrl,

        AuthorInfoDto authorInfoDto
) {
    public static BookSummaryDto from(BookEntity book) {
        return new BookSummaryDto(
                book.getSlug(),
                book.getTitle(),
                book.getCategory(),
                book.getPrice(),
                book.getImageUrl(),
                AuthorInfoDto.from(book.getAuthor()));
    }
}
