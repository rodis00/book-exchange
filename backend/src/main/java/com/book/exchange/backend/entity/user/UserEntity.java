package com.book.exchange.backend.entity.user;

import com.book.exchange.backend.entity.Person;
import com.book.exchange.backend.entity.order.OrderEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_")
public class UserEntity extends Person {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq"
    )
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 25
    )
    private Long id;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    @Column(nullable = false)
    private String password;

    private String imageUrl;

    private int coin;

    @Column(
            unique = true,
            nullable = false
    )
    private String referralNumber;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getReferralNumber() {
        return referralNumber;
    }

    public void setReferralNumber(String referralNumber) {
        this.referralNumber = referralNumber;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
