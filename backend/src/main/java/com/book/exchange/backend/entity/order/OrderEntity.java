package com.book.exchange.backend.entity.order;

import com.book.exchange.backend.entity.BaseEntity;
import com.book.exchange.backend.entity.book.BookEntity;
import com.book.exchange.backend.entity.user.UserEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_")
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_seq"
    )
    @SequenceGenerator(
            name = "order_seq",
            sequenceName = "order_seq",
            allocationSize = 25
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @ManyToMany
    @JoinTable(
            name = "order_book_",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<BookEntity> books = new ArrayList<>();

    private String referralNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public String getReferralNumber() {
        return referralNumber;
    }

    public void setReferralNumber(String referralNumber) {
        this.referralNumber = referralNumber;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
