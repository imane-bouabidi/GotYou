package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.stories.CreateStoryDto;
import com.wora.gotYou.dtos.stories.UpdateStoryDto;
import com.wora.gotYou.dtos.stories.StoryDto;
import com.wora.gotYou.services.GenericService;

public interface StoriesServiceInter extends GenericService<CreateStoryDto, UpdateStoryDto, StoryDto, Long> {
    StoryDto getStoryById(Long id);
}