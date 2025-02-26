package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.donation.CreateDonationDto;
import com.wora.gotYou.dtos.donation.DonationDto;
import com.wora.gotYou.dtos.donation.UpdateDonationDto;
import com.wora.gotYou.entities.Donation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DonationMapper {
    DonationDto toDTO(Donation entity);
    Donation toEntity(CreateDonationDto dto);
    Donation toEntity(UpdateDonationDto updateDto);
//    @Mapping(target = "id", ignore = true)
    void updateDonationFromDto(UpdateDonationDto dto, @MappingTarget Donation donation);
}
