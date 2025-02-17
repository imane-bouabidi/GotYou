package com.wora.gotYou.dtos.stories;

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
public class UpdateStoryDto {

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @NotNull(message = "Student ID must not be null")
    private Long studentId;
}