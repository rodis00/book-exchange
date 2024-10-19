package com.book.exchange.backend.user;

import com.book.exchange.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByNick(String nick);

    boolean existsByEmail(String email);

    UserEntity findByReferralNumber(String referralNumber);

    Optional<UserEntity> findBySlug(String slug);

    boolean existsBySlug(String slug);

    UserEntity findByImageUrl(String url);
}
