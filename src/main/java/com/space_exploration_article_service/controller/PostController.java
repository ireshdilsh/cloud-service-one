package com.space_exploration_article_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.space_exploration_article_service.payload.PostDto;
import com.space_exploration_article_service.payload.PostRequest;
import com.space_exploration_article_service.payload.UploadImageRequest;
import com.space_exploration_article_service.payload.UploadImagesDto;
import com.space_exploration_article_service.service.PostService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
@Slf4j
public class PostController {
    
    private final PostService postService;

    /** The only public route: lists all posts. */
    @GetMapping
    public List<PostDto> getAll() {
        return postService.getAll();
    }

    @GetMapping("/{id}")
    public PostDto getById(@PathVariable UUID id) {
        return postService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@Valid @RequestBody PostRequest request, @AuthenticationPrincipal Jwt jwt) {
        return postService.create(request, userId(jwt));
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable UUID id, @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        return postService.update(id, request, userId(jwt));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        postService.delete(id, userId(jwt));
    }

    @PostMapping("/{postId}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadImagesDto addImage(@PathVariable UUID postId, @Valid @RequestBody UploadImageRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        return postService.addImage(postId, request, userId(jwt));
    }

    @PutMapping("/{postId}/images/{imageId}")
    public UploadImagesDto updateImage(@PathVariable UUID postId, @PathVariable Long imageId,
            @Valid @RequestBody UploadImageRequest request, @AuthenticationPrincipal Jwt jwt) {
        return postService.updateImage(postId, imageId, request, userId(jwt));
    }

    @DeleteMapping("/{postId}/images/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable UUID postId, @PathVariable Long imageId, @AuthenticationPrincipal Jwt jwt) {
        postService.deleteImage(postId, imageId, userId(jwt));
    }

    private UUID userId(Jwt jwt) {
        try {
            return UUID.fromString(jwt.getSubject());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("JWT subject (sub) must be a UUID");
        }
    }
}
