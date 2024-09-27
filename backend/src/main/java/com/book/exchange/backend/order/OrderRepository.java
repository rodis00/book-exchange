package com.book.exchange.backend.order;

import com.book.exchange.backend.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserSlug(String userSlug);

    Optional<OrderEntity> findBySlug(String slug);
}
