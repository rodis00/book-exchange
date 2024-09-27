package com.book.exchange.backend.user;

import com.book.exchange.backend.user.dto.UserDto;
import com.book.exchange.backend.user.dto.UserPasswordDto;
import com.book.exchange.backend.user.dto.UserRequestDto;
import com.book.exchange.backend.user.dto.UserUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<String> saveUser(
            @RequestBody UserRequestDto requestDto
    ) {
        userService.saveUser(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Saved");
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Retrieve a single user by its unique slug field")
    public ResponseEntity<UserDto> getUserBySlug(
            @PathVariable String slug
    ) {
        return ResponseEntity
                .ok(userService.getUserDtoBySlug(slug));
    }

    @PutMapping("/{slug}")
    @Operation(summary = "Update user by its unique slug field")
    public ResponseEntity<UserDto> updateUserBySlug(
            @PathVariable String slug,
            @RequestBody UserUpdateRequestDto requestDto
    ) {
        return ResponseEntity.ok(userService.updateUserBySlug(slug, requestDto));
    }

    @PatchMapping("/{slug}/change-password")
    @Operation(summary = "Change user password by its unique slug field")
    public ResponseEntity<UserDto> changeUserPassword(
            @PathVariable String slug,
            @RequestBody UserPasswordDto passwordDto
    ) {
        return ResponseEntity.ok(userService.changeUserPassword(slug, passwordDto));
    }

    @PatchMapping(
            value = "/{slug}/update-picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Update user profile picture by its unique slug field")
    public ResponseEntity<UserDto> updateUserPicture(
            @PathVariable String slug,
            @RequestBody MultipartFile file
    ) {
        return ResponseEntity.ok(userService.updateUserImage(slug, file));
    }

    @DeleteMapping("/{slug}")
    @Operation(summary = "Delete a user by its unique slug field")
    public ResponseEntity<Void> deleteUserBySlug(
            @PathVariable String slug
    ) {
        userService.deleteUserBySlug(slug);
        return ResponseEntity.noContent().build();
    }
}
