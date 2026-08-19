package com.space_exploration_article_service.payload;

import java.time.Instant;
import java.util.Set;

import com.space_exploration_article_service.model.User;
import com.space_exploration_article_service.utils.PostType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private String title;

    private String content;

    private String category;

    private Instant updatedAt;

    private PostType postType;

    private Set<UploadImagesDto> images;

    private User user;
}