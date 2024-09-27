package com.book.exchange.backend.image;

import com.book.exchange.backend.image.dto.ImageSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/images")
@Tag(name = "Image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Retrieve a single image by its unique slug field")
    public ResponseEntity<byte[]> getImageBySlug(
            @PathVariable String slug
    ) {
        ImageSummaryDto image = imageService.getImageDtoBySlug(slug);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.contentType()))
                .body(image.data());
    }

    @DeleteMapping("/{slug}")
    @Operation(summary = "Delete a image by its unique slug field")
    public ResponseEntity<Void> deleteImageBySlug(
            @PathVariable String slug
    ) {
        imageService.deleteImageBySlug(slug);
        return ResponseEntity.noContent().build();
    }
}
