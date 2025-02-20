package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.request.CreateRequestDto;
import com.wora.gotYou.dtos.request.UpdateRequestDto;
import com.wora.gotYou.dtos.request.RequestDto;
import com.wora.gotYou.services.GenericService;

public interface RequestServiceInter extends GenericService<CreateRequestDto, UpdateRequestDto, RequestDto, Long> {
}