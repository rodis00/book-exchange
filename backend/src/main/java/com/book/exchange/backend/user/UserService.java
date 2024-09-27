package com.book.exchange.backend.user;

import com.book.exchange.backend.entity.image.ImageEntity;
import com.book.exchange.backend.entity.user.UserEntity;
import com.book.exchange.backend.exception.*;
import com.book.exchange.backend.image.ImageService;
import com.book.exchange.backend.user.dto.UserDto;
import com.book.exchange.backend.user.dto.UserPasswordDto;
import com.book.exchange.backend.user.dto.UserRequestDto;
import com.book.exchange.backend.user.dto.UserUpdateRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {

    @Value("${app.image-url}")
    private String imageUrl;

    private final UserRepository userRepository;
    private final ImageService imageService;

    public UserService(
            UserRepository userRepository,
            ImageService imageService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    public void saveUser(UserRequestDto requestDto) {
        UserEntity user = createUser(requestDto);
        userRepository.save(user);
    }

    private UserEntity createUser(UserRequestDto requestDto) {
        UserEntity user = new UserEntity();
        user.setFirstName(requestDto.firstName());
        user.setLastName(requestDto.lastName());
        user.setNick(generateUniqueNick());
        user.setDateOfBirth(requestDto.dateOfBirth());
        user.setEmail(requestDto.email());
        user.setPassword(requestDto.password());
        user.setReferralNumber(generateUniqueReferralNumber());
        user.setSlug(generateSlug(user));
        return user;
    }

    private String generateUniqueNick() {
        return generateRandomSuffix(8);
    }

    private String generateRandomSuffix(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder suffix = new StringBuilder();

        for (int i = 0; i < length; i++) {
            suffix.append(characters.charAt(random.nextInt(characters.length())));
        }
        return suffix.toString();
    }

    private String generateUniqueReferralNumber() {
        return Base64.getEncoder()
                .encodeToString(UUID.randomUUID().toString().getBytes());
    }

    private String generateSlug(UserEntity user) {
        String firstName = user.getFirstName().toLowerCase();
        String lastName = user.getLastName().toLowerCase();

        String slug = firstName + "-" + lastName;
        int index = 1;
        while (userRepository.existsBySlug(slug)) {
            slug = firstName + "-" + lastName + "-" + index;
            index++;
        }
        return slug;
    }

    public UserEntity getUserBySlug(String slug) {
        return userRepository
                .findBySlug(slug)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public UserDto getUserDtoBySlug(String slug) {
        UserEntity user = getUserBySlug(slug);
        return UserDto.from(user);
    }

    public UserDto updateUserBySlug(
            String slug,
            UserUpdateRequestDto userRequestDto
    ) {
        UserEntity user = getUserBySlug(slug);

        if (userRequestDto.firstName() != null) {
            user.setFirstName(userRequestDto.firstName());
        }
        if (userRequestDto.lastName() != null) {
            user.setLastName(userRequestDto.lastName());
        }
        if (userRequestDto.nick() != null) {
            if (userRepository.existsByNick(userRequestDto.nick())) {
                throw new InvalidNickException("This nick is already taken.");
            }
            user.setNick(userRequestDto.nick());
        }
        if (userRequestDto.dateOfBirth() != null) {
            user.setDateOfBirth(userRequestDto.dateOfBirth());
        }
        if (userRequestDto.email() != null) {
            if (userRepository.existsByEmail(userRequestDto.email())) {
                throw new InvalidEmailException("This email is already taken.");
            }
            user.setEmail(userRequestDto.email());
        }
        userRepository.save(user);

        return UserDto.from(user);
    }

    public UserDto changeUserPassword(
            String slug,
            UserPasswordDto passwordDto
    ) {
        UserEntity user = getUserBySlug(slug);

        if (!passwordDto.oldPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect.");
        }

        if (!passwordDto.newPassword().equals(passwordDto.confirmPassword())) {
            throw new InvalidPasswordException("Passwords do not match");
        }

        user.setPassword(passwordDto.newPassword());
        userRepository.save(user);

        return UserDto.from(user);
    }

    @Transactional
    public UserDto updateUserImage(
            String slug,
            MultipartFile file
    ) {
        if (file == null)
            throw new InvalidImageException("File is null.");
        UserEntity user = getUserBySlug(slug);
        if (user.getImageUrl() != null) {
            try {
                updateExistingImage(user, file);
                userRepository.save(user);
            } catch (Exception e) {
                throw new InvalidImageException(e.getMessage());
            }
        } else {
            ImageEntity newImage = createImage(file);
            user.setImageUrl(imageUrl + newImage.getSlug());
            userRepository.save(user);
        }
        return UserDto.from(user);
    }

    private void updateExistingImage(
            UserEntity user,
            MultipartFile file
    ) throws IOException {
        String existingImageSlug = user.getImageUrl().substring(imageUrl.length());
        ImageEntity existingImage = imageService.getImageBySlug(existingImageSlug);
        existingImage.setContentType(file.getContentType());
        existingImage.setOriginalName(file.getOriginalFilename());
        existingImage.setData(file.getBytes());
        String newImageSlug = imageService.generateSlug(existingImage);
        existingImage.setSlug(newImageSlug);
        user.setImageUrl(imageUrl + newImageSlug);
        user.setUpdatedAt(LocalDateTime.now());
    }

    private ImageEntity createImage(MultipartFile file) {
        return imageService.saveImage(file);
    }

    public void deleteUserBySlug(String slug) {
        UserEntity user = getUserBySlug(slug);
        imageService.deleteImageBySlug(user.getImageUrl().substring(imageUrl.length()));
        userRepository.delete(user);
    }

    public UserEntity getUserByReferralNumber(String referralNumber) {
        return userRepository.findByReferralNumber(referralNumber);
    }
}
