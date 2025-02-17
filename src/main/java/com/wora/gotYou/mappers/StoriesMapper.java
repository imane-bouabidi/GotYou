package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.stories.CreateStoryDto;
import com.wora.gotYou.dtos.stories.UpdateStoryDto;
import com.wora.gotYou.entities.Stories;

public interface StoriesMapper {
    UpdateStoryDto toDTO(Stories entity);
    Stories toEntity(CreateStoryDto dto);
    Stories toEntity(UpdateStoryDto updateDto);
}
