package com.book.exchange.backend.entity.author;

import com.book.exchange.backend.entity.Person;
import com.book.exchange.backend.entity.book.BookEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author_")
public class AuthorEntity extends Person {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_seq"
    )
    @SequenceGenerator(
            name = "author_seq",
            sequenceName = "author_seq",
            allocationSize = 25
    )
    private Long id;

    private String imageUrl;

    @OneToMany(mappedBy = "author")
    private List<BookEntity> books = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
