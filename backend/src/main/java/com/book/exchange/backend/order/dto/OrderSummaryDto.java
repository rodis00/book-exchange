package com.book.exchange.backend.order.dto;

import com.book.exchange.backend.entity.book.BookEntity;
import com.book.exchange.backend.entity.order.OrderEntity;
import com.book.exchange.backend.entity.order.OrderType;
import com.book.exchange.backend.entity.order.Status;

import java.util.List;

public record OrderSummaryDto(

        String slug,

        OrderType orderType,

        String referralNumber,

        Status status,

        List<String> books
) {
    public static OrderSummaryDto from(OrderEntity order) {
        return new OrderSummaryDto(
                order.getSlug(),
                order.getOrderType(),
                order.getReferralNumber(),
                order.getStatus(),
                order.getBooks()
                        .stream()
                        .map(BookEntity::getSlug)
                        .toList());
    }
}
