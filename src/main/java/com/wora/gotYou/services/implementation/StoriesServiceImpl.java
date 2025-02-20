package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.stories.CreateStoryDto;
import com.wora.gotYou.dtos.stories.UpdateStoryDto;
import com.wora.gotYou.dtos.stories.StoryDto;
import com.wora.gotYou.entities.Stories;
import com.wora.gotYou.mappers.StoriesMapper;
import com.wora.gotYou.repositories.StoriesRepository;
import com.wora.gotYou.services.interfaces.StoriesServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoriesServiceImpl implements StoriesServiceInter {

    private final StoriesRepository storiesRepository;
    private final StoriesMapper storiesMapper;

    @Override
    public StoryDto save(CreateStoryDto dto) {
        Stories story = storiesMapper.toEntity(dto);
        Stories savedStory = storiesRepository.save(story);
        return storiesMapper.toDTO(savedStory);
    }

    @Override
    public StoryDto update(UpdateStoryDto dto, Long id) {
        Stories existingStory = storiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        storiesMapper.updateStoryFromDto(dto, existingStory);
        Stories updatedStory = storiesRepository.save(existingStory);
        return storiesMapper.toDTO(updatedStory);
    }

    @Override
    public List<StoryDto> findAll() {
        return storiesRepository.findAll()
                .stream()
                .map(storiesMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        storiesRepository.deleteById(id);
    }
}