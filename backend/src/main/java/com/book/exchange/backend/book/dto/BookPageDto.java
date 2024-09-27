package com.book.exchange.backend.book.dto;

import com.book.exchange.backend.entity.book.BookEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public record BookPageDto(

        List<BookWithAuthorDto> books,

        int pageNumber,

        int pageSize,

        int numberOfElements,

        long totalElements,

        int totalPages,

        boolean isFirst,

        boolean isLast
) {
    public static BookPageDto from(
            List<BookWithAuthorDto> books,
            Page<BookEntity> bookPage
    ) {
        return new BookPageDto(
                books,
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getNumberOfElements(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages(),
                bookPage.isFirst(),
                bookPage.isLast()
        );
    }
}
