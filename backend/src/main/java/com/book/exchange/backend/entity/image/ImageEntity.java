package com.book.exchange.backend.entity.image;

import com.book.exchange.backend.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "image_")
public class ImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_seq"
    )
    @SequenceGenerator(
            name = "image_seq",
            sequenceName = "image_seq",
            allocationSize = 25
    )
    private Long id;

    private String originalName;

    @Lob
    private byte[] data;

    private String contentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
