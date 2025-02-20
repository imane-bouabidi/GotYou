package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.media.CreateMediaDto;
import com.wora.gotYou.dtos.media.UpdateMediaDto;
import com.wora.gotYou.dtos.media.MediaDto;
import com.wora.gotYou.services.GenericService;

public interface MediaServiceInter extends GenericService<CreateMediaDto, UpdateMediaDto, MediaDto, Long> {
}