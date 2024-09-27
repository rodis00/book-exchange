package com.book.exchange.backend.user.dto;

import com.book.exchange.backend.entity.user.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDto(

        String slug,

        String firstName,

        String lastName,

        String nick,

        LocalDate dateOfBirth,

        String email,

        String imageUrl,

        int coin,

        String referralNumber,

        LocalDateTime updatedAt,

        LocalDateTime createdAt
) {
    public static UserDto from(UserEntity user) {
        return new UserDto(
                user.getSlug(),
                user.getFirstName(),
                user.getLastName(),
                user.getNick(),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getImageUrl(),
                user.getCoin(),
                user.getReferralNumber(),
                user.getUpdatedAt(),
                user.getCreatedAt()
        );
    }
}
