package com.space_exploration_article_service.model;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.space_exploration_article_service.utils.PostType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
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
    private String title;
    private String content;
    private String category;
    private Instant createdAt;
    private Instant updatedAt;
    private PostType postType ;

    @OneToMany
    @JoinColumn(name ="parcel_job_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UploadImages> parcelImages;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        externalId = UUID.randomUUID();
        postType = PostType.DRAFT;
    }
}
