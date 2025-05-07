package com.extension.project.boundlessbooks.repository;

import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<BookMetadata, Long> {

    @Query("SELECT b FROM BookMetadata b " +
            "WHERE (:title IS NULL OR b.title LIKE CONCAT('%', CAST(:title AS STRING), '%')) " +
            "AND (:author IS NULL OR b.author LIKE CONCAT('%', CAST(:author AS STRING), '%')) " +
            "AND (:category IS NULL OR b.category = CAST(:category AS STRING)) " +
            "AND ((CAST(:releaseDate AS DATE) IS NULL) OR b.releaseDate = :releaseDate)")
    List<BookMetadata> findBooksByFilters(
            @Param("title") String title,
            @Param("author") String author,
            @Param("category") String category,
            @Param("releaseDate") Date releaseDate
    );

    @Query("SELECT b FROM BookMetadata b " +
            "WHERE (:title IS NULL OR b.title LIKE CONCAT('%', CAST(:title AS STRING), '%')) " +
            "AND (:author IS NULL OR b.author LIKE CONCAT('%', CAST(:author AS STRING), '%')) " +
            "AND (:category IS NULL OR b.category = CAST(:category AS STRING)) " +
            "AND ((CAST(:releaseDate AS DATE) IS NULL) OR b.releaseDate = :releaseDate)")
    Page<BookMetadata> findBooksByFiltersWithPagination(
            @Param("title") String title,
            @Param("author") String author,
            @Param("category") String category,
            @Param("releaseDate") Date releaseDate,
            Pageable pageable
    );
}