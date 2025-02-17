package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.donor.CreateDonorDto;
import com.wora.gotYou.dtos.donor.UpdateDonorDto;
import com.wora.gotYou.entities.Donor;

public interface DonorMapper {
    UpdateDonorDto toDTO(Donor entity);
    Donor toEntity(CreateDonorDto dto);
    Donor toEntity(UpdateDonorDto updateDto);
}
