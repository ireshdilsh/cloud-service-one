package com.space_exploration_article_service.model;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.space_exploration_article_service.utils.PostType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID externalId;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    private String category;
    private Instant createdAt;
    private Instant updatedAt;
    private PostType postType;

    /** ID of the author managed by the separate user microservice. */
    @Column(nullable = false, updatable = false)
    private UUID authorId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UploadImages> images;


    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        externalId = UUID.randomUUID();
        if (postType == null) {
            postType = PostType.DRAFT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
