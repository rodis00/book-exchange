package com.book.exchange.backend.book;

import com.book.exchange.backend.book.dto.BookDto;
import com.book.exchange.backend.book.dto.BookPageDto;
import com.book.exchange.backend.book.dto.BookRequestDto;
import com.book.exchange.backend.book.dto.SortBy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/books")
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    public BookController(
            final BookService bookService
    ) {
        this.bookService = bookService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new book with image attachment")
    public ResponseEntity<String> saveBook(
            @RequestBody(
                    content = @Content(
                            encoding = @Encoding(
                                    name = "book",
                                    contentType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            )
            @RequestPart BookRequestDto book,
            @RequestPart MultipartFile file
    ) {
        bookService.saveBook(book, file);
        return new ResponseEntity<>("book saved", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieve a list of all books with pagination and sorting")
    public ResponseEntity<BookPageDto> getAllBooks(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = SortBy.CREATED_AT) String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        return ResponseEntity.ok(bookService.getAllBooks(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Retrieve a single book by its unique slug field")
    public ResponseEntity<BookDto> getBookBySlug(
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(bookService.getBookBySlug(slug));
    }

    @GetMapping("/available")
    @Operation(summary = "Retrieve a list of available books with pagination and sorting")
    public ResponseEntity<BookPageDto> getAvailableBooks(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = SortBy.CREATED_AT) String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        return ResponseEntity.ok(bookService.getAvailableBooks(pageNumber, pageSize, sortBy, sortDirection));
    }

    @DeleteMapping("/{slug}")
    @Operation(summary = "Delete a book by its unique slug field")
    public ResponseEntity<Void> deleteBookBySlug(
            @PathVariable String slug
    ) {
        bookService.deleteBookBySlug(slug);
        return ResponseEntity.noContent().build();
    }
}
