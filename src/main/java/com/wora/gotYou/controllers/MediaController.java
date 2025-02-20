package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.media.MediaDto;
import com.wora.gotYou.dtos.media.CreateMediaDto;
import com.wora.gotYou.dtos.media.UpdateMediaDto;
import com.wora.gotYou.services.interfaces.MediaServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaServiceInter mediaService;

    @PostMapping
    public ResponseEntity<MediaDto> createMedia(@RequestBody CreateMediaDto dto) {
        MediaDto savedMedia = mediaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMedia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaDto> updateMedia(
            @PathVariable Long id,
            @RequestBody UpdateMediaDto dto
    ) {
        MediaDto updatedMedia = mediaService.update(dto, id);
        return ResponseEntity.ok(updatedMedia);
    }

    @GetMapping
    public ResponseEntity<List<MediaDto>> getAllMedia() {
        List<MediaDto> mediaList = mediaService.findAll();
        return ResponseEntity.ok(mediaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDto> getMediaById(@PathVariable Long id) {
        MediaDto media = mediaService.getMediaById(id);
        return ResponseEntity.ok(media);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        mediaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}