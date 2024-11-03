package com.extension.project.boundlessbooks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "books_metadata")
@NoArgsConstructor
public class BookMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;

    @Column(name = "author", columnDefinition = "VARCHAR(255)")
    private String author;

    @Column(name = "publisher", columnDefinition = "VARCHAR(255)")
    private String publisher;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "likedBooks")
    private List<UserProfile> likedByUsers;
}