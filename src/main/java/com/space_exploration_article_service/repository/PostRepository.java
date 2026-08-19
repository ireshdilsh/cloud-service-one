package com.space_exploration_article_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.space_exploration_article_service.model.Post;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByExternalId(UUID externalId);
}
