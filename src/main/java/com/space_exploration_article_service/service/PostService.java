package com.space_exploration_article_service.service;

import java.util.List;
import java.util.UUID;

import com.space_exploration_article_service.payload.PostDto;
import com.space_exploration_article_service.payload.PostRequest;
import com.space_exploration_article_service.payload.UploadImagesDto;
import com.space_exploration_article_service.payload.UploadImageRequest;

public interface PostService {

    PostDto create(PostRequest request, UUID authorId);

    List<PostDto> getAll();

    PostDto getById(UUID id);

    PostDto update(UUID id, PostRequest request, UUID currentUserId);

    void delete(UUID id, UUID currentUserId);

    UploadImagesDto addImage(UUID postId, UploadImageRequest request, UUID currentUserId);

    UploadImagesDto updateImage(UUID postId, Long imageId, UploadImageRequest request, UUID currentUserId);

    void deleteImage(UUID postId, Long imageId, UUID currentUserId);
}
