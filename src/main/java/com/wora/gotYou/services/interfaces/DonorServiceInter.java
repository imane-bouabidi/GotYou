package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.donor.CreateDonorDto;
import com.wora.gotYou.dtos.donor.UpdateDonorDto;
import com.wora.gotYou.dtos.donor.DonorDto;
import com.wora.gotYou.services.GenericService;

public interface DonorServiceInter extends GenericService<CreateDonorDto, UpdateDonorDto, DonorDto, Long> {
}