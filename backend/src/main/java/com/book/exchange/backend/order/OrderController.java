package com.book.exchange.backend.order;

import com.book.exchange.backend.order.dto.OrderDto;
import com.book.exchange.backend.order.dto.OrderRequestDto;
import com.book.exchange.backend.order.dto.OrderSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name = "Order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Create a new order with optional referral number")
    public ResponseEntity<String> saveOrder(
            @RequestBody OrderRequestDto requestDto
    ) {
        orderService.saveOrder(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Order saved");
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Retrieve a single order by its unique slug field")
    public ResponseEntity<OrderDto> getOrderBySlug(
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(orderService.getOrderBySlug(slug));
    }

    @GetMapping("/users/{userSlug}")
    @Operation(summary = "Retrieve a list of user orders by its unique slug field")
    public ResponseEntity<List<OrderSummaryDto>> getOrdersByUserSlug(
            @PathVariable String userSlug
    ) {
        return ResponseEntity.ok(orderService.getOrdersByUserSlug(userSlug));
    }
}
