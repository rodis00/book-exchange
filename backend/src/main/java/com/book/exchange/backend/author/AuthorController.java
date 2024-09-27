package com.book.exchange.backend.author;

import com.book.exchange.backend.author.dto.AuthorDto;
import com.book.exchange.backend.author.dto.AuthorRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/authors")
@Tag(name = "Author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(
            final AuthorService authorService
    ) {
        this.authorService = authorService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new author with an optional image attachment")
    public ResponseEntity<String> saveAuthor(
            @RequestBody(
                    content = @Content(
                            encoding = @Encoding(
                                    name = "author",
                                    contentType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            )
            @RequestPart(name = "author") AuthorRequestDto author,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        authorService.saveAuthor(author, file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Author saved");
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Retrieve a single author by its unique slug field")
    public ResponseEntity<AuthorDto> getAuthorBySlug(
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(authorService.getAuthorBySlug(slug));
    }

    @DeleteMapping("/{slug}")
    @Operation(summary = "Delete an author by its unique slug field")
    public ResponseEntity<Void> deleteAuthorBySlug(
            @PathVariable String slug
    ) {
        authorService.deleteAuthorBySlug(slug);
        return ResponseEntity.noContent().build();
    }
}
