package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.stories.StoryDto;
import com.wora.gotYou.dtos.stories.CreateStoryDto;
import com.wora.gotYou.dtos.stories.UpdateStoryDto;
import com.wora.gotYou.services.interfaces.StoriesServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoriesController {

    private final StoriesServiceInter storiesService;

    @PostMapping
    public ResponseEntity<StoryDto> createStory(@RequestBody CreateStoryDto dto) {
        StoryDto savedStory = storiesService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryDto> updateStory(
            @PathVariable Long id,
            @RequestBody UpdateStoryDto dto
    ) {
        StoryDto updatedStory = storiesService.update(dto, id);
        return ResponseEntity.ok(updatedStory);
    }

    @GetMapping
    public ResponseEntity<List<StoryDto>> getAllStories() {
        List<StoryDto> stories = storiesService.findAll();
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDto> getStoryById(@PathVariable Long id) {
        StoryDto story = storiesService.getStoryById(id);
        return ResponseEntity.ok(story);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storiesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}