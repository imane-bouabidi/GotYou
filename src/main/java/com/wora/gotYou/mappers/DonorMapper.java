package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.donor.CreateDonorDto;
import com.wora.gotYou.dtos.donor.DonorDto;
import com.wora.gotYou.dtos.donor.UpdateDonorDto;
import com.wora.gotYou.entities.Donor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonorMapper {
    DonorDto toDTO(Donor entity);
    Donor toEntity(CreateDonorDto dto);
    Donor toEntity(UpdateDonorDto updateDto);
}
