package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.request.CreateRequestDto;
import com.wora.gotYou.dtos.request.UpdateRequestDto;
import com.wora.gotYou.entities.Request;

public interface RequestMapper {
    UpdateRequestDto toDTO(Request entity);
    Request toEntity(CreateRequestDto dto);
    Request toEntity(UpdateRequestDto updateDto);
}
