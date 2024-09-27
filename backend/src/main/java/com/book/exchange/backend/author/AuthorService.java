package com.book.exchange.backend.author;

import com.book.exchange.backend.author.dto.AuthorDto;
import com.book.exchange.backend.author.dto.AuthorRequestDto;
import com.book.exchange.backend.entity.author.AuthorEntity;
import com.book.exchange.backend.entity.image.ImageEntity;
import com.book.exchange.backend.exception.AuthorNotFoundException;
import com.book.exchange.backend.exception.InvalidImageException;
import com.book.exchange.backend.image.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ImageService imageService;

    @Value("${app.image-url}")
    private String imageUrl;

    public AuthorService(
            AuthorRepository authorRepository,
            ImageService imageService) {
        this.authorRepository = authorRepository;
        this.imageService = imageService;
    }

    public void saveAuthor(
            AuthorRequestDto requestDto,
            MultipartFile file
    ) {
        try {
            AuthorEntity authorEntity = createAuthor(requestDto, file);
            authorRepository.save(authorEntity);
        } catch (Exception e) {
            throw new InvalidImageException(e.getMessage());
        }
    }

    private AuthorEntity createAuthor(
            AuthorRequestDto requestDto,
            MultipartFile file
    ) {
        AuthorEntity author = new AuthorEntity();
        author.setFirstName(requestDto.firstName());
        author.setLastName(requestDto.lastName());
        author.setNick(requestDto.nick());
        author.setDateOfBirth(requestDto.dateOfBirth());
        author.setSlug(generateSlug(author));

        ImageEntity image = createImage(file);
        if (Objects.nonNull(image)) {
            author.setImageUrl(imageUrl + image.getSlug());
        }
        return author;
    }

    private String generateSlug(AuthorEntity authorEntity) {
        String firstName = authorEntity.getFirstName().toLowerCase();
        String lastName = authorEntity.getLastName().toLowerCase();
        return firstName.charAt(0) + "-" + lastName;
    }

    private ImageEntity createImage(MultipartFile file) {
        if (Objects.nonNull(file)) {
            return imageService.saveImage(file);
        }
        return null;
    }

    public AuthorEntity findAuthorBySlug(String slug) {
        return authorRepository
                .findBySlug(slug)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found"));
    }

    public AuthorDto getAuthorBySlug(String slug) {
        AuthorEntity author = findAuthorBySlug(slug);
        return AuthorDto.from(author);
    }

    public void deleteAuthorBySlug(String slug) {
        AuthorEntity author = findAuthorBySlug(slug);
        if (Objects.nonNull(author.getImageUrl())) {
            imageService.deleteImageBySlug(author.getSlug().substring(imageUrl.length()));
        }
        authorRepository.delete(author);
    }
}
