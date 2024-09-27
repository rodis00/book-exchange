package com.book.exchange.backend.book.dto;

import com.book.exchange.backend.entity.book.Category;
import com.book.exchange.backend.entity.book.Condition;

import java.time.LocalDate;

public record BookRequestDto(

        String title,

        Category category,

        String description,

        LocalDate releaseDate,

        int price,

        String authorSlug,

        boolean isAvailable,

        Condition condition
) {
}
