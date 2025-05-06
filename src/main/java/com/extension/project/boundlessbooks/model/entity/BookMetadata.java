package com.extension.project.boundlessbooks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "books_metadata",
        indexes = {
                @Index(name = "idx_title_author", columnList = "title, author")
        }
)
@NoArgsConstructor
public class BookMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", columnDefinition = "VARCHAR(255)", unique = true)
    private String title;

    @Column(name = "author", columnDefinition = "VARCHAR(255)")
    private String author;

    @Column(name = "publisher", columnDefinition = "VARCHAR(255)")
    private String publisher;

    @Column(name = "category", columnDefinition = "VARCHAR(255)")
    private String category;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "pages", columnDefinition = "INTEGER")
    private Integer pages;

    @Column(name = "release_date", columnDefinition = "DATE")
    private Date releaseDate;

    @Column(name = "cover_image_url", columnDefinition = "TEXT")
    private String coverImageUrl;

    @Column(name = "epub_url", columnDefinition = "TEXT")
    private String epubUrl;

    @ManyToMany(mappedBy = "favoriteBooks")
    private List<UserProfile> favoriteByUsers;

    @ManyToMany(mappedBy = "shelfBooks")
    private List<UserProfile> shelfByUsers;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}