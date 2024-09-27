package com.book.exchange.backend.entity.book;

import com.book.exchange.backend.entity.BaseEntity;
import com.book.exchange.backend.entity.author.AuthorEntity;
import com.book.exchange.backend.entity.order.OrderEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_")
public class BookEntity extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_seq"
    )
    @SequenceGenerator(
            name = "book_seq",
            sequenceName = "book_seq",
            allocationSize = 25
    )
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    private LocalDate releaseDate;

    private int price;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    private String imageUrl;

    private boolean isAvailable;

    @ManyToMany(mappedBy = "books")
    private List<OrderEntity> orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Condition condition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
