package com.space_exploration_article_service.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.space_exploration_article_service.exception.ForbiddenException;
import com.space_exploration_article_service.exception.NotFoundException;
import com.space_exploration_article_service.model.Post;
import com.space_exploration_article_service.model.UploadImages;
import com.space_exploration_article_service.payload.PostDto;
import com.space_exploration_article_service.payload.PostRequest;
import com.space_exploration_article_service.payload.UploadImageRequest;
import com.space_exploration_article_service.payload.UploadImagesDto;
import com.space_exploration_article_service.repository.PostRepository;
import com.space_exploration_article_service.repository.UploadImageRepository;
import com.space_exploration_article_service.service.PostService;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UploadImageRepository uploadImageRepository;

    @Override
    @Transactional
    public PostDto create(PostRequest request, UUID authorId) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .postType(request.getPostType())
                .authorId(authorId)
                .build();
        return toDto(postRepository.save(post));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getAll() {
        return postRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto getById(UUID id) {
        return toDto(findPost(id));
    }

    @Override
    @Transactional
    public PostDto update(UUID id, PostRequest request, UUID currentUserId) {
        Post post = findPost(id);
        assertOwner(post, currentUserId);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        post.setPostType(request.getPostType());
        return toDto(postRepository.save(post));
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID currentUserId) {
        Post post = findPost(id);
        assertOwner(post, currentUserId);
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public UploadImagesDto addImage(UUID postId, UploadImageRequest request, UUID currentUserId) {
        Post post = findPost(postId);
        assertOwner(post, currentUserId);
        UploadImages image = UploadImages.builder()
                .fileName(request.getFileName())
                .blobUrl(request.getBlobUrl())
                .contentType(request.getContentType())
                .post(post)
                .build();
        return toDto(uploadImageRepository.save(image));
    }

    @Override
    @Transactional
    public UploadImagesDto updateImage(UUID postId, Long imageId, UploadImageRequest request, UUID currentUserId) {
        Post post = findPost(postId);
        assertOwner(post, currentUserId);
        UploadImages image = findImage(postId, imageId);
        image.setFileName(request.getFileName());
        image.setBlobUrl(request.getBlobUrl());
        image.setContentType(request.getContentType());
        return toDto(uploadImageRepository.save(image));
    }

    @Override
    @Transactional
    public void deleteImage(UUID postId, Long imageId, UUID currentUserId) {
        Post post = findPost(postId);
        assertOwner(post, currentUserId);
        uploadImageRepository.delete(findImage(postId, imageId));
    }

    private Post findPost(UUID id) {
        return postRepository.findByExternalId(id)
                .orElseThrow(() -> new NotFoundException("Post not found: " + id));
    }

    private UploadImages findImage(UUID postId, Long imageId) {
        return uploadImageRepository.findByIdAndPostExternalId(imageId, postId)
                .orElseThrow(() -> new NotFoundException("Image not found: " + imageId));
    }

    private void assertOwner(Post post, UUID currentUserId) {
        if (!post.getAuthorId().equals(currentUserId)) {
            throw new ForbiddenException("You do not own this post");
        }
    }

    private PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getExternalId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .postType(post.getPostType())
                .authorId(post.getAuthorId())
                .images(post.getImages() == null ? java.util.Set.of() : post.getImages().stream().map(this::toDto)
                        .collect(java.util.stream.Collectors.toSet()))
                .build();
    }

    private UploadImagesDto toDto(UploadImages image) {
        return UploadImagesDto.builder()
                .id(image.getId())
                .fileName(image.getFileName())
                .blobUrl(image.getBlobUrl())
                .contentType(image.getContentType())
                .createdAt(image.getCreatedAt())
                .updatedAt(image.getUpdatedAt())
                .build();
    }
}
