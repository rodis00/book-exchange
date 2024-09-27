package com.book.exchange.backend.author.dto;

import com.book.exchange.backend.book.dto.BookSummaryDto;
import com.book.exchange.backend.entity.author.AuthorEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AuthorDto(

        String slug,

        String firstName,

        String lastName,

        String nick,

        LocalDate dateOfBirth,

        String imageUrl,

        List<BookSummaryDto> booksSummaryDto,

        LocalDateTime updatedAt,

        LocalDateTime createdAt
) {
    public static AuthorDto from(AuthorEntity authorEntity) {
        return new AuthorDto(
                authorEntity.getSlug(),
                authorEntity.getFirstName(),
                authorEntity.getLastName(),
                authorEntity.getNick(),
                authorEntity.getDateOfBirth(),
                authorEntity.getImageUrl(),
                authorEntity.getBooks()
                        .stream()
                        .map(BookSummaryDto::from)
                        .toList(),
                authorEntity.getUpdatedAt(),
                authorEntity.getCreatedAt()
        );
    }
}
