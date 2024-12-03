package com.extension.project.boundlessbooks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "google_users")
@NoArgsConstructor
@ToString
public class GoogleUser{

    @Id
    private String id;

    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "googleUser")
    private UserProfile userProfile;
}
