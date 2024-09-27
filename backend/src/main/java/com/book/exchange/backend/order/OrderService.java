package com.book.exchange.backend.order;

import com.book.exchange.backend.book.BookService;
import com.book.exchange.backend.entity.book.BookEntity;
import com.book.exchange.backend.entity.order.OrderEntity;
import com.book.exchange.backend.entity.order.Status;
import com.book.exchange.backend.entity.user.UserEntity;
import com.book.exchange.backend.exception.InvalidReferralNumberException;
import com.book.exchange.backend.exception.OrderNotFoundException;
import com.book.exchange.backend.order.dto.OrderDto;
import com.book.exchange.backend.order.dto.OrderRequestDto;
import com.book.exchange.backend.order.dto.OrderSummaryDto;
import com.book.exchange.backend.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final BookService bookService;

    public OrderService(
            OrderRepository orderRepository,
            UserService userService,
            BookService bookService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public void saveOrder(OrderRequestDto requestDto) {
        OrderEntity order = createOrder(requestDto);
        orderRepository.save(order);
    }

    private OrderEntity createOrder(OrderRequestDto requestDto) {
        OrderEntity order = new OrderEntity();
        order.setOrderType(requestDto.orderType());
        order.setStatus(Status.CREATED);
        order.setSlug(generateSlug(order));

        List<BookEntity> books = bookService
                .getBooksBySlugs(requestDto.books());

        order.setBooks(books);
        changeBooksAvailability(books);

        UserEntity user = userService
                .getUserBySlug(requestDto.userSlug());

        order.setUser(user);

        String referralNumber = requestDto.referralNumber();
        if (Objects.nonNull(referralNumber)) {
            if (!isValidReferralNumber(referralNumber, user)) {
                throw new InvalidReferralNumberException("Invalid referral number");
            }
            order.setReferralNumber(referralNumber);
            awardUserWithCoin(referralNumber);
        }
        return order;
    }

    private boolean isValidReferralNumber(
            String referralNumber,
            UserEntity owner
    ) {
        return !referralNumber.equals(owner.getReferralNumber()) &&
                userService.getUserByReferralNumber(referralNumber) != null;
    }

    private void awardUserWithCoin(String referralNumber) {
        int AWARD_COIN = 1;
        UserEntity user = userService.getUserByReferralNumber(referralNumber);
        user.setCoin(user.getCoin() + AWARD_COIN);
    }

    private void changeBooksAvailability(List<BookEntity> books) {
        bookService.changeBooksAvailableStatus(books);
    }

    private String generateSlug(OrderEntity order) {
        return UUID.randomUUID().toString();
    }

    public OrderDto getOrderBySlug(String slug) {
        OrderEntity order = orderRepository
                .findBySlug(slug)
                .orElseThrow(() -> new OrderNotFoundException("Order not found."));

        return OrderDto.from(order);
    }

    public List<OrderSummaryDto> getOrdersByUserSlug(String userSlug) {
        UserEntity user = userService.getUserBySlug(userSlug);

        return orderRepository
                .findAllByUserSlug(userSlug)
                .stream()
                .map(OrderSummaryDto::from)
                .toList();
    }
}
