package com.wora.gotYou.dtos.media;

import com.wora.gotYou.entities.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaDto {
    private Long id;
    private String title;
    private String url;
    private MediaType type;
    private Long requestId;
    private Long storyId;
}