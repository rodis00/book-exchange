package com.book.exchange.backend.user.dto;

public record UserPasswordDto(

        String oldPassword,

        String newPassword,

        String confirmPassword
) {
}
