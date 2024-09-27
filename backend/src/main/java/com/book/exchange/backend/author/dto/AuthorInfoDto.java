package com.book.exchange.backend.author.dto;

import com.book.exchange.backend.entity.author.AuthorEntity;

public record AuthorInfoDto(

        String firstName,

        String lastName,

        String nick
) {
    public static AuthorInfoDto from(AuthorEntity author) {
        return new AuthorInfoDto(
                author.getFirstName(),
                author.getLastName(),
                author.getNick());
    }
}
