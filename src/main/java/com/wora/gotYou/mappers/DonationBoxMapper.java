package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.donationBox.CreateDonationBoxDto;
import com.wora.gotYou.dtos.donationBox.UpdateDonationBoxDto;
import com.wora.gotYou.entities.DonationBox;

public interface DonationBoxMapper {
    UpdateDonationBoxDto toDTO(DonationBox entity);
    DonationBox toEntity(CreateDonationBoxDto dto);
    DonationBox toEntity(UpdateDonationBoxDto updateDto);
}
