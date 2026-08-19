package com.space_exploration_article_service.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImagesDto {

    private Long id;

    private String fileName;

    private String blobUrl;

    private String contentType;

    private Instant createdAt;

    private Instant updatedAt;

}
