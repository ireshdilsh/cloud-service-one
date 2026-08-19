package com.space_exploration_article_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.space_exploration_article_service.model.UploadImages;

import java.util.Optional;

@Repository
public interface UploadImageRepository extends JpaRepository<UploadImages, Long>{

    Optional<UploadImages> findByIdAndPostExternalId(Long id, java.util.UUID postExternalId);
}
