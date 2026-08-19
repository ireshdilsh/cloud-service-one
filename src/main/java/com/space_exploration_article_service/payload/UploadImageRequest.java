package com.space_exploration_article_service.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UploadImageRequest {
    @NotBlank
    private String fileName;

    @NotBlank
    private String blobUrl;

    @NotBlank
    private String contentType;
}
