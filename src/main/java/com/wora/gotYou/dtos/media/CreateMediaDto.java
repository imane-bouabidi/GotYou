package com.wora.gotYou.dtos.media;

import com.wora.gotYou.entities.enums.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMediaDto {

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @NotBlank(message = "URL must not be blank")
    @Size(max = 255, message = "URL must be at most 255 characters")
    private String url;

    @NotNull(message = "Media type must not be null")
    private MediaType type;

    private Long requestId;
    private Long storyId;
}