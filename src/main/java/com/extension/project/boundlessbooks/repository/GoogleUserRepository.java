package com.extension.project.boundlessbooks.repository;

import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleUserRepository extends JpaRepository<GoogleUser, String> {
}
