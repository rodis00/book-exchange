package com.book.exchange.backend.order.dto;

import com.book.exchange.backend.entity.book.BookEntity;
import com.book.exchange.backend.entity.order.OrderEntity;
import com.book.exchange.backend.entity.order.OrderType;
import com.book.exchange.backend.entity.order.Status;

import java.util.List;

public record OrderDto(

        String slug,

        OrderType orderType,

        String userSlug,

        String referralNumber,

        Status status,

        List<String> books
) {
    public static OrderDto from(OrderEntity order) {
        return new OrderDto(
                order.getSlug(),
                order.getOrderType(),
                order.getUser().getSlug(),
                order.getReferralNumber(),
                order.getStatus(),
                order.getBooks()
                        .stream()
                        .map(BookEntity::getSlug)
                        .toList()
        );
    }
}
