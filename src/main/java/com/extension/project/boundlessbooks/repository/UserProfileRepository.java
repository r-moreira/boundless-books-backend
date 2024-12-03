package com.extension.project.boundlessbooks.repository;

import com.extension.project.boundlessbooks.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
}
