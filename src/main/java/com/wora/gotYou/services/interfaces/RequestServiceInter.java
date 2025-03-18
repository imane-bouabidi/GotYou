package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.request.CreateRequestDto;
import com.wora.gotYou.dtos.request.UpdateRequestDto;
import com.wora.gotYou.dtos.request.RequestDto;
import com.wora.gotYou.entities.Request;
import com.wora.gotYou.services.GenericService;

import java.util.List;

public interface RequestServiceInter extends GenericService<CreateRequestDto, UpdateRequestDto, RequestDto, Long> {
    RequestDto getRequestById(Long id);
    List<RequestDto> getRequestsByStudentId();
    List<RequestDto> searchRequests(String keyword);

}