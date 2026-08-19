package com.space_exploration_article_service.payload;

import com.space_exploration_article_service.utils.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String category;

    @NotNull
    private PostType postType;
}
