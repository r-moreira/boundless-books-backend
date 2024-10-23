package com.extension.project.boundlessbooks.repository;

import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<BookMetadata, Long> {
}
