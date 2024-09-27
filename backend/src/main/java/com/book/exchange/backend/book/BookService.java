package com.book.exchange.backend.book;

import com.book.exchange.backend.author.AuthorRepository;
import com.book.exchange.backend.book.dto.BookDto;
import com.book.exchange.backend.book.dto.BookPageDto;
import com.book.exchange.backend.book.dto.BookRequestDto;
import com.book.exchange.backend.book.dto.BookWithAuthorDto;
import com.book.exchange.backend.entity.author.AuthorEntity;
import com.book.exchange.backend.entity.book.BookEntity;
import com.book.exchange.backend.entity.image.ImageEntity;
import com.book.exchange.backend.exception.BookNotFoundException;
import com.book.exchange.backend.image.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ImageService imageService;
    private final AuthorRepository authorRepository;

    @Value("${app.image-url}")
    private String imageUrl;

    public BookService(
            BookRepository bookRepository,
            ImageService imageService,
            AuthorRepository authorRepository
    ) {
        this.bookRepository = bookRepository;
        this.imageService = imageService;
        this.authorRepository = authorRepository;
    }

    public void saveBook(
            BookRequestDto requestDto,
            MultipartFile file
    ) {
        BookEntity bookEntity = createBook(requestDto, file);
        bookRepository.save(bookEntity);
    }


    private BookEntity createBook(
            BookRequestDto requestDto,
            MultipartFile file
    ) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(requestDto.title());
        bookEntity.setCategory(requestDto.category());
        bookEntity.setDescription(requestDto.description());
        bookEntity.setReleaseDate(requestDto.releaseDate());
        bookEntity.setPrice(requestDto.price());
        bookEntity.setAvailable(requestDto.isAvailable());
        bookEntity.setCondition(requestDto.condition());
        bookEntity.setSlug(generateSlug(bookEntity));

        AuthorEntity author = authorRepository
                .findBySlug(requestDto.authorSlug())
                .orElse(null);

        bookEntity.setAuthor(author);

        ImageEntity image = createImage(file);
        bookEntity.setImageUrl(imageUrl + image.getSlug());

        return bookEntity;
    }

    private ImageEntity createImage(MultipartFile image) {
        return imageService.saveImage(image);
    }

    private String generateSlug(BookEntity bookEntity) {
        String title = bookEntity.getTitle()
                .replaceAll("\\s+", "-")
                .toLowerCase();

        String slug = title;
        int index = 1;

        while (bookRepository.existsBySlug(slug)) {
            slug = title + "-" + index;
            index++;
        }
        return slug;
    }

    public BookPageDto getAllBooks(
            int pageNumber,
            int pageSize,
            String sortBy,
            Sort.Direction sortDirection
    ) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<BookEntity> bookPage = bookRepository.findAll(pageable);

        List<BookWithAuthorDto> books = bookPage.getContent().stream()
                .map(BookWithAuthorDto::from)
                .toList();

        return BookPageDto.from(books, bookPage);
    }

    public BookEntity findBookBySlug(String slug) {
        return bookRepository
                .findBySlug(slug)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public BookDto getBookBySlug(String slug) {
        BookEntity book = findBookBySlug(slug);
        return BookDto.from(book);
    }

    public List<BookEntity> getBooksBySlugs(List<String> bookSlugs) {
        List<BookEntity> books = new ArrayList<>();
        bookSlugs.forEach(slug -> bookRepository.findBySlug(slug).ifPresent(books::add));
        return books;
    }

    public void changeBooksAvailableStatus(List<BookEntity> books) {
        books.forEach(book -> book.setAvailable(!book.isAvailable()));
        bookRepository.saveAll(books);
    }

    public BookPageDto getAvailableBooks(
            int pageNumber,
            int pageSize,
            String sortBy,
            Sort.Direction sortDirection
    ) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<BookEntity> bookPage = bookRepository.findByAvailable(true, pageable);

        List<BookWithAuthorDto> books = bookPage.getContent()
                .stream()
                .map(BookWithAuthorDto::from)
                .toList();

        return BookPageDto.from(books, bookPage);
    }

    public void deleteBookBySlug(String slug) {
        BookEntity book = findBookBySlug(slug);
        imageService.deleteImageBySlug(book.getImageUrl().substring(imageUrl.length()));
        bookRepository.delete(book);
    }
}
