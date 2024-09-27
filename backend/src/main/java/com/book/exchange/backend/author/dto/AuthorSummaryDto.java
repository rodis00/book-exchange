package com.book.exchange.backend.author.dto;

import com.book.exchange.backend.entity.author.AuthorEntity;

public record AuthorSummaryDto(

        String slug,

        String firstName,

        String lastName,

        String nick
) {
    public static AuthorSummaryDto from(AuthorEntity author) {
        return new AuthorSummaryDto(
                author.getSlug(),
                author.getFirstName(),
                author.getLastName(),
                author.getNick());
    }
}
