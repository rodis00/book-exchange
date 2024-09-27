package com.book.exchange.backend.author.dto;

import java.time.LocalDate;

public record AuthorRequestDto(

        String firstName,

        String lastName,

        String nick,

        LocalDate dateOfBirth
) {
}
