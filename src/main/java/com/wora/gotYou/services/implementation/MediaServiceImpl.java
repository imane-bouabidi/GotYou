package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.media.CreateMediaDto;
import com.wora.gotYou.dtos.media.UpdateMediaDto;
import com.wora.gotYou.dtos.media.MediaDto;
import com.wora.gotYou.entities.Media;
import com.wora.gotYou.mappers.MediaMapper;
import com.wora.gotYou.repositories.MediaRepository;
import com.wora.gotYou.services.interfaces.MediaServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaServiceInter {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    @Override
    public MediaDto save(CreateMediaDto dto) {
        Media media = mediaMapper.toEntity(dto);
        Media savedMedia = mediaRepository.save(media);
        return mediaMapper.toDTO(savedMedia);
    }

    @Override
    public MediaDto update(UpdateMediaDto dto, Long id) {
        Media existingMedia = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));
//        mediaMapper.updateMediaFromDto(dto, existingMedia);
        Media updatedMedia = mediaRepository.save(existingMedia);
        return mediaMapper.toDTO(updatedMedia);
    }

    @Override
    public List<MediaDto> findAll() {
        return mediaRepository.findAll()
                .stream()
                .map(mediaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        mediaRepository.deleteById(id);
    }
}