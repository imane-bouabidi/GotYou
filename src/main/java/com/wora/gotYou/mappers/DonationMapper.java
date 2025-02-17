package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.donation.CreateDonationDto;
import com.wora.gotYou.dtos.donation.UpdateDonationDto;
import com.wora.gotYou.entities.Donation;

public interface DonationMapper {
    UpdateDonationDto toDTO(Donation entity);
    Donation toEntity(CreateDonationDto dto);
    Donation toEntity(UpdateDonationDto updateDto);
}
