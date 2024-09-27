package com.book.exchange.backend.user.dto;

import java.time.LocalDate;

public record UserUpdateRequestDto(

        String firstName,

        String lastName,

        String nick,

        LocalDate dateOfBirth,

        String email
) {
}
