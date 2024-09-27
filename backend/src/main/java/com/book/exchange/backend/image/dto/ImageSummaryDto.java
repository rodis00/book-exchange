package com.book.exchange.backend.image.dto;

public record ImageSummaryDto(

        byte[] data,

        String contentType
) {
}
