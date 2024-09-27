package com.book.exchange.backend.order.dto;

import com.book.exchange.backend.entity.order.OrderType;

import java.util.List;

public record OrderRequestDto(

        OrderType orderType,

        String userSlug,

        String referralNumber,

        List<String> books
) {
}
