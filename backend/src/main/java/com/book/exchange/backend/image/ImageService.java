package com.book.exchange.backend.image;

import com.book.exchange.backend.entity.image.ImageEntity;
import com.book.exchange.backend.entity.user.UserEntity;
import com.book.exchange.backend.exception.ImageNotFoundException;
import com.book.exchange.backend.exception.InvalidImageException;
import com.book.exchange.backend.image.dto.ImageSummaryDto;
import com.book.exchange.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final UserRepository userRepository;
    @Value("${app.image-url}")
    private String imageUrl;

    private final ImageRepository imageRepository;

    public ImageService(
            final ImageRepository imageRepository,
            UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public ImageEntity saveImage(MultipartFile image) {
        try {
            ImageEntity imageEntity = createImage(image);
            return imageRepository.save(imageEntity);
        } catch (Exception e) {
            throw new InvalidImageException(e.getMessage());
        }
    }

    private ImageEntity createImage(MultipartFile image) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setOriginalName(image.getOriginalFilename());
        imageEntity.setData(image.getBytes());
        imageEntity.setContentType(image.getContentType());
        imageEntity.setSlug(generateSlug(imageEntity));
        return imageEntity;
    }

    public String generateSlug(ImageEntity imageEntity) {
        String originalName = imageEntity.getOriginalName().toLowerCase();
        return originalName.replaceAll("\\s+", "-");
    }

    public ImageEntity getImageBySlug(String slug) {
        return imageRepository
                .findBySlug(slug)
                .orElseThrow(() -> new ImageNotFoundException("Image not found."));
    }

    @Transactional
    public ImageSummaryDto getImageDtoBySlug(String slug) {
        ImageEntity image = getImageBySlug(slug);
        return new ImageSummaryDto(
                image.getData(),
                image.getContentType()
        );
    }

    @Transactional
    public void deleteImageBySlug(String slug) {
        if (!imageRepository.existsBySlug(slug))
            throw new ImageNotFoundException("Image not found.");
        removeUserImageUrl(slug);
        imageRepository.deleteBySlug(slug);
    }

    public void removeUserImageUrl(String slug) {
        ImageEntity image = getImageBySlug(slug);
        String url = imageUrl + image.getSlug();
        UserEntity user = userRepository.findByImageUrl(url);
        if (user != null) {
            user.setImageUrl(null);
            userRepository.save(user);
        }
    }
}
