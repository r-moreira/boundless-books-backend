package com.extension.project.boundlessbooks.repository;

import com.extension.project.boundlessbooks.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT u FROM UserProfile u WHERE u.googleUser.id = :googleUserId")
    Optional<UserProfile> findByGoogleUserId(@Param("googleUserId") String googleUserId);
}
