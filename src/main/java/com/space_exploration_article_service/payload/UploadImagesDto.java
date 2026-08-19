package com.space_exploration_article_service.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImagesDto {

    private String fileName;

    private String blobUrl;

    private String contentType;

}
