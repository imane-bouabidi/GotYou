package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.media.CreateMediaDto;
import com.wora.gotYou.dtos.media.UpdateMediaDto;
import com.wora.gotYou.entities.Media;

public interface MediaMapper {
    UpdateMediaDto toDTO(Media entity);
    Media toEntity(CreateMediaDto dto);
    Media toEntity(UpdateMediaDto updateDto);
}
