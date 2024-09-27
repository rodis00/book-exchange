package com.book.exchange.backend.user.dto;

import java.time.LocalDate;

public record UserRequestDto(

        String firstName,

        String lastName,

        LocalDate dateOfBirth,

        String email,

        String password
) {
}